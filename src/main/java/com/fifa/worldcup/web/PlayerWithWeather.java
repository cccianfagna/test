package com.fifa.worldcup.web;

import com.fifa.worldcup.domain.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PlayerWithWeather {

    private Player player;
    private String weatherDescription;
}
