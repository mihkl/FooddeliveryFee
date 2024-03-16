package MJ.fooddelivery.controller;

import MJ.fooddelivery.model.BaseFeeCalculationRules;
import MJ.fooddelivery.model.ExtraFeeCalculationRules;
import MJ.fooddelivery.repository.BaseFeeCalculationRulesRepository;
import MJ.fooddelivery.repository.ExtraFeeCalculationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FeeCalculationRulesControllerTests {

    @Mock
    private BaseFeeCalculationRulesRepository baseFeeCalculationRulesRepository;

    @Mock
    private ExtraFeeCalculationRepository extraFeeCalculationRepository;

    @InjectMocks
    private FeeCalculationRulesController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void updateBaseFeeSuccess() {

        String city = "TestCity";
        String vehicleType = "Car";
        double baseFee = 10.0;

        BaseFeeCalculationRules baseFeeRules = new BaseFeeCalculationRules();
        when(baseFeeCalculationRulesRepository.findBaseFeeByCityAndVehicleType(city, vehicleType)).thenReturn(baseFeeRules);

        ResponseEntity<String> response = controller.updateBaseFee(city, vehicleType, baseFee);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Base fee updated successfully for city: TestCity and vehicle type: Car", response.getBody());
        verify(baseFeeCalculationRulesRepository, times(1)).save(baseFeeRules);
    }
    @Test
    void updateBaseFeeError() {

        String city = "NonExistingCity";
        String vehicleType = "Car";
        double baseFee = 10.0;

        when(baseFeeCalculationRulesRepository.findBaseFeeByCityAndVehicleType(city, vehicleType)).thenReturn(null);

        ResponseEntity<String> response = controller.updateBaseFee(city, vehicleType, baseFee);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Base fee rule not found for city: NonExistingCity and vehicle type: Car", response.getBody());
        verify(baseFeeCalculationRulesRepository, never()).save(any());
    }
    @Test
    void updateExtraFeeRulesSuccess() {

        double tempBigFee = 5.0;
        double tempBigFeeValue = 15.0;
        double tempSmallFee = 3.0;
        double tempSmallFeeValue = 10.0;
        double windFee = 2.0;
        double windFeeValue = 8.0;
        double windWarning = 20.0;

        List<ExtraFeeCalculationRules> rules = new ArrayList<>();
        rules.add(new ExtraFeeCalculationRules());
        when(extraFeeCalculationRepository.findAll()).thenReturn(rules);

        ResponseEntity<String> response = controller.updateExtraFeeRules(
                tempBigFee, tempBigFeeValue, tempSmallFee, tempSmallFeeValue, windFee, windFeeValue, windWarning);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Extra fee calculation rules updated successfully", response.getBody());
        verify(extraFeeCalculationRepository, times(1)).save(any());
    }
}