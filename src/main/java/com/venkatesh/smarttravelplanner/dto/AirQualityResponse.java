package com.venkatesh.smarttravelplanner.dto;

import java.util.List;

public class AirQualityResponse {

    private List<AirQualityData> list;

    public List<AirQualityData> getList() {
        return list;
    }

    public void setList(List<AirQualityData> list) {
        this.list = list;
    }
}