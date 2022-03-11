package com.fifa.worldcup.openweather;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class WeatherResponse {

    private List<Weather> weather;

}
