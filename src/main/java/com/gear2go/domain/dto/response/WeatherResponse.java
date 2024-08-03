package com.gear2go.domain.dto.response;

public record WeatherResponse(String conditions, String temp, String humidity, String windSpeed, String clouds) {
}
