package com.venkatesh.smarttravelplanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.venkatesh.smarttravelplanner.dto.AirQualityResponse;

@Service
public class AQIService {

    @Autowired
    private RestTemplate restTemplate;

    public AirQualityResponse getAirQuality(double lat, double lon, String apiKey) {

        String url = "https://api.openweathermap.org/data/2.5/air_pollution"
                + "?lat=" + lat
                + "&lon=" + lon
                + "&appid=" + apiKey;

        return restTemplate.getForObject(url, AirQualityResponse.class);
    }
}