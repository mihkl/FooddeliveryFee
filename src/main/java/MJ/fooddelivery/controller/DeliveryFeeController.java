package MJ.fooddelivery.controller;

import MJ.fooddelivery.service.DeliveryFeeCalculator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @Tag(name = "DeliveryFee", description = "Deliveryfee API")

    @Operation(summary = "Show Input form", description = "Return a input view with all the available options.")
    @GetMapping("/inputForm")
    public String showDeliveryFeeForm() {
        return "inputForm";
    }
    @Operation(summary = "Show warning view", description = "Returns a warning view when the weather conditions are too dangerous for a certain transport.")
    @GetMapping("/warning")
    public String showWarning(){
        return "warning";
    }
    @Operation(summary = "Show error view", description = "Returns an error view when something goes wrong with getting data for calculating.")
    @GetMapping("/error")
    public String showError(){
        return "error";
    }
    @Operation(summary = "Submit input parameters for calculating fee", description = "Posts input parameters for calculating fee and return a view with the calculated results.")
    @PostMapping("/result")
    public String calculateDeliveryFee(
           @RequestParam String city,
           @RequestParam String vehicleType, Model model) {
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
