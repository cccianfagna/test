package com.fifa.worldcup.unit.openweather;

import com.fifa.worldcup.openweather.OpenWeatherHttpClient;
import com.fifa.worldcup.openweather.Weather;
import com.fifa.worldcup.openweather.WeatherResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class OpenWeatherHttpClientTest {

    private final static String URL = "http://localhost:8080";
    private final static String API_KEY = "apiKey";
    private final static String CITY_QUERY = "cityQuery";
    private final static String DATA_URL = String.format("%s/data/2.5/weather?q=%s&appid=%s", URL, CITY_QUERY, API_KEY);

    @Mock
    private RestTemplate restTemplate;
    private OpenWeatherHttpClient openWeatherHttpClient;

    @BeforeEach
    public void before() {
        openWeatherHttpClient = new OpenWeatherHttpClient(URL, API_KEY, CITY_QUERY, restTemplate);
    }

    @Test
    public void shouldAnswerWithWeatherData() {
        // given
        WeatherResponse weatherResponse = new WeatherResponse(List.of(new Weather("Clouds", "few clouds")));
        when(restTemplate.getForObject(DATA_URL, WeatherResponse.class)).thenReturn(weatherResponse);

        // when
        Optional<WeatherResponse> currentWeather = openWeatherHttpClient.currentWeather();

        // then
        assertThat(currentWeather).isEqualTo(Optional.of(weatherResponse));
    }

    @Test
    public void shouldBeEmptyTheAnswer() {
        // given
        String url = String.format("%s/data/2.5/weather?q=%s&appid=%s", URL, CITY_QUERY, API_KEY);
        when(restTemplate.getForObject(DATA_URL, WeatherResponse.class)).thenThrow(new RestClientException("Empty"));

        // when
        Optional<WeatherResponse> empty = openWeatherHttpClient.currentWeather();

        // then
        assertThat(empty.isEmpty()).isTrue();
    }
}
