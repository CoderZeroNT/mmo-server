package com.zerommo.mmo_server.config;

import com.zerommo.mmo_server.websocket.GameWebSocketHandler;
import com.zerommo.mmo_server.websocket.JwtHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final JwtHandshakeInterceptor jwtInterceptor;
    private final GameWebSocketHandler gameHandler;

    public WebSocketConfig(
            JwtHandshakeInterceptor jwtInterceptor,
            GameWebSocketHandler gameHandler
    ) {
        this.jwtInterceptor = jwtInterceptor;
        this.gameHandler = gameHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(gameHandler, "/ws/game")
                .addInterceptors(jwtInterceptor)
                .setAllowedOrigins("*");
    }
}
