package com.venkatesh.smarttravelplanner.dto;

public class DailyForecast {

    private String date;
    private Double temperature;
    private String description;
    private String icon;

    public DailyForecast() {
    }

    public DailyForecast(String date, Double temperature,
                         String description, String icon) {
        this.date = date;
        this.temperature = temperature;
        this.description = description;
        this.icon = icon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}