package MJ.fooddelivery.config;

import MJ.fooddelivery.repository.WeatherDataRepository;
import MJ.fooddelivery.service.WeatherDataParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class CronJobConfigTests {
    @Mock
    private WeatherDataParser weatherDataParser;
    @Mock
    private WeatherDataRepository weatherDataRepository;
    @InjectMocks
    private CronJobConfig cronJobConfig;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @AfterEach
    void tearDown() {

    }
//    @Test
//    void importWeatherDataCallParserAndRepository() {
//        String testXML = "test";
//
//        cronJobConfig.importWeatherData();
//        verify(weatherDataParser, times(1));
//    }
}
