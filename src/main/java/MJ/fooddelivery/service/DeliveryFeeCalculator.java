
package MJ.fooddelivery.service;

import MJ.fooddelivery.model.BaseFeeCalculationRules;
import MJ.fooddelivery.model.ExtraFeeCalculationRules;
import MJ.fooddelivery.model.WeatherData;
import MJ.fooddelivery.repository.BaseFeeCalculationRulesRepository;
import MJ.fooddelivery.repository.ExtraFeeCalculationRepository;
import MJ.fooddelivery.repository.WeatherDataRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
@Service
public class DeliveryFeeCalculator {
    private final WeatherDataRepository weatherDataRepository;
    private final BaseFeeCalculationRulesRepository baseFeeCalculationRulesRepository;
    private final ExtraFeeCalculationRepository extraFeeCalculationRepository;

    public DeliveryFeeCalculator(WeatherDataRepository weatherDataRepository, BaseFeeCalculationRulesRepository baseFeeCalculationRulesRepository,
                                 ExtraFeeCalculationRepository extraFeeCalculationRepository) {
        this.weatherDataRepository = weatherDataRepository;
        this.baseFeeCalculationRulesRepository = baseFeeCalculationRulesRepository;
        this.extraFeeCalculationRepository = extraFeeCalculationRepository;
    }
    public double calculateDeliveryFee(String city, String vehicleType) {
        WeatherData weatherData = weatherDataRepository.findLatestWeatherDataByStationName(city, PageRequest.of(0, 1)).getFirst();
        if (weatherData == null) {
            return 0;
        }
        ExtraFeeCalculationRules extraFeeRules = extraFeeCalculationRepository.findById(1L).orElse(null);
        if (extraFeeRules == null) {
            return 0;
        }
        BaseFeeCalculationRules baseFeeRules = baseFeeCalculationRulesRepository.findBaseFeeByCityAndVehicleType(city, vehicleType);
        if (baseFeeRules == null) {
            return 0;
        }
        double baseFee = baseFeeRules.getBaseFee();

        Double warningWind = extraFeeRules.getWindWarning();
        Double windFee = extraFeeRules.getWindFee();
        Double tempSmallFee = extraFeeRules.getTempSmallFee();
        Double tempBigFee = extraFeeRules.getTempBigFee();

        Double windFeeValue = extraFeeRules.getWindFeeValue();
        Double tempSmallFeeValue = extraFeeRules.getTempSmallFeeValue();
        Double tempBigFeeValue = extraFeeRules.getTempBigFeeValue();
        Double snowFeeValue = extraFeeRules.getSnowFeeValue();
        Double rainFeeValue = extraFeeRules.getRainFeeValue();

        double extraFee = calculateExtraFee(weatherData, vehicleType, warningWind, windFee, tempSmallFee, tempBigFee,
                windFeeValue, tempSmallFeeValue, tempBigFeeValue, snowFeeValue, rainFeeValue);
        if (extraFee == -1) {
            return -1;
        }
        return baseFee + extraFee;
    }
    private double calculateExtraFee(WeatherData weatherData, String vehicleType, Double warningWind, Double windFee, Double tempSmallFee,
                                     Double tempBigFee, Double windFeeValue, Double tempSmallFeeValue, Double tempBigFeeValue, Double snowFeeValue, Double rainFeeValue) {
        double windSpeed = weatherData.getWindSpeed();
        double airTemp = weatherData.getAirTemperature();
        String phenomenon = weatherData.getWeatherPhenomenon();

        System.out.println(phenomenon);

        double extraFee = 0.0;
        if ((vehicleType.equals("Scooter") || vehicleType.equals("Bike"))) {

            if (phenomenon.contains("Glaze") || phenomenon.contains("Thunder") || phenomenon.contains("Hail")) {return -1;}
            if (airTemp < tempSmallFee && airTemp > tempBigFee) {extraFee += tempSmallFeeValue;}
            else if (airTemp < tempBigFee) {extraFee += tempBigFeeValue;}

            if (phenomenon.contains("snowfall") || phenomenon.contains("snow") || phenomenon.contains("sleet")) {extraFee += snowFeeValue;}
            else if (phenomenon.contains("rain") || phenomenon.contains("shower") && !phenomenon.contains("snow")) {extraFee += rainFeeValue;}

            if ((vehicleType.equals("Bike"))){

                if (windSpeed > warningWind ) {return -1;}
                if (windSpeed > windFee) {extraFee += windFeeValue;}
            }
        }
        System.out.println(extraFee);
        return extraFee;
    }
}
