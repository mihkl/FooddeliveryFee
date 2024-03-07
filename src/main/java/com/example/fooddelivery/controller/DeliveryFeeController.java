package com.example.fooddelivery.controller;

import com.example.fooddelivery.service.DeliveryFeeCalculator;
import com.example.fooddelivery.service.WeatherDataParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DeliveryFeeController {
    private final DeliveryFeeCalculator deliveryFeeCalculator;

    public DeliveryFeeController(DeliveryFeeCalculator deliveryFeeCalculator) {
        this.deliveryFeeCalculator = deliveryFeeCalculator;
    }

    @GetMapping("/delivery-fee")
    public String showDeliveryFeeForm() {
        return "inputForm";
    }

    @GetMapping("/warning")
    public String showWarning(){
        return "warning";
    }

    @GetMapping("/error")
    public String showError(){
        return "error";
    }

    @PostMapping("/calculate-delivery-fee")
    public String calculateDeliveryFee(@RequestParam String city,
                                       @RequestParam String vehicleType,
                                       Model model) {
        double deliveryFee = deliveryFeeCalculator.calculateDeliveryFee(city, vehicleType);
        if (deliveryFee == -1){
            return showWarning();
        }
        if(deliveryFee == 0){
            return showError();
        }
        model.addAttribute("city", city);
        model.addAttribute("vehicleType", vehicleType);
        model.addAttribute("deliveryFee", deliveryFee);
        return "result";
    }
}
