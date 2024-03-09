package MJ.fooddelivery.controller;

import MJ.fooddelivery.service.DeliveryFeeCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeliveryFeeControllerTests {
    @Mock
    private DeliveryFeeCalculator deliveryFeeCalculator;
    @Mock
    private Model model;
    @InjectMocks
    private DeliveryFeeController deliveryFeeController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        deliveryFeeController = new DeliveryFeeController(deliveryFeeCalculator);
    }
    //Tests for returning Views
    @Test
    void showDeliveryFeeFormReturnInputFormView() {

        String viewName = deliveryFeeController.showDeliveryFeeForm();

        assertEquals("inputForm", viewName);
    }
    @Test
    void showWarningReturnWarningView() {

        String viewName = deliveryFeeController.showWarning();

        assertEquals("warning", viewName);
    }
    @Test
    void showErrorReturnErrorView() {

        String viewName = deliveryFeeController.showError();

        assertEquals("error", viewName);
    }
    @Test
    void feeEqualsMinusOneReturnWarningView() {

        when(deliveryFeeCalculator.calculateDeliveryFee(anyString(), anyString())).thenReturn(-1.0);

        String viewName = deliveryFeeController.calculateDeliveryFee("Tallinn", "Car", model);

        assertEquals("warning", viewName);
    }
    @Test
    void parsingFailedReturnErrorView() {

        when(deliveryFeeCalculator.calculateDeliveryFee(anyString(), anyString())).thenReturn(0.0);

        String viewName = deliveryFeeController.calculateDeliveryFee("Tallinn", "Car", model);

        assertEquals("error", viewName);
    }
    @Test
    void calculationSuccessfulReturnResultView() {

        when(deliveryFeeCalculator.calculateDeliveryFee(anyString(), anyString())).thenReturn(10.0);

        String viewName = deliveryFeeController.calculateDeliveryFee("Tallinn", "Car", model);

        assertEquals("result", viewName);
    }
    //Tests for JSON return
    @Test
    public void calculateDeliveryFeeJSONSuccess() {
        when(deliveryFeeCalculator.calculateDeliveryFee(anyString(), anyString())).thenReturn(10.0);

        ResponseEntity<?> responseEntity = deliveryFeeController.calculateDeliveryFeeJSON("Tallinn-Harku", "Car");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("{\"city\": \"Tallinn-Harku\", \"vehicleType\": \"Car\", \"deliveryFee\": 10.0}", responseEntity.getBody());
    }
    @Test
    public void calculateDeliveryFeeJSONWeatherWarning() {

        when(deliveryFeeCalculator.calculateDeliveryFee(anyString(), anyString())).thenReturn(-1.0);

        ResponseEntity<?> responseEntity = deliveryFeeController.calculateDeliveryFeeJSON("Tallinn-Harku", "Car");

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Warning: Weather conditions are too dangerous for a certain transport.", responseEntity.getBody());
    }
    @Test
    public void calculateDeliveryFeeJSONInternalServerError() {

        when(deliveryFeeCalculator.calculateDeliveryFee(anyString(), anyString())).thenReturn(0.0);

        ResponseEntity<?> responseEntity = deliveryFeeController.calculateDeliveryFeeJSON("Tallinn-Harku", "Car");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Error: Something went wrong with getting data for calculating.", responseEntity.getBody());
    }
}

