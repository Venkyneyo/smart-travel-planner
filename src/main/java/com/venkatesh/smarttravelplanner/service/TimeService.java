package com.venkatesh.smarttravelplanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.venkatesh.smarttravelplanner.dto.TimeResponse;

@Service
public class TimeService {

    @Autowired
    private RestTemplate restTemplate;

    public TimeResponse getCurrentTime() {

        String url =
                "https://worldtimeapi.org/api/timezone/Asia/Kolkata";

        return restTemplate.getForObject(url,
                TimeResponse.class);

    }

}