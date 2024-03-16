### Application to calculate the delivery fee for a certain city and vehicle based on waether data from the Estonian Environment Agency

## Project layout
The project consists of:

### A CronJob class to periodically import weather data.

### 2 Controllers
DeliveyFeeController for managing the main flow of the application, this handles requests for calculating data (you can make a request through the html page or using a URL like http://localhost:8080/result/json?city=Tallinn-Harku&vehicleType=Bike).
The response is either given as JSON or a formatted HTML view containing the response, up to the user to decide which they prefer.

FeeCalculationController is responsible for updating the business rules in the H2 Database on which fee calculations are done.

Both controllers are documented with Swagger and the endpoints can be tested when running the program and navigating to http://localhost:8080/swagger-ui/index.html#/

### 3 models
BaseFeeCalculationRules models the basefee entity in the database.

ExtraFeeCalculationRules models the extrafee entity in the database.

WeatherData models the weatherdata entity in the database.

### 3 Repositories
Each model has a repository for it.

BaseFeeCalculationRulesRepository
ExtraFeeCalculationRulesRepository
WeatherDataRepository

These repositories allow communication with database tables (saving new data to them and also retrieving it later for use in calculations).

### 2 Services

WeatherDataParser which parses the raw data imported by the CronJob and parses it to a usable form and saves it to the database using WeatherData and WeatherDataRepository
The parser only extracts the data which is relevant to the chosen cities (Tallinn, Tartu and Pärnu).
If any data is missing or invalid it either replaces it with placeholder values or returns an error to the controller.

DeliveryFeeCalculator which contains all of the logic for calculating the fee. It takes in all of the business rules and weatherdata from database tables and calculates the fee.
The fee calculation is split into 2 parts:
BaseFee and ExtraFee

##FoodDeliveryApplication
This is the main class which the application is started from.

##Tests
The Cronjob, Controllers and Services have tests written for them. The tests do not cover all scenarios, but they do cover most common use cases.
