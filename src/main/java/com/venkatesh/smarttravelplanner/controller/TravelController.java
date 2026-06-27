package com.venkatesh.smarttravelplanner.controller;
import com.venkatesh.smarttravelplanner.dto.ForecastResponse;
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
import com.venkatesh.smarttravelplanner.service.SearchHistoryService;

@Controller
public class TravelController {

    private final SearchHistoryService service;

    public TravelController(SearchHistoryService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home() {

        return "index";

    }

    @GetMapping("/weather")
    public String getWeather(@RequestParam String city, Model model) {

        WeatherResponse weather = service.getWeather(city);

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
                service.getDailyForecast(city));

        return "index";
    }
    
    @GetMapping("/history")
    public String history(Model model) {

        List<SearchHistory> history = service.getSearchHistory();

        model.addAttribute("history", history);

        model.addAttribute("totalSearches",
                service.getTotalSearches());

        model.addAttribute("totalCities",
                service.getTotalCities());

        model.addAttribute("averageTemperature",
                String.format("%.1f", service.getAverageTemperature()));

        return "history";
    }

}