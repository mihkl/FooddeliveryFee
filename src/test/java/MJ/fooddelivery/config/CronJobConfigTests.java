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

/**
 * Unit tests for the CronJobConfig class.
 */
class CronJobConfigTests {

    /**
     * Mock object for the WeatherDataParser.
     */
    @Mock
    private WeatherDataParser weatherDataParser;

    /**
     * The instance of CronJobConfig under test, with dependencies injected.
     */
    @InjectMocks
    private CronJobConfig cronJobConfig;

    /**
     * Sets up the Mockito mocks and initializes the test instance.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Tests whether the importWeatherData method properly calls the parseAndSaveWeatherData method of WeatherDataParser.
     */
    @Test
    void importWeatherDataCallParserAndRepository() {
        cronJobConfig.importWeatherData();

        verify(weatherDataParser, times(1)).parseAndSaveWeatherData(anyString(), any());
    }
}
