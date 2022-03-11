package com.fifa.worldcup.web;

import com.fifa.worldcup.domain.Player;
import com.fifa.worldcup.service.PlayerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlayerRestController {

    private PlayerService playerService;

    public PlayerRestController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/players")
    public List<Player> get() {
        return playerService.findAll();
    }

    @GetMapping("/players/{id}")
    public Player get(@PathVariable Long id) {
        return playerService.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    @PostMapping("/players")
    public void post(@RequestBody Player player) {
        playerService.save(player);
    }

    @DeleteMapping("/players/{id}")
    public void delete(@PathVariable Long id) {
        playerService.delete(id);
    }
}
