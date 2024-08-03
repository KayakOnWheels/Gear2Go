package com.gear2go;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication
public class Gear2GoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Gear2GoApplication.class, args);
    }

}
