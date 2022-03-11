package com.fifa.worldcup.openweather;

import lombok.*;

@AllArgsConstructor
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class Weather {

    private String main;
    private String description;
}
