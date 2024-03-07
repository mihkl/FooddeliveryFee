package com.example.fooddelivery.config;

import com.example.fooddelivery.repository.WeatherDataRepository;
import com.example.fooddelivery.service.WeatherDataParser;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CronJobConfig {
    private final WeatherDataRepository weatherDataRepository;

    public CronJobConfig(WeatherDataRepository weatherDataRepository) {this.weatherDataRepository = weatherDataRepository;}

    @PostConstruct
    public void init() {importWeatherData();}

    @Scheduled(cron = "0 * * * * *")//(cron = "0 15 * * * *")
    public void importWeatherData() {
        System.out.println("Importing weather data");
        String apiUrl = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";
        String weatherDataXml = new RestTemplate().getForObject(apiUrl, String.class);

        WeatherDataParser.parseAndSaveWeatherData(weatherDataXml, weatherDataRepository);
    }
}
