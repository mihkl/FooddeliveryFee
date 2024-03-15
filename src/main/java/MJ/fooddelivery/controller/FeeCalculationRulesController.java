package MJ.fooddelivery.controller;

import MJ.fooddelivery.model.ExtraFeeCalculationRules;
import MJ.fooddelivery.model.BaseFeeCalculationRules;
import MJ.fooddelivery.repository.BaseFeeCalculationRulesRepository;
import MJ.fooddelivery.repository.ExtraFeeCalculationRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fee-rules")
public class FeeCalculationRulesController {

    private final BaseFeeCalculationRulesRepository baseFeeCalculationRulesRepository;
    private final ExtraFeeCalculationRepository extraFeeCalculationRepository;
    @Autowired
    public FeeCalculationRulesController(BaseFeeCalculationRulesRepository baseFeeCalculationRulesRepository, ExtraFeeCalculationRepository extraFeeCalculationRepository) {
        this.baseFeeCalculationRulesRepository = baseFeeCalculationRulesRepository;
        this.extraFeeCalculationRepository = extraFeeCalculationRepository;
    }
    @Tag(name = "FeeCalculationRules", description = "FeeCalculationRules API")
    @Operation(summary = "Edit base fee of a certain city and vehicle", description = "Edit base fee (must be a positive number or database will not get updated)" +
            " of a certain city and vehicle by submitting the city and " +
            "vehicle type parameters and the new desired base fee for those conditions")
    @PutMapping("/{city}/{vehicleType}")
    public ResponseEntity<String> updateBaseFee(@PathVariable String city,
                                                @PathVariable String vehicleType,
                                                @RequestParam @Positive double baseFee) {
        BaseFeeCalculationRules baseFeeRules = baseFeeCalculationRulesRepository.findBaseFeeByCityAndVehicleType(city, vehicleType);
        if (baseFeeRules != null) {
            baseFeeRules.setBaseFee(baseFee);
            baseFeeCalculationRulesRepository.save(baseFeeRules);
            return ResponseEntity.ok("Base fee updated successfully for city: " + city + " and vehicle type: " + vehicleType);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Base fee rule not found for city: " + city + " and vehicle type: " + vehicleType);
        }
    }
    @Tag(name = "FeeCalculationRules", description = "FeeCalculationRules API")
    @Operation(summary = "Update extra fee calculation rules", description = "Update extra fee calculation rules with new values (all variables ending in Value" +
            " must be positive, because we cant have a negative extra fee and also wind related variables must be positive as negative wind speed doesnt exist)")
    @PutMapping("/updateExtraFee")
    public ResponseEntity<String> updateExtraFeeRules(
            @RequestParam double tempBigFee,
            @RequestParam @Positive double tempBigFeeValue,
            @RequestParam double tempSmallFee,
            @RequestParam @Positive double tempSmallFeeValue,
            @RequestParam @Positive double windFee,
            @RequestParam @Positive double windFeeValue,
            @RequestParam @Positive double windWarning) {

        List<ExtraFeeCalculationRules> rules = extraFeeCalculationRepository.findAll();
        for (ExtraFeeCalculationRules rule : rules) {
            rule.setTempBigFee(tempBigFee);
            rule.setTempBigFeeValue(tempBigFeeValue);
            rule.setTempSmallFee(tempSmallFee);
            rule.setTempSmallFeeValue(tempSmallFeeValue);
            rule.setWindFee(windFee);
            rule.setWindFeeValue(windFeeValue);
            rule.setWindWarning(windWarning);
            extraFeeCalculationRepository.save(rule);
        }

        return ResponseEntity.ok("Extra fee calculation rules updated successfully");
    }
}
