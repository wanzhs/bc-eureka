package com.ga.com.eureka.oauth2center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class Oauth2CenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2CenterApplication.class, args);
    }

}
