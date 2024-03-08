package MJ.fooddelivery.config;

import MJ.fooddelivery.repository.WeatherDataRepository;
import MJ.fooddelivery.service.WeatherDataParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
    @Test
    void importWeatherDataCallParserAndRepository() {
        String apiUrl = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";
        String testXML = new RestTemplate().getForObject(apiUrl, String.class);

        cronJobConfig.importWeatherData();
        verify(weatherDataParser, times(1)).parseAndSaveWeatherData(testXML, weatherDataRepository);
    }
}
