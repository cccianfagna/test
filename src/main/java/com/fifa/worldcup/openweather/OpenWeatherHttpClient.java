package com.fifa.worldcup.openweather;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class OpenWeatherHttpClient {

    private final RestTemplate restTemplate;
    private final String url;
    private final String apiKey;
    private final String cityQueryString;

    public OpenWeatherHttpClient(@Value("${openweather.url}") String url,
                                 @Value("${openweather.apikey}") String apiKey,
                                 @Value("${openweather.cityQueryString}") String cityQueryString,
                                 RestTemplate restTemplate) {
        this.url = url;
        this.apiKey = apiKey;
        this.cityQueryString = cityQueryString;
        this.restTemplate = restTemplate;
    }

    public Optional<WeatherResponse> currentWeather() {
        var currentWeatherUrl = String.format("%s/data/2.5/weather?q=%s&appid=%s",
                url, cityQueryString, apiKey);
        try {
            return Optional.ofNullable(restTemplate.getForObject(currentWeatherUrl, WeatherResponse.class));
        } catch (RestClientException restClientException) {
            return Optional.empty();
        }
    }
}
