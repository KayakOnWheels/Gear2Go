package com.gear2go.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties("gear2go")
public class Gear2GoProperties {

    private String root;
    private String user;
    private String recovery;
    private String secretKey;
}
