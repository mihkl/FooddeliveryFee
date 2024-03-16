package MJ.fooddelivery.config;

import MJ.fooddelivery.repository.WeatherDataRepository;
import MJ.fooddelivery.service.WeatherDataParser;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CronJobConfig {

    private final WeatherDataParser weatherDataParser;
    private final WeatherDataRepository weatherDataRepository;
    public CronJobConfig(WeatherDataParser weatherDataParser, WeatherDataRepository weatherDataRepository) {
        this.weatherDataParser = weatherDataParser;
        this.weatherDataRepository = weatherDataRepository;}
    @PostConstruct
    public void init() {importWeatherData();}

    @Scheduled(cron = "0 * * * * *")//(cron = "0 15 * * * *") to import on the 15th minute of each hour, current value just for testing
    public void importWeatherData() {
        System.out.println("Importing weather data"); //for debugging
        String apiUrl = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";
        String weatherDataXml = new RestTemplate().getForObject(apiUrl, String.class);

        weatherDataParser.parseAndSaveWeatherData(weatherDataXml, weatherDataRepository);
    }
}
