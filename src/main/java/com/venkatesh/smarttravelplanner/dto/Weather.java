package com.venkatesh.smarttravelplanner.dto;

public class Weather {

    private String description;

    private String icon;

    public Weather() {
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