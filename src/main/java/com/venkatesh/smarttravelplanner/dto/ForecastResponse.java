package com.venkatesh.smarttravelplanner.dto;

import java.util.List;

public class ForecastResponse {

    private List<ForecastItem> list;

    public List<ForecastItem> getList() {
        return list;
    }

    public void setList(List<ForecastItem> list) {
        this.list = list;
    }
}