package com.venkatesh.smarttravelplanner.dto;

public class MainData {

    private Double temp;
    private Integer humidity;

    public MainData() {
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }
}