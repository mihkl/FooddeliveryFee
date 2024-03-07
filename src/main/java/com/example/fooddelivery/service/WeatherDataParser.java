package com.example.fooddelivery.service;

import com.example.fooddelivery.model.WeatherData;
import com.example.fooddelivery.repository.WeatherDataRepository;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.Date;


@Service
public class WeatherDataParser {
    public static void parseAndSaveWeatherData(String xmlData, WeatherDataRepository weatherDataRepository) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            InputSource is = new InputSource(new StringReader(xmlData));
            org.w3c.dom.Document doc = builder.parse(is);

            NodeList stationList = doc.getElementsByTagName("station");

            for (int i = 0; i < stationList.getLength(); i++) {
                Element stationElement = (Element) stationList.item(i);

                String stationName = getElementTextContent(stationElement, "name");

                if (isDesiredCity(stationName)) {
                    String wmoCode = getElementTextContent(stationElement, "wmocode");
                    double airTemperature = parseDoubleElementTextContent(stationElement, "airtemperature");
                    double windSpeed = parseDoubleElementTextContent(stationElement, "windspeed");
                    String weatherPhenomenon = getElementTextContent(stationElement, "phenomenon");
                    Date timestamp = new Date();

                    WeatherData weatherData = new WeatherData(stationName, wmoCode, airTemperature, windSpeed, weatherPhenomenon, timestamp);
                    weatherDataRepository.save(weatherData);
                }
            }
        } catch (Exception e) {
            System.out.println("Error parsing data");
        }
    }
    private static boolean isDesiredCity(String cityName) {
        return cityName.equals("Tallinn-Harku") ||
                cityName.equals("Tartu-Tõravere") ||
                cityName.equals("Pärnu");
    }
    private static String getElementTextContent(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            String data = node.getTextContent();

            if (data != null && !data.isEmpty()) {return data;}
        }
        return "No data";
    }
    private static double parseDoubleElementTextContent(Element element, String tagName) {
        String textContent = getElementTextContent(element, tagName);
        if (!textContent.isEmpty()) {
            try {
                return Double.parseDouble(textContent);
            } catch (NumberFormatException e) {return 0.0;}
        }
        return 0.0;
    }
}
