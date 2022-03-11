package com.fifa.worldcup.unit.openweather;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fifa.worldcup.helper.FileLoader;
import com.fifa.worldcup.openweather.Weather;
import com.fifa.worldcup.openweather.WeatherResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class WeatherResponseTest {

    @Test
    public void shouldDeserializeJson() throws IOException {
        // given
        String json = FileLoader.read("classpath:weatherApiResponse.json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        WeatherResponse expectedWeatherResponse = new WeatherResponse(List.of(new Weather("Clear", "clear sky")));

        // when
        WeatherResponse currentWeatherResponse = objectMapper.readValue(json, WeatherResponse.class);

        // then
        assertThat(currentWeatherResponse).isEqualTo(expectedWeatherResponse);
    }
}
