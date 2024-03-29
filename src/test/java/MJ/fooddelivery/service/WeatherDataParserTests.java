package MJ.fooddelivery.service;

import MJ.fooddelivery.model.WeatherData;
import MJ.fooddelivery.repository.WeatherDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the WeatherDataParser class.
 */
class WeatherDataParserTests {
    @InjectMocks
    private WeatherDataParser weatherDataParser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test for parsing and saving weather data when valid data is provided.
     */
    @Test
    void parseAndSaveWeatherDataValidData() {
        // Given
        String xmlData = "<observations><station><name>Tallinn-Harku</name><wmocode>12345</wmocode><airtemperature>10.5" +
                "</airtemperature><windspeed>8.0</windspeed><phenomenon>Mist</phenomenon></station></observations>";
        WeatherDataRepository weatherDataRepository = mock(WeatherDataRepository.class);

        // When
        weatherDataParser.parseAndSaveWeatherData(xmlData, weatherDataRepository);

        // Then
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

    /**
     * Test for parsing and saving weather data for an unwanted city.
     */
    @Test
    void parseAndSaveWeatherDataUnwantedCity() {
        // Given
        String xmlData = "<observations><station><name>UnWantedCity</name><wmocode>12345</wmocode><airtemperature>10.5" +
                "</airtemperature><windspeed>8.0</windspeed><phenomenon>Sunny</phenomenon></station></observations>";
        WeatherDataRepository weatherDataRepository = mock(WeatherDataRepository.class);

        // When
        weatherDataParser.parseAndSaveWeatherData(xmlData, weatherDataRepository);

        // Then
        verify(weatherDataRepository, never()).save(any());
    }
}
