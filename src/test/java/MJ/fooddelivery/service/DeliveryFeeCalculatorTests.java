package MJ.fooddelivery.service;

import MJ.fooddelivery.model.WeatherData;
import MJ.fooddelivery.repository.WeatherDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class DeliveryFeeCalculatorTests {

    @Mock
    private WeatherDataRepository weatherDataRepository;
    private DeliveryFeeCalculator deliveryFeeCalculator;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        deliveryFeeCalculator = new DeliveryFeeCalculator(weatherDataRepository);
    }
    @Test
    void calculateDeliveryFeeNoExtraFee() {
        String city = "Tallinn-Harku";
        String vehicleType = "Car";

        WeatherData weatherData = new WeatherData();
        weatherData.setWindSpeed(10.0);
        weatherData.setAirTemperature(5.0);
        weatherData.setWeatherPhenomenon("Cloudy");

        when(weatherDataRepository.findLatestWeatherDataByStationName(city, PageRequest.of(0, 1)))
                .thenReturn(Collections.singletonList(weatherData));

        double expectedFee = 4.0;
        assertEquals(expectedFee, deliveryFeeCalculator.calculateDeliveryFee(city, vehicleType));
    }
    @Test
    void calculateDeliveryFeeExtraFeeDueToSnowfall() {
        String city = "Tartu-Tõravere";
        String vehicleType = "Bike";

        WeatherData weatherData = new WeatherData();
        weatherData.setWindSpeed(5.0);
        weatherData.setAirTemperature(-5.0);
        weatherData.setWeatherPhenomenon("snowfall");

        when(weatherDataRepository.findLatestWeatherDataByStationName(city, PageRequest.of(0, 1)))
                .thenReturn(Collections.singletonList(weatherData));

        double expectedFee = 2.0 + 1.5;
        assertEquals(expectedFee, deliveryFeeCalculator.calculateDeliveryFee(city, vehicleType));
    }
    @Test
    void calculateDeliveryFeeWarningDueToHail() {
        String city = "Pärnu";
        String vehicleType = "Bike";

        WeatherData weatherData = new WeatherData();
        weatherData.setWindSpeed(0.0);
        weatherData.setAirTemperature(5.0);
        weatherData.setWeatherPhenomenon("Hail");

        when(weatherDataRepository.findLatestWeatherDataByStationName(city, PageRequest.of(0, 1)))
                .thenReturn(Collections.singletonList(weatherData));

        double expectedFee = -1;
        assertEquals(expectedFee, deliveryFeeCalculator.calculateDeliveryFee(city, vehicleType));
    }
    @Test
    void calculateDeliveryFeeWarningDueToWindSpeed() {
        String city = "Tallinn-Harku";
        String vehicleType = "Bike";

        WeatherData weatherData = new WeatherData();
        weatherData.setWindSpeed(100);
        weatherData.setAirTemperature(5.0);
        weatherData.setWeatherPhenomenon("Mist");

        when(weatherDataRepository.findLatestWeatherDataByStationName(city, PageRequest.of(0, 1)))
                .thenReturn(Collections.singletonList(weatherData));

        double expectedFee = -1;
        assertEquals(expectedFee, deliveryFeeCalculator.calculateDeliveryFee(city, vehicleType));
    }
    @Test
    void calculateDeliveryFeeScooterHeavyWind() {
        String city = "Tallinn-Harku";
        String vehicleType = "Scooter";

        WeatherData weatherData = new WeatherData();
        weatherData.setWindSpeed(100);
        weatherData.setAirTemperature(5.0);
        weatherData.setWeatherPhenomenon("Mist");

        when(weatherDataRepository.findLatestWeatherDataByStationName(city, PageRequest.of(0, 1)))
                .thenReturn(Collections.singletonList(weatherData));

        double expectedFee = 3.5;
        assertEquals(expectedFee, deliveryFeeCalculator.calculateDeliveryFee(city, vehicleType));
    }
    @Test
    void calculateDeliveryError() {
        String city = "";
        String vehicleType = "";

        WeatherData weatherData = new WeatherData();
        weatherData.setWindSpeed(100);
        weatherData.setAirTemperature(5.0);
        weatherData.setWeatherPhenomenon("Mist");

        when(weatherDataRepository.findLatestWeatherDataByStationName(city, PageRequest.of(0, 1)))
                .thenReturn(Collections.singletonList(weatherData));

        double expectedFee = 0.0;
        assertEquals(expectedFee, deliveryFeeCalculator.calculateDeliveryFee(city, vehicleType));
    }
}

