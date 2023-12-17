package com.example.tp4.config;

import com.example.tp4.model.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class WeatherResponseParser {
    public static void main(String[] args) {
        String jsonResponse = "{\n" +
                "  \"city\": {\n" +
                "    \"insee\": \"22288\",\n" +
                "    \"cp\": 22210,\n" +
                "    \"name\": \"Saint-Étienne-du-Gué-de-l'Isle\",\n" +
                "    \"latitude\": 48.0853,\n" +
                "    \"longitude\": -2.6433,\n" +
                "    \"altitude\": 102\n" +
                "  },\n" +
                "  \"update\": \"2023-12-17T09:13:24+01:00\",\n" +
                "  \"forecast\": {\n" +
                "    \"insee\": \"22288\",\n" +
                "    \"cp\": 22210,\n" +
                "    \"latitude\": 48.0853,\n" +
                "    \"longitude\": -2.6433,\n" +
                "    \"day\": 0,\n" +
                "    \"datetime\": \"2023-12-17T01:00:00+0100\",\n" +
                "    \"wind10m\": 10,\n" +
                "    \"gust10m\": 26,\n" +
                "    \"dirwind10m\": 143,\n" +
                "    \"rr10\": 0,\n" +
                "    \"rr1\": 0,\n" +
                "    \"probarain\": 10,\n" +
                "    \"weather\": 3,\n" +
                "    \"tmin\": -1,\n" +
                "    \"tmax\": 8,\n" +
                "    \"sun_hours\": 5,\n" +
                "    \"etp\": 0,\n" +
                "    \"probafrost\": 20,\n" +
                "    \"probafog\": 60,\n" +
                "    \"probawind70\": 0,\n" +
                "    \"probawind100\": 0,\n" +
                "    \"gustx\": 26\n" +
                "  }\n" +
                "}";

        jsonResponse = jsonResponse.replaceFirst("(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2})([+-]\\d{2})(\\d{2})", "$1$2:$3");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Register JavaTimeModule to handle dates

        try {
            WeatherResponse weatherData = objectMapper.readValue(jsonResponse, WeatherResponse.class);
            System.out.println(weatherData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}