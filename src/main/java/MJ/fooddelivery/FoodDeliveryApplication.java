package MJ.fooddelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
/**
 * The main class to start the Food Delivery application.
 */
@SpringBootApplication
@EnableScheduling
public class FoodDeliveryApplication {
	public static void main(String[] args)
	{SpringApplication.run(FoodDeliveryApplication.class, args);}}




