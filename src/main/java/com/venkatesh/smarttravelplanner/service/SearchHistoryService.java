package com.venkatesh.smarttravelplanner.service;
import com.venkatesh.smarttravelplanner.dto.ForecastResponse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.venkatesh.smarttravelplanner.dto.DailyForecast;
import com.venkatesh.smarttravelplanner.dto.ForecastItem;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.venkatesh.smarttravelplanner.dto.WeatherResponse;
import com.venkatesh.smarttravelplanner.entity.SearchHistory;
import com.venkatesh.smarttravelplanner.repository.SearchHistoryRepository;

@Service
public class SearchHistoryService {

    private final SearchHistoryRepository repository;
    private final RestTemplate restTemplate;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    public SearchHistoryService(SearchHistoryRepository repository,
                                RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public SearchHistory saveSearch(SearchHistory searchHistory) {
        return repository.save(searchHistory);
    }

    public List<SearchHistory> getAllSearchHistory() {
        return repository.findAll();
    }

    public List<SearchHistory> getSearchHistory() {
        return repository.findAllByOrderBySearchedAtDesc();
    }
    
    public WeatherResponse getWeather(String city) {

        String url = apiUrl +
                "?q=" + city +
                "&appid=" + apiKey +
                "&units=metric";

        WeatherResponse response =
                restTemplate.getForObject(url, WeatherResponse.class);

        if (response != null) {

            SearchHistory history = new SearchHistory();

            history.setCity(response.getName());

            history.setCountry(response.getSys().getCountry());

            history.setTemperature(response.getMain().getTemp());

            history.setHumidity(response.getMain().getHumidity());

            history.setWeather(response.getWeather().get(0).getDescription());

            history.setWindSpeed(response.getWind().getSpeed());

            history.setSearchedAt(java.time.LocalDateTime.now());

            repository.save(history);
        }

        return response;
    }
    
    public long getTotalSearches() {

        return repository.count();

    }

    public long getTotalCities() {

        return repository.countDistinctCities();

    }

    public Double getAverageTemperature() {

        Double avg = repository.getAverageTemperature();

        return avg == null ? 0.0 : avg;

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

                daily.setDescription(
                        item.getWeather().get(0).getDescription());

                daily.setIcon(
                        item.getWeather().get(0).getIcon());

                dailyForecasts.add(daily);

            }

            if (dailyForecasts.size() == 5) {
                break;
            }

        }

        return dailyForecasts;

    }

}