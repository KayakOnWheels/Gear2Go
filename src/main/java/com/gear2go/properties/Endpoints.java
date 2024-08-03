package com.gear2go.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties("gear2go.endpoint")
public class Endpoints {

    private String root;
    private String user;
    private String recovery;
}
