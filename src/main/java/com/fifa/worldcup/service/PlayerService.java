package com.fifa.worldcup.service;

import com.fifa.worldcup.domain.Player;
import com.fifa.worldcup.openweather.OpenWeatherHttpClient;
import com.fifa.worldcup.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PlayerService {

    private PlayerRepository playerRepository;
    private OpenWeatherHttpClient openWeatherHttpClient;

    public Optional<Player> findById(Long id) {
        return playerRepository.findById(id);
    }

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public void save(Player player) {
        // Weird business logics
        validatePlayerRules(player);

        playerRepository.save(player);
    }

    public void delete(Long id) {
        playerRepository.deleteById(id);
    }

    private void validatePlayerRules(Player player) {
        if (player.getName().length() + player.getLastName().length() < 10) {
            String nameAndLastName = player.getName() + " " + player.getLastName();
            throw new RuntimeException("No valid player (" + nameAndLastName + "). Length of (name + lastName) < 10");
        }
    }
}
