package MJ.fooddelivery.service;

import MJ.fooddelivery.model.BaseFeeCalculationRules;
import MJ.fooddelivery.model.ExtraFeeCalculationRules;
import MJ.fooddelivery.model.WeatherData;
import MJ.fooddelivery.repository.BaseFeeCalculationRulesRepository;
import MJ.fooddelivery.repository.ExtraFeeCalculationRepository;
import MJ.fooddelivery.repository.WeatherDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class DeliveryFeeCalculatorTests {

    @Mock
    private WeatherDataRepository weatherDataRepository;

    @Mock
    private BaseFeeCalculationRulesRepository baseFeeCalculationRulesRepository;

    @Mock
    private ExtraFeeCalculationRepository extraFeeCalculationRepository;

    private DeliveryFeeCalculator deliveryFeeCalculator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        deliveryFeeCalculator = new DeliveryFeeCalculator(weatherDataRepository, baseFeeCalculationRulesRepository, extraFeeCalculationRepository);
    }

    @Test
    void calculateDeliveryFeeValidDataExtraFeeDueToWind() {

        String city = "Tallinn-Harku";
        String vehicleType = "Bike";
        WeatherData weatherData = new WeatherData();
        weatherData.setWindSpeed(17.0);
        weatherData.setAirTemperature(15.0);
        weatherData.setWeatherPhenomenon("Cloudy");
        BaseFeeCalculationRules baseFeeCalculationRules = new BaseFeeCalculationRules();
        baseFeeCalculationRules.setBaseFee(4.0);
        ExtraFeeCalculationRules extraFeeCalculationRules = new ExtraFeeCalculationRules();
        extraFeeCalculationRules.setWindFee(15.0);
        extraFeeCalculationRules.setWindFeeValue(2.0);
        extraFeeCalculationRules.setTempSmallFee(0.0);
        extraFeeCalculationRules.setTempBigFee(10.0);
        extraFeeCalculationRules.setTempSmallFeeValue(1.0);
        extraFeeCalculationRules.setTempBigFeeValue(2.0);
        extraFeeCalculationRules.setSnowFeeValue(3.0);
        extraFeeCalculationRules.setRainFeeValue(4.0);
        extraFeeCalculationRules.setWindWarning(20.0);

        when(weatherDataRepository.findLatestWeatherDataByStationName(city, PageRequest.of(0, 1)))
                .thenReturn(Collections.singletonList(weatherData));
        when(extraFeeCalculationRepository.findById(1L)).thenReturn(Optional.of(extraFeeCalculationRules));
        when(baseFeeCalculationRulesRepository.findBaseFeeByCityAndVehicleType(city, vehicleType))
                .thenReturn(baseFeeCalculationRules);

        double expectedFee = 4.0 + 2.0;
        double actualFee = deliveryFeeCalculator.calculateDeliveryFee(city, vehicleType);

        assertEquals(expectedFee, actualFee);
    }
    @Test
    void calculateDeliveryFeeValidDataExtraFeeWindWarning() {

        String city = "Tallinn-Harku";
        String vehicleType = "Bike";
        WeatherData weatherData = new WeatherData();
        weatherData.setWindSpeed(25.0);
        weatherData.setAirTemperature(15.0);
        weatherData.setWeatherPhenomenon("Cloudy");
        BaseFeeCalculationRules baseFeeCalculationRules = new BaseFeeCalculationRules();
        baseFeeCalculationRules.setBaseFee(4.0);
        ExtraFeeCalculationRules extraFeeCalculationRules = new ExtraFeeCalculationRules();
        extraFeeCalculationRules.setWindFee(15.0);
        extraFeeCalculationRules.setWindFeeValue(2.0);
        extraFeeCalculationRules.setTempSmallFee(0.0);
        extraFeeCalculationRules.setTempBigFee(10.0);
        extraFeeCalculationRules.setTempSmallFeeValue(1.0);
        extraFeeCalculationRules.setTempBigFeeValue(2.0);
        extraFeeCalculationRules.setSnowFeeValue(3.0);
        extraFeeCalculationRules.setRainFeeValue(4.0);
        extraFeeCalculationRules.setWindWarning(20.0);

        when(weatherDataRepository.findLatestWeatherDataByStationName(city, PageRequest.of(0, 1)))
                .thenReturn(Collections.singletonList(weatherData));
        when(extraFeeCalculationRepository.findById(1L)).thenReturn(Optional.of(extraFeeCalculationRules));
        when(baseFeeCalculationRulesRepository.findBaseFeeByCityAndVehicleType(city, vehicleType))
                .thenReturn(baseFeeCalculationRules);

        double expectedFee = -1.0;
        double actualFee = deliveryFeeCalculator.calculateDeliveryFee(city, vehicleType);

        assertEquals(expectedFee, actualFee);
    }
    @Test
    void calculateDeliveryFeeValidDataExtraDueToTemperature() {

        String city = "Tallinn-Harku";
        String vehicleType = "Bike";
        WeatherData weatherData = new WeatherData();
        weatherData.setWindSpeed(2.0);
        weatherData.setAirTemperature(1.0);
        weatherData.setWeatherPhenomenon("Cloudy");
        BaseFeeCalculationRules baseFeeCalculationRules = new BaseFeeCalculationRules();
        baseFeeCalculationRules.setBaseFee(4.0);
        ExtraFeeCalculationRules extraFeeCalculationRules = new ExtraFeeCalculationRules();
        extraFeeCalculationRules.setWindFee(15.0);
        extraFeeCalculationRules.setWindFeeValue(2.0);
        extraFeeCalculationRules.setTempSmallFee(10.0);
        extraFeeCalculationRules.setTempBigFee(2.0);
        extraFeeCalculationRules.setTempSmallFeeValue(1.0);
        extraFeeCalculationRules.setTempBigFeeValue(2.0);
        extraFeeCalculationRules.setSnowFeeValue(3.0);
        extraFeeCalculationRules.setRainFeeValue(4.0);
        extraFeeCalculationRules.setWindWarning(20.0);

        when(weatherDataRepository.findLatestWeatherDataByStationName(city, PageRequest.of(0, 1)))
                .thenReturn(Collections.singletonList(weatherData));
        when(extraFeeCalculationRepository.findById(1L)).thenReturn(Optional.of(extraFeeCalculationRules));
        when(baseFeeCalculationRulesRepository.findBaseFeeByCityAndVehicleType(city, vehicleType))
                .thenReturn(baseFeeCalculationRules);

        double expectedFee = 4.0 + 2.0;
        double actualFee = deliveryFeeCalculator.calculateDeliveryFee(city, vehicleType);

        assertEquals(expectedFee, actualFee);
    }
    @Test
    void calculateDeliveryFeeValidDataExtraDueToSnow() {

        String city = "Tallinn-Harku";
        String vehicleType = "Bike";
        WeatherData weatherData = new WeatherData();
        weatherData.setWindSpeed(1.0);
        weatherData.setAirTemperature(20.0);
        weatherData.setWeatherPhenomenon("snow");
        BaseFeeCalculationRules baseFeeCalculationRules = new BaseFeeCalculationRules();
        baseFeeCalculationRules.setBaseFee(4.0);
        ExtraFeeCalculationRules extraFeeCalculationRules = new ExtraFeeCalculationRules();
        extraFeeCalculationRules.setWindFee(15.0);
        extraFeeCalculationRules.setWindFeeValue(2.0);
        extraFeeCalculationRules.setTempSmallFee(10.0);
        extraFeeCalculationRules.setTempBigFee(2.0);
        extraFeeCalculationRules.setTempSmallFeeValue(1.0);
        extraFeeCalculationRules.setTempBigFeeValue(2.0);
        extraFeeCalculationRules.setSnowFeeValue(3.0);
        extraFeeCalculationRules.setRainFeeValue(4.0);
        extraFeeCalculationRules.setWindWarning(20.0);

        when(weatherDataRepository.findLatestWeatherDataByStationName(city, PageRequest.of(0, 1)))
                .thenReturn(Collections.singletonList(weatherData));
        when(extraFeeCalculationRepository.findById(1L)).thenReturn(Optional.of(extraFeeCalculationRules));
        when(baseFeeCalculationRulesRepository.findBaseFeeByCityAndVehicleType(city, vehicleType))
                .thenReturn(baseFeeCalculationRules);

        double expectedFee = 4.0 + 3.0;
        double actualFee = deliveryFeeCalculator.calculateDeliveryFee(city, vehicleType);

        assertEquals(expectedFee, actualFee);
    }
    @Test
    void calculateDeliveryFeeValidDataExtraDueToRain() {

        String city = "Tallinn-Harku";
        String vehicleType = "Bike";
        WeatherData weatherData = new WeatherData();
        weatherData.setWindSpeed(1.0);
        weatherData.setAirTemperature(20.0);
        weatherData.setWeatherPhenomenon("rain");
        BaseFeeCalculationRules baseFeeCalculationRules = new BaseFeeCalculationRules();
        baseFeeCalculationRules.setBaseFee(4.0);
        ExtraFeeCalculationRules extraFeeCalculationRules = new ExtraFeeCalculationRules();
        extraFeeCalculationRules.setWindFee(15.0);
        extraFeeCalculationRules.setWindFeeValue(2.0);
        extraFeeCalculationRules.setTempSmallFee(10.0);
        extraFeeCalculationRules.setTempBigFee(2.0);
        extraFeeCalculationRules.setTempSmallFeeValue(1.0);
        extraFeeCalculationRules.setTempBigFeeValue(2.0);
        extraFeeCalculationRules.setSnowFeeValue(3.0);
        extraFeeCalculationRules.setRainFeeValue(4.0);
        extraFeeCalculationRules.setWindWarning(20.0);

        when(weatherDataRepository.findLatestWeatherDataByStationName(city, PageRequest.of(0, 1)))
                .thenReturn(Collections.singletonList(weatherData));
        when(extraFeeCalculationRepository.findById(1L)).thenReturn(Optional.of(extraFeeCalculationRules));
        when(baseFeeCalculationRulesRepository.findBaseFeeByCityAndVehicleType(city, vehicleType))
                .thenReturn(baseFeeCalculationRules);

        double expectedFee = 4.0 + 4.0;
        double actualFee = deliveryFeeCalculator.calculateDeliveryFee(city, vehicleType);

        assertEquals(expectedFee, actualFee);
    }
    @Test
    void calculateDeliveryFeeInvalidExtraFeeRules() {
        String city = "Tallinn-Harku";
        String vehicleType = "Car";
        WeatherData weatherData = new WeatherData();
        weatherData.setWindSpeed(10.0);
        weatherData.setAirTemperature(5.0);
        weatherData.setWeatherPhenomenon("Cloudy");
        BaseFeeCalculationRules baseFeeCalculationRules = new BaseFeeCalculationRules();
        baseFeeCalculationRules.setBaseFee(4.0);

        when(weatherDataRepository.findLatestWeatherDataByStationName(city, PageRequest.of(0, 1)))
                .thenReturn(Collections.singletonList(weatherData));
        when(extraFeeCalculationRepository.findById(1L)).thenReturn(Optional.empty());
        when(baseFeeCalculationRulesRepository.findBaseFeeByCityAndVehicleType(city, vehicleType))
                .thenReturn(baseFeeCalculationRules);

        double expectedFee = 0.0;
        double actualFee = deliveryFeeCalculator.calculateDeliveryFee(city, vehicleType);

        assertEquals(expectedFee, actualFee);
    }
}

