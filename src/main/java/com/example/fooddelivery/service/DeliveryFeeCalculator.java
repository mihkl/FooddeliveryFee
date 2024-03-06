package com.example.fooddelivery.service;

import com.example.fooddelivery.model.WeatherData;
import com.example.fooddelivery.repository.WeatherDataRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
@Service
public class DeliveryFeeCalculator {
    private final WeatherDataRepository weatherDataRepository;

    public DeliveryFeeCalculator(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
    }
    public double calculateDeliveryFee(String city, String vehicleType) {
        WeatherData weatherData = weatherDataRepository.findLatestWeatherDataByStationName(city, PageRequest.of(0, 1)).getFirst();

        double baseFee = calculateBaseFee(city, vehicleType);
        double extraFee = calculateExtraFee(weatherData, vehicleType);
        if (extraFee == -1){
            return -1;
        }
        return baseFee + extraFee;
    }
    private double calculateBaseFee(String city, String vehicleType) {
        double baseFee = 0.0;
        baseFee = switch (city) {
            case "Tallinn-Harku" -> switch (vehicleType) {
                case "Car" -> 4.0;
                case "Scooter" -> 3.5;
                case "Bike" -> 3.0;
                default -> baseFee;
            };
            case "Tartu-Tõravere" -> switch (vehicleType) {
                case "Car" -> 3.5;
                case "Scooter" -> 3.0;
                case "Bike" -> 2.0;
                default -> baseFee;
            };
            case "Pärnu" -> switch (vehicleType) {
                case "Car" -> 3.0;
                case "Scooter" -> 2.5;
                case "Bike" -> 2.0;
                default -> baseFee;
            };
            default -> 0.0;
        };
        return baseFee;

    }
    private double calculateExtraFee(WeatherData weatherData, String vehicleType) {
        double windSpeed = weatherData.getWindSpeed();
        double airTemp = weatherData.getAirTemperature();
        String phenomenon = weatherData.getWeatherPhenomenon();

        double extraFee = 0.0;
        if ((vehicleType.equals("Scooter") || vehicleType.equals("Bike"))) {

            if (phenomenon.contains("Glaze") || phenomenon.contains("Thunder") || phenomenon.contains("Hail")) {return -1;}

            if (airTemp < 0.0 && airTemp > -10.0) {extraFee += 0.5;}

            else if (airTemp < -10.0) {extraFee += 1.0;}

            if (phenomenon.contains("snowfall") || phenomenon.contains("snow") || phenomenon.contains("sleet")) {extraFee += 1.0;}

            else if (phenomenon.contains("rain") || phenomenon.contains("shower") && !phenomenon.contains("snow")) {extraFee += 0.5;}

            if ((vehicleType.equals("Bike"))){

                if (windSpeed > 20 ) {return -1;}

                if (windSpeed > 10.0) {extraFee += 1.0;}
            }

        }
        return extraFee;
    }
}
