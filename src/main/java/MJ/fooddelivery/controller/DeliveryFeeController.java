package MJ.fooddelivery.controller;

import MJ.fooddelivery.service.DeliveryFeeCalculator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @Tag(name = "DeliveryFee", description = "Deliveryfee API")
    @Operation(summary = "Show warning view", description = "Returns a warning view when the weather conditions are too dangerous for a certain transport.")
    @GetMapping("/warning")
    public String showWarning(){
        return "warning";
    }

    @Tag(name = "DeliveryFee", description = "Deliveryfee API")
    @Operation(summary = "Show error view", description = "Returns an error view when something goes wrong with getting data for calculating.")
    @GetMapping("/error")
    public String showError(){
        return "error";
    }

    @Tag(name = "DeliveryFee", description = "Deliveryfee API")
    @Operation(summary = "Submit input parameters for calculating fee", description = "Posts input parameters for calculating fee and return a view with the calculated results.")
    @RequestMapping(value = "/result", method = {RequestMethod.POST, RequestMethod.GET})
    //Post for submitting the form and Get for using an url like
    // http://localhost:8080/result/json?city=Tallinn-Harku&vehicleType=Bike
    public String calculateDeliveryFee(
           @RequestParam String city,
           @RequestParam String vehicleType,
           Model model)
    {
        double deliveryFee = deliveryFeeCalculator.calculateDeliveryFee(city, vehicleType);
        if (deliveryFee == -1) {
            return showWarning();
        }
        if (deliveryFee == 0) {
            return showError();
        }
        model.addAttribute("city", city);
        model.addAttribute("vehicleType", vehicleType);
        model.addAttribute("deliveryFee", deliveryFee);

        return "result";
    }
    @Tag(name = "DeliveryFee", description = "Deliveryfee API")
    @Operation(summary = "Submit input parameters for calculating fee in JSON", description = "Posts input parameters for calculating fee and return the calculated results in JSON format.")
    @RequestMapping(value = "/result/json", method = {RequestMethod.POST, RequestMethod.GET})
    //Post for submitting the form and Get for using an url like
    // http://localhost:8080/result/json?city=Tallinn-Harku&vehicleType=Bike
    @ResponseBody
    public ResponseEntity<?> calculateDeliveryFeeJSON(
            @RequestParam String city,
            @RequestParam String vehicleType)
    {
        double deliveryFee = deliveryFeeCalculator.calculateDeliveryFee(city, vehicleType);
        if (deliveryFee == -1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Warning: Weather conditions are too dangerous for a certain transport.");
        }
        if (deliveryFee == 0) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Something went wrong with getting data for calculating.");
        }
        return ResponseEntity.ok().body("{\"city\": \"" + city + "\", \"vehicleType\": \"" + vehicleType + "\", \"deliveryFee\": " + deliveryFee + "}");
    }

}

