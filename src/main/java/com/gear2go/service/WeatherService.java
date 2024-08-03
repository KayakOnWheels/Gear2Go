package com.gear2go.service;

import com.gear2go.domain.dto.response.WeatherResponse;
import com.gear2go.properties.OpenWeatherProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final UriService uriService;
    private final RestTemplate restTemplate;
    private final OpenWeatherProperties openWeatherProperties;

    public WeatherResponse getWeather(String location) {

        Map<String, List<String>> params = new HashMap<>();
        params.put("q", List.of(location));
        params.put("appid", List.of(openWeatherProperties.getApiKey()));

        return restTemplate.getForObject(uriService.buildUri(openWeatherProperties.getEndpoint(), CollectionUtils.toMultiValueMap(params)), WeatherResponse.class);
    }

}
