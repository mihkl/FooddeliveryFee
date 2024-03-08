package MJ.fooddelivery.service;

import MJ.fooddelivery.repository.WeatherDataRepository;
import MJ.fooddelivery.model.WeatherData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WeatherDataParserTests {
    @InjectMocks
    private WeatherDataParser weatherDataParser;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void parseAndSaveWeatherDataValidData() {

        String xmlData = "<observations><station><name>Tallinn-Harku</name><wmocode>12345</wmocode><airtemperature>10.5" +
                "</airtemperature><windspeed>8.0</windspeed><phenomenon>Mist</phenomenon></station></observations>";
        WeatherDataRepository weatherDataRepository = mock(WeatherDataRepository.class);

        weatherDataParser.parseAndSaveWeatherData(xmlData, weatherDataRepository);

        ArgumentCaptor<WeatherData> captor = ArgumentCaptor.forClass(WeatherData.class);
        verify(weatherDataRepository, times(1)).save(captor.capture());

        WeatherData savedWeatherData = captor.getValue();
        assertEquals("Tallinn-Harku", savedWeatherData.getStationName());
        assertEquals("12345", savedWeatherData.getWmoCode());
        assertEquals(10.5, savedWeatherData.getAirTemperature());
        assertEquals(8, savedWeatherData.getWindSpeed());
        assertEquals("Mist", savedWeatherData.getWeatherPhenomenon());
        assertNotNull(savedWeatherData.getTimestamp());
    }
    @Test
    void parseAndSaveWeatherDataUnwantedCity() {

        String xmlData = "<observations><station><name>UnWantedCity</name><wmocode>12345</wmocode><airtemperature>10.5" +
                "</airtemperature><windspeed>8.0</windspeed><phenomenon>Sunny</phenomenon></station></observations>";
        WeatherDataRepository weatherDataRepository = mock(WeatherDataRepository.class);

        weatherDataParser.parseAndSaveWeatherData(xmlData, weatherDataRepository);

        verify(weatherDataRepository, never()).save(any());
    }
}
