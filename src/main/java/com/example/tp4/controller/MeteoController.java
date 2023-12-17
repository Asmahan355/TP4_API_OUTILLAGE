package com.example.tp4.controller;


import com.example.tp4.mapper.FeatureCollection;
import com.example.tp4.model.WeatherResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MeteoController {

    @Value("${api.meteo.token}")
    private  String apiMeteoToken;

    @PostMapping("/meteo")
    public String getWeather(@RequestParam("address") String address, Model model) throws JsonProcessingException {
        /**
         * Faire appel a l'api addresse pour recuperer les donneés de la commune a rechercer
         * longitude et latitude pour faire passer ces params a l'api meteo
         */
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://api-adresse.data.gouv.fr/search/?q={address}&limit=1";
        String jsonResponse = restTemplate.getForObject(apiUrl, String.class, address);

        String jsonString = jsonResponse;
        ObjectMapper objectMapper = new ObjectMapper();
        FeatureCollection featureCollection = objectMapper.readValue(jsonString, FeatureCollection.class);


        String city = featureCollection.getFeatures().get(0).getProperties().getLabel();
        double longitude = featureCollection.getFeatures().get(0).getGeometry().getCoordinates().get(0);
        double latitude = featureCollection.getFeatures().get(0).getGeometry().getCoordinates().get(1) ;

        /**
         * faire passer les params longitude et latitude a l'api meteo
         */
        WeatherResponse weatherResponse = callWeatherApi(longitude, latitude);

        System.out.println(weatherResponse.getForecast().getWeather());

        //ici on transforme les donnes vers le model pour le template meteo

        return "meteo"; // Retourner le nom du template Thymeleaf associé
    }


    private  WeatherResponse callWeatherApi(double lng, double lat) throws JsonProcessingException {
        WeatherResponse weatherResponse = new WeatherResponse();
        // Define the API endpoint
        String apiUrl = "https://api.meteo-concept.com/api/forecast/daily/{day}";
        // Set the day parameter value (e.g., 0 for today)
        int day = 0;
        // Set the latitude and longitude for the latlng parameter
        String latlng = lat +","+ lng;

        // Set the authentication token
        String token = apiMeteoToken;

        // Create a map to hold the parameters
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("day", String.valueOf(day));
        uriVariables.put("latlng", latlng);
        uriVariables.put("token", token);

        // Create HttpHeaders and set the accept and X-AUTH-TOKEN headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("X-AUTH-TOKEN", token);

        // Create a RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Make the API call
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                new org.springframework.http.HttpEntity<>(headers),
                String.class,
                uriVariables
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String responseBody = responseEntity.getBody();

            /**
             * there was an error parsing date, the response from the api generate a non validate date (without colon)
             * and OffsetDateTime class does not handle time zone offsets without a colon,
             */
            responseBody = responseBody.replaceFirst("(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2})([+-]\\d{2})(\\d{2})", "$1$2:$3");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            weatherResponse = objectMapper.readValue(responseBody, WeatherResponse.class);
        } else {
            weatherResponse=null;
        }
        return weatherResponse;
    }
}
