package MJ.fooddelivery.service;

import MJ.fooddelivery.model.BaseFeeCalculationRules;
import MJ.fooddelivery.model.ExtraFeeCalculationRules;
import MJ.fooddelivery.model.WeatherData;
import MJ.fooddelivery.repository.BaseFeeCalculationRulesRepository;
import MJ.fooddelivery.repository.ExtraFeeCalculationRepository;
import MJ.fooddelivery.repository.WeatherDataRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for calculating the delivery fee based on various factors including weather conditions.
 */
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

    /**
     * Calculates the delivery fee based on the specified city and vehicle type, considering weather conditions and other factors.
     *
     * @param city        The city for which the delivery fee is to be calculated.
     * @param vehicleType The type of vehicle used for delivery.
     * @return            The calculated delivery fee.
     */
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

    /**
     * Calculates the extra fee based on weather conditions and vehicle type.
     *
     * @param weatherData     The latest weather data for the delivery city.
     * @param vehicleType     The type of vehicle used for delivery.
     * @param warningWind     The wind speed threshold for warning.
     * @param windFee         The wind speed threshold for applying a wind fee.
     * @param tempSmallFee    The temperature threshold for applying a small temperature fee.
     * @param tempBigFee      The temperature threshold for applying a big temperature fee.
     * @param windFeeValue    The value of the wind fee.
     * @param tempSmallFeeValue The value of the small temperature fee.
     * @param tempBigFeeValue The value of the big temperature fee.
     * @param snowFeeValue    The value of the snow fee.
     * @param rainFeeValue    The value of the rain fee.
     * @return               The calculated extra fee.
     */
    private double calculateExtraFee(WeatherData weatherData, String vehicleType, Double warningWind, Double windFee, Double tempSmallFee,
                                     Double tempBigFee, Double windFeeValue, Double tempSmallFeeValue, Double tempBigFeeValue, Double snowFeeValue, Double rainFeeValue) {
        double windSpeed = weatherData.getWindSpeed();
        double airTemp = weatherData.getAirTemperature();
        String phenomenon = weatherData.getWeatherPhenomenon();

        double extraFee = 0.0;

        if ((vehicleType.equals("Scooter") || vehicleType.equals("Bike"))) {
            if (phenomenon.contains("Glaze") || phenomenon.contains("Thunder") || phenomenon.contains("Hail")) {
                return -1;
            }
            if (airTemp < tempSmallFee && airTemp > tempBigFee) {
                extraFee += tempSmallFeeValue;
            } else if (airTemp < tempBigFee) {
                extraFee += tempBigFeeValue;
            }

            if (phenomenon.contains("snowfall") || phenomenon.contains("snow") || phenomenon.contains("sleet")) {
                extraFee += snowFeeValue;
            } else if (phenomenon.contains("rain") || phenomenon.contains("shower") && !phenomenon.contains("snow")) {
                extraFee += rainFeeValue;
            }

            if ((vehicleType.equals("Bike"))) {
                if (windSpeed > warningWind) {
                    return -1;
                }
                if (windSpeed > windFee) {
                    extraFee += windFeeValue;
                }
            }
        }
        return extraFee;
    }
}
