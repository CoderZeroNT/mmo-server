package com.zerommo.mmo_server.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Component
public class GameWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {

        String username =
                (String) session.getAttributes().get("username");

        System.out.println("Player connected: " + username);

        session.sendMessage(new TextMessage(
                "CONNECTED_AS:" + username
        ));
    }

    @Override
    protected void handleTextMessage(
            WebSocketSession session,
            TextMessage message
    ) throws Exception {

        String username =
                (String) session.getAttributes().get("username");

        System.out.println("MSG from " + username + ": " + message.getPayload());

        session.sendMessage(new TextMessage(
                "SERVER_ECHO:" + message.getPayload()
        ));
    }
}
