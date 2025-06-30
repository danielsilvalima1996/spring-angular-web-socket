package com.example.backendws;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

	@Autowired
	private TesteRespostaParalelo testeRespostaParalelo;

	@MessageMapping("/mensagem")
    public void mensagem(String message, SimpMessageHeaderAccessor headerAccessor) throws InterruptedException {
		String login = ((Map) headerAccessor.getHeader("nativeHeaders")).get("login").toString();
		testeRespostaParalelo.enviarMensagem(message, login);
    }

}