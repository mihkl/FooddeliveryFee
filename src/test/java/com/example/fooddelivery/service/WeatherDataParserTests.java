package com.example.fooddelivery.service;

import com.example.fooddelivery.model.WeatherData;
import com.example.fooddelivery.repository.WeatherDataRepository;
import com.example.fooddelivery.service.WeatherDataParser;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WeatherDataParserTests {
    @Test
    void parseAndSaveWeatherDataValidData() {

        String xmlData = "<observations><station><name>Tallinn-Harku</name><wmocode>12345</wmocode><airtemperature>10.5" +
                "</airtemperature><windspeed>8.0</windspeed><phenomenon>Mist</phenomenon></station></observations>";
        WeatherDataRepository weatherDataRepository = mock(WeatherDataRepository.class);

        WeatherDataParser.parseAndSaveWeatherData(xmlData, weatherDataRepository);

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

        WeatherDataParser.parseAndSaveWeatherData(xmlData, weatherDataRepository);

        verify(weatherDataRepository, never()).save(any());
    }
    @Test
    void parseAndSaveWeatherDataValidDataNoError() {

        String xmlData = "<observations><station><name>Tallinn-Harku</name><wmocode>12345</wmocode><airtemperature>10.5" +
                "</airtemperature><windspeed>8.0</windspeed><phenomenon>Sunny</phenomenon></station></observations>";
        WeatherDataRepository weatherDataRepository = mock(WeatherDataRepository.class);

        WeatherDataParser.parseAndSaveWeatherData(xmlData, weatherDataRepository);

        assertFalse(WeatherDataParser.ReturnErrorStatus());
    }
    @Test
    void parseAndSaveWeatherDataInvalidDataError() {

        String xmlData = "<invalidXML>";
        WeatherDataRepository weatherDataRepository = mock(WeatherDataRepository.class);

        WeatherDataParser.parseAndSaveWeatherData(xmlData, weatherDataRepository);

        assertTrue(WeatherDataParser.ReturnErrorStatus());
    }
}
