package com.example.backendws;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Component
public class WebSocketEventListener {

    @Qualifier("sessoes")
    @Autowired
    private Map<String, String> sessoes;

    // método que escuta uma nova conexão
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        log.info("Nova conexao WebSocket recebida: " + event.getMessage());
        // pega a sessão
        final String session = event.getMessage().getHeaders().get("simpSessionId").toString();
        // pega o usuário
        final String usuario = ((java.util.Map) event.getMessage().getHeaders().get("nativeHeaders")).get("login")
                .toString();
        // adiciona a propriedade sessoes.
        sessoes.put(usuario, session);
    }

    // método que escuta quando a conexão é fechada
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        log.info("Conexao WebSocket encerrada: " + event.getSessionId());
    }

}
