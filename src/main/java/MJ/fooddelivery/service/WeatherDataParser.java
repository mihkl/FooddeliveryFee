package MJ.fooddelivery.service;

import MJ.fooddelivery.model.WeatherData;
import MJ.fooddelivery.repository.WeatherDataRepository;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.Date;

/**
 * Service class responsible for parsing XML weather data and saving it to the database.
 */
@Service
public class WeatherDataParser {

    /**
     * Parses the provided XML weather data and saves it to the database.
     *
     * @param xmlData              The XML data containing weather information.
     * @param weatherDataRepository The repository for storing weather data.
     */
    public void parseAndSaveWeatherData(String xmlData, WeatherDataRepository weatherDataRepository) {

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

            if (data != null && !data.isEmpty()) {
                return data;
            }
        }
        return "No data";
    }

    private static double parseDoubleElementTextContent(Element element, String tagName) {
        String textContent = getElementTextContent(element, tagName);
        if (!textContent.isEmpty()) {
            try {
                return Double.parseDouble(textContent);
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
        return 0.0;
    }
}
