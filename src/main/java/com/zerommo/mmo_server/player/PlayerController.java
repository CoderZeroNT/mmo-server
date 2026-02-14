package com.zerommo.mmo_server.player;

import com.zerommo.mmo_server.player.Player;
import com.zerommo.mmo_server.user.User;
import com.zerommo.mmo_server.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;

    public PlayerController(PlayerRepository playerRepository, UserRepository userRepository) {
        this.playerRepository = playerRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<?> createPlayer(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        
        User user = userRepository.findByEmail(email).orElseThrow();

        if (playerRepository.findByNameIgnoreCase(name).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Name already taken"));
        }

        Player player = new Player();
        player.setName(name);
        player.setUserId(user.getId());
        
        return ResponseEntity.ok(playerRepository.save(player));
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getPlayerByName(@PathVariable String name) {
        return playerRepository.findByNameIgnoreCase(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/my-characters")
    public ResponseEntity<?> getMyPlayers() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow();
        return ResponseEntity.ok(playerRepository.findByUserId(user.getId()));
    }
}