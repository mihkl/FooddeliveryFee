package MJ.fooddelivery.model;

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
    public String getStationName() {
        return stationName;
    }
    public String getWmoCode() {
        return wmoCode;
    }
    public Date getTimestamp() {
        return timestamp;
    }

    public void setWmoCode(String wmoCode) {
        this.wmoCode = wmoCode;
    }
    public void setAirTemperature(double airTemperature) {
        this.airTemperature = airTemperature;
    }
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }
    public void setWeatherPhenomenon(String phenomenon) {
        this.weatherPhenomenon = phenomenon;
    }
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
