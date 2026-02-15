package com.zerommo.mmo_server.auth;

import com.zerommo.mmo_server.service.RedisService;
import com.zerommo.mmo_server.user.User;
import com.zerommo.mmo_server.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final long LOGIN_RATE_LIMIT_TTL = 60; // 1 min
    private static final int MAX_LOGIN_ATTEMPTS = 5;
    private static final long USER_CACHE_TTL = 300; // 5 min

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RedisService redisService;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          RedisService redisService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.redisService = redisService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {

        String emailKey = request.getEmail().toLowerCase();

        if (userRepository.findByEmail(emailKey).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Email already registered"));
        }

        String password = request.getPassword();
        if (!password.matches("^(?=.*\\d).{8,}$")) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error",
                            "Password must be at least 8 characters and contain a number"));
        }

        User user = new User();
        user.setEmail(emailKey);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("PLAYER");

        userRepository.save(user);

        redisService.delete("user:email:" + emailKey);

        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {

        String emailKey = request.getEmail().toLowerCase();
        String rateKey = "login:attempts:" + emailKey;

        Long attempts = redisService.increment(rateKey, LOGIN_RATE_LIMIT_TTL, TimeUnit.SECONDS);
        if (attempts != null && attempts > MAX_LOGIN_ATTEMPTS) {
            return ResponseEntity.status(429)
                    .body(Map.of("error", "Too many login attempts. Try again later."));
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        redisService.delete(rateKey);

        String userCacheKey = "user:email:" + emailKey;
        User cachedUser = (User) redisService.get(userCacheKey);

        User user;
        if (cachedUser != null) {
            user = cachedUser;
        } else {
            user = userRepository.findByEmail(emailKey).orElseThrow();
            redisService.set(userCacheKey, user, USER_CACHE_TTL);
        }

        String token = jwtService.generateToken(user.getEmail());

        return ResponseEntity.ok(Map.of("token", token));
    }
}
