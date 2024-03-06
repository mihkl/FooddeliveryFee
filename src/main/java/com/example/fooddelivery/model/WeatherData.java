package com.example.fooddelivery.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;

@Entity
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stationName;
    private String wmoCode;
    private double airTemperature;
    private double windSpeed;
    private String weatherPhenomenon;
    private Date timestamp;

    // Constructors
    public WeatherData() {
    }
    public WeatherData(String stationName, String wmoCode, double airTemperature, double windSpeed, String weatherPhenomenon, Date timestamp) {
        this.stationName = stationName;
        this.wmoCode = wmoCode;
        this.airTemperature = airTemperature;
        this.windSpeed = windSpeed;
        this.weatherPhenomenon = weatherPhenomenon;
        this.timestamp = timestamp;
    }

    public double getAirTemperature() {
        return airTemperature;
    }
    public double getWindSpeed() {
        return windSpeed;
    }
    public String getWeatherPhenomenon() {
        return weatherPhenomenon;
    }
}
