package com.zerommo.mmo_server.player;

import com.zerommo.mmo_server.user.User;
import com.zerommo.mmo_server.user.UserRepository;
import com.zerommo.mmo_server.service.RedisService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private static final long PLAYER_CACHE_TTL = 300; // 5 min
    private static final long USER_PLAYERS_CACHE_TTL = 120; // 2 min

    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;
    private final RedisService redisService;

    public PlayerController(PlayerRepository playerRepository,
                            UserRepository userRepository,
                            RedisService redisService) {
        this.playerRepository = playerRepository;
        this.userRepository = userRepository;
        this.redisService = redisService;
    }

    @PostMapping
    public ResponseEntity<?> createPlayer(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow();

        if (playerRepository.findByNameIgnoreCase(name).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Name already taken"));
        }

        if ("PLAYER".equalsIgnoreCase(user.getRole())) {
            long playerCount = playerRepository.countByUserId(user.getId());

            if (playerCount >= 1) {
                return ResponseEntity.badRequest().body(
                        Map.of("error", "PLAYER accounts can only have 1 character"));
            }
        }

        Player player = new Player();
        player.setName(name);
        player.setUserId(user.getId());

        Player saved = playerRepository.save(player);

        redisService.delete("player:name:" + name.toLowerCase());
        redisService.delete("player:user:" + user.getId());

        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getPlayerByName(@PathVariable String name) {

        String cacheKey = "player:name:" + name.toLowerCase();

        Player cached = (Player) redisService.get(cacheKey);
        if (cached != null) {
            return ResponseEntity.ok(cached);
        }

        return playerRepository.findByNameIgnoreCase(name)
                .map(player -> {
                    redisService.set(cacheKey, player, PLAYER_CACHE_TTL);
                    return ResponseEntity.ok(player);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/my-characters")
    public ResponseEntity<?> getMyPlayers() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow();

        String cacheKey = "player:user:" + user.getId();

        Object cached = redisService.get(cacheKey);
        if (cached != null) {
            return ResponseEntity.ok(cached);
        }
        var players = playerRepository.findByUserId(user.getId());

        redisService.set(cacheKey, players, USER_PLAYERS_CACHE_TTL);

        return ResponseEntity.ok(players);
    }
}
