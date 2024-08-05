package com.gear2go.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties("open-weather.api")
public class OpenWeatherProperties {

    private String endpoint;
    private String apiKey;
}
