package MJ.fooddelivery.config;

import MJ.fooddelivery.service.WeatherDataParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
    void importWeatherDataCallParserAndRepository() {

        cronJobConfig.importWeatherData();
        verify(weatherDataParser, times(1)).parseAndSaveWeatherData(anyString(), any());
    }
}
