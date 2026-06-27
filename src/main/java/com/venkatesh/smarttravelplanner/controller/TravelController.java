package com.venkatesh.smarttravelplanner.controller;

import com.venkatesh.smarttravelplanner.service.WeatherService;
import com.venkatesh.smarttravelplanner.service.SearchHistoryService;
import org.springframework.beans.factory.annotation.Value;
import com.venkatesh.smarttravelplanner.dto.AirQualityResponse;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import java.util.List;
import com.venkatesh.smarttravelplanner.entity.SearchHistory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.venkatesh.smarttravelplanner.dto.WeatherResponse;
import com.venkatesh.smarttravelplanner.service.AQIService;

@Controller
public class TravelController {

	private final WeatherService weatherService;
	private final SearchHistoryService searchHistoryService;
	private final AQIService aqiService;

	public TravelController(WeatherService weatherService,
            SearchHistoryService searchHistoryService,
            AQIService aqiService) {

			this.weatherService = weatherService;
			this.searchHistoryService = searchHistoryService;
			this.aqiService = aqiService;
	}
	
    @GetMapping("/")
    public String home() {

        return "index";

    }

    @GetMapping("/weather")
    public String getWeather(@RequestParam String city, Model model) {

    	WeatherResponse weather = weatherService.getWeather(city);
    	
    	double lat = weather.getCoord().getLat();
    	double lon = weather.getCoord().getLon();
    	
    	model.addAttribute("latitude", lat);
    	model.addAttribute("longitude", lon);

    	AirQualityResponse airQuality =
    	        aqiService.getAirQuality(lat, lon, apiKey);

    	int aqi = airQuality.getList().get(0).getMain().getAqi();

    	model.addAttribute("aqi", aqi);
    	
        model.addAttribute("weather", weather);

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("hh:mm a")
                                 .withZone(ZoneId.systemDefault());

        String sunrise = formatter.format(
                Instant.ofEpochSecond(weather.getSys().getSunrise()));

        String sunset = formatter.format(
                Instant.ofEpochSecond(weather.getSys().getSunset()));

        model.addAttribute("sunrise", sunrise);
        model.addAttribute("sunset", sunset);

        model.addAttribute("forecast",
                weatherService.getDailyForecast(city));

        return "index";
    }
    
    @GetMapping("/history")
    public String history(Model model) {

    	List<SearchHistory> history =
    	        searchHistoryService.getSearchHistory();

        model.addAttribute("history", history);

        model.addAttribute("totalSearches",
        		searchHistoryService.getTotalSearches());

        model.addAttribute("totalCities",
        		searchHistoryService.getTotalCities());

        model.addAttribute("averageTemperature",
                String.format("%.1f", searchHistoryService.getAverageTemperature()));

        return "history";
    }
    
    @Value("${weather.api.key}")
    private String apiKey;

}