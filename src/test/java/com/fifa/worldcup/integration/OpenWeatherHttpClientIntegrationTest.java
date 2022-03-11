package com.fifa.worldcup.integration;

import com.fifa.worldcup.helper.FileLoader;
import com.fifa.worldcup.openweather.OpenWeatherHttpClient;
import com.fifa.worldcup.openweather.Weather;
import com.fifa.worldcup.openweather.WeatherResponse;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WireMockTest
public class OpenWeatherHttpClientIntegrationTest {

    private static String URL = "http://localhost:";
    private static String API_KEY = "apiKey";
    private static String CITY_QUERY = "cityQuery";

    private OpenWeatherHttpClient openWeatherHttpClient;

    @BeforeEach
    public void before(WireMockRuntimeInfo wmRuntimeInfo) {
        openWeatherHttpClient = new OpenWeatherHttpClient(URL + wmRuntimeInfo.getHttpPort(),
                API_KEY, CITY_QUERY,new RestTemplateBuilder().build());
    }

    @Test
    public void shouldGetCurrentDataWeather(WireMockRuntimeInfo wmRuntimeInfo) throws IOException {
        // given
        WeatherResponse expectedResponse = new WeatherResponse(List.of(new Weather("Clear", "clear sky")));
        String dataWeatherUrl = String.format("/data/2.5/weather?q=%s&appid=%s",
                CITY_QUERY, API_KEY);
        stubFor(get(dataWeatherUrl).willReturn(
                aResponse().
                        withBody(FileLoader.read("classpath:weatherApiResponse.json")).
                        withHeader("Content-Type", "application/json").
                        withStatus(HttpStatus.OK.ordinal())
        ));

        // when
        Optional<WeatherResponse> actualResponse = openWeatherHttpClient.currentWeather();

        // then
        assertThat(actualResponse).isEqualTo(Optional.of(expectedResponse));
    }

    @Test
    public void shouldGetAndEmptyAnswer() {
        // given
        String dataWeatherUrl = String.format("/data/2.5/weather?q=%s&appid=%s",
                CITY_QUERY, API_KEY);
        stubFor(get(dataWeatherUrl).willReturn(
                aResponse().withStatus(HttpStatus.NOT_FOUND.value())
                )
        );

        // when
       Optional<WeatherResponse> empty = openWeatherHttpClient.currentWeather();

       // then
        assertThat(empty).isEmpty();
    }
}
