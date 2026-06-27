package com.venkatesh.smarttravelplanner.dto;
import com.venkatesh.smarttravelplanner.dto.Coord;

import java.util.List;

public class WeatherResponse {

    private String name;
    private MainData main;
    private List<Weather> weather;
    private Wind wind;
    private Sys sys;
    private Coord coord;
    
    public WeatherResponse() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MainData getMain() {
        return main;
    }

    public void setMain(MainData main) {
        this.main = main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
    
    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }
    
    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }
}