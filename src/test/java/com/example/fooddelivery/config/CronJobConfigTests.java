package com.example.fooddelivery.config;

import com.example.fooddelivery.service.WeatherDataParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
class CronJobConfigTests {
    @Mock
    private WeatherDataParser weatherDataParser;
    @InjectMocks
    private CronJobConfig cronJobConfig;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void importWeatherData_ShouldInvokeParserAndRepository() {

        cronJobConfig.importWeatherData();
        verify(weatherDataParser, times(1));
    }
}
