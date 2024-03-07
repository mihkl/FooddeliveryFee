package com.example.fooddelivery.controller;

import com.example.fooddelivery.service.DeliveryFeeCalculator;
import com.example.fooddelivery.service.WeatherDataParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

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

    }
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
    void noErrorCalculationSuccessfulReturnResultView() {

        when(deliveryFeeCalculator.calculateDeliveryFee(anyString(), anyString())).thenReturn(10.0);

        String viewName = deliveryFeeController.calculateDeliveryFee("Tallinn", "Car", model);

        assertEquals("result", viewName);
    }
}

