package com.gear2go.controller.external;

import com.gear2go.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/weather")
public class OpenWeatherController {

    private final WeatherService weatherService;

    @GetMapping("/{location}")
    public void getWeatherForLocation(@PathVariable String location) {
        ResponseEntity.ok(weatherService.getWeather(location));
    }
}
