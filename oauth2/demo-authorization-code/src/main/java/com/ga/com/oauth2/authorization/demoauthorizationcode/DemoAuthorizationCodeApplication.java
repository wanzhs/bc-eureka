package com.ga.com.oauth2.authorization.demoauthorizationcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DemoAuthorizationCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoAuthorizationCodeApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
