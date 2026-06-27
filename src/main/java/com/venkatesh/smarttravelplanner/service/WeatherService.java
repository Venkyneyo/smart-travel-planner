package com.venkatesh.smarttravelplanner.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.venkatesh.smarttravelplanner.dto.DailyForecast;
import com.venkatesh.smarttravelplanner.dto.ForecastItem;
import com.venkatesh.smarttravelplanner.dto.ForecastResponse;
import com.venkatesh.smarttravelplanner.dto.WeatherResponse;
import com.venkatesh.smarttravelplanner.entity.SearchHistory;
import com.venkatesh.smarttravelplanner.repository.SearchHistoryRepository;

@Service
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SearchHistoryRepository repository;

    @Value("${weather.api.key}")
    private String apiKey;

    public WeatherResponse getWeather(String city) {

        String url = "https://api.openweathermap.org/data/2.5/weather"
                + "?q=" + city
                + "&appid=" + apiKey
                + "&units=metric";

        WeatherResponse response =
                restTemplate.getForObject(url, WeatherResponse.class);

        saveSearchHistory(response);

        return response;
    }

    public ForecastResponse getForecast(String city) {

        String url = "https://api.openweathermap.org/data/2.5/forecast"
                + "?q=" + city
                + "&appid=" + apiKey
                + "&units=metric";

        return restTemplate.getForObject(url, ForecastResponse.class);
    }

    public List<DailyForecast> getDailyForecast(String city) {

        ForecastResponse response = getForecast(city);

        List<DailyForecast> dailyForecasts = new ArrayList<>();

        Set<String> addedDates = new HashSet<>();

        for (ForecastItem item : response.getList()) {

            String date = item.getDt_txt().split(" ")[0];

            if (!addedDates.contains(date)) {

                addedDates.add(date);

                DailyForecast daily = new DailyForecast();

                daily.setDate(date);
                daily.setTemperature(item.getMain().getTemp());
                daily.setDescription(item.getWeather().get(0).getDescription());
                daily.setIcon(item.getWeather().get(0).getIcon());

                dailyForecasts.add(daily);
            }

            if (dailyForecasts.size() == 5) {
                break;
            }
        }

        return dailyForecasts;
    }

    private void saveSearchHistory(WeatherResponse response) {

        SearchHistory history = new SearchHistory();

        history.setCity(response.getName());
        history.setCountry(response.getSys().getCountry());
        history.setTemperature(response.getMain().getTemp());
        history.setHumidity(response.getMain().getHumidity());
        history.setWeather(response.getWeather().get(0).getDescription());
        history.setWindSpeed(response.getWind().getSpeed());
        history.setSearchedAt(LocalDateTime.now());

        repository.save(history);
    }
}