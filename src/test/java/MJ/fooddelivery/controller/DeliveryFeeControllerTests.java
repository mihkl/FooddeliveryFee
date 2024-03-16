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

/**
 * Unit tests for the DeliveryFeeController class.
 */
@ExtendWith(MockitoExtension.class)
class DeliveryFeeControllerTests {

    /**
     * Mock object for the DeliveryFeeCalculator.
     */
    @Mock
    private DeliveryFeeCalculator deliveryFeeCalculator;

    /**
     * Mock object for the Model.
     */
    @Mock
    private Model model;

    /**
     * The instance of DeliveryFeeController under test, with dependencies injected.
     */
    @InjectMocks
    private DeliveryFeeController deliveryFeeController;

    /**
     * Initializes the mocks and the instance under test before each test method.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        deliveryFeeController = new DeliveryFeeController(deliveryFeeCalculator);
    }

    /**
     * Tests whether the showDeliveryFeeForm method returns the expected view.
     */
    @Test
    void showDeliveryFeeFormReturnInputFormView() {
        String viewName = deliveryFeeController.showDeliveryFeeForm();
        assertEquals("inputForm", viewName);
    }

    /**
     * Tests whether the showWarning method returns the expected view.
     */
    @Test
    void showWarningReturnWarningView() {
        String viewName = deliveryFeeController.showWarning();
        assertEquals("warning", viewName);
    }

    /**
     * Tests whether the showError method returns the expected view.
     */
    @Test
    void showErrorReturnErrorView() {
        String viewName = deliveryFeeController.showError();
        assertEquals("error", viewName);
    }

    /**
     * Tests whether the calculateDeliveryFee method returns the warning view when fee equals -1.
     */
    @Test
    void feeEqualsMinusOneReturnWarningView() {
        when(deliveryFeeCalculator.calculateDeliveryFee(anyString(), anyString())).thenReturn(-1.0);
        String viewName = deliveryFeeController.calculateDeliveryFee("Tallinn", "Car", model);
        assertEquals("warning", viewName);
    }

    /**
     * Tests whether the calculateDeliveryFee method returns the error view when parsing fails.
     */
    @Test
    void parsingFailedReturnErrorView() {
        when(deliveryFeeCalculator.calculateDeliveryFee(anyString(), anyString())).thenReturn(0.0);
        String viewName = deliveryFeeController.calculateDeliveryFee("Tallinn", "Car", model);
        assertEquals("error", viewName);
    }

    /**
     * Tests whether the calculateDeliveryFee method returns the result view when calculation is successful.
     */
    @Test
    void calculationSuccessfulReturnResultView() {
        when(deliveryFeeCalculator.calculateDeliveryFee(anyString(), anyString())).thenReturn(10.0);
        String viewName = deliveryFeeController.calculateDeliveryFee("Tallinn", "Car", model);
        assertEquals("result", viewName);
    }

    /**
     * Tests whether the calculateDeliveryFeeJSON method returns the expected JSON response when calculation is successful.
     */
    @Test
    public void calculateDeliveryFeeJSONSuccess() {
        when(deliveryFeeCalculator.calculateDeliveryFee(anyString(), anyString())).thenReturn(10.0);
        ResponseEntity<?> responseEntity = deliveryFeeController.calculateDeliveryFeeJSON("Tallinn-Harku", "Car");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("{\"city\": \"Tallinn-Harku\", \"vehicleType\": \"Car\", \"deliveryFee\": 10.0}", responseEntity.getBody());
    }

    /**
     * Tests whether the calculateDeliveryFeeJSON method returns the expected JSON response when weather warning occurs.
     */
    @Test
    public void calculateDeliveryFeeJSONWeatherWarning() {
        when(deliveryFeeCalculator.calculateDeliveryFee(anyString(), anyString())).thenReturn(-1.0);
        ResponseEntity<?> responseEntity = deliveryFeeController.calculateDeliveryFeeJSON("Tallinn-Harku", "Car");
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Warning: Weather conditions are too dangerous for a certain transport.", responseEntity.getBody());
    }

    /**
     * Tests whether the calculateDeliveryFeeJSON method returns the expected JSON response when an internal server error occurs.
     */
    @Test
    public void calculateDeliveryFeeJSONInternalServerError() {
        when(deliveryFeeCalculator.calculateDeliveryFee(anyString(), anyString())).thenReturn(0.0);
        ResponseEntity<?> responseEntity = deliveryFeeController.calculateDeliveryFeeJSON("Tallinn-Harku", "Car");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Error: Something went wrong with getting data for calculating.", responseEntity.getBody());
    }
}
