package com.fifa.worldcup.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OpenWeatherRestTemplateConfig {

    @Bean
    public RestTemplate openWeatherRestTemplate() {
        return new RestTemplateBuilder().build();
    }
}
