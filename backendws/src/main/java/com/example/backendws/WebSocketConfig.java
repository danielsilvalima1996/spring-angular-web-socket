package com.example.backendws;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // começo do endpoint/tópico de retorno da mensagem ao cliente
        config.enableSimpleBroker("/topico");
        // começo do endpoint/tópico que recebe a mensagem ao cliente
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
    	// endpoint que faz a conexão que está no WebSocketService
    	registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:4200", "**")  // Origem permitida
                .withSockJS(); // conexão com o SockJS

        registry.addEndpoint("/ws")
            .setAllowedOrigins("http://localhost:4200", "**");
    }

    /*
     * opcional, para caso você queira guardar as sessões com chave e valor, 
     * sendo a chave o nome do usuário que está no header da conexão e o valor será a sessão.
     */
    @Bean("sessoes")
    public Map<String, String> mapaDeSessoes() {
        return new HashMap<>();
    }
}
