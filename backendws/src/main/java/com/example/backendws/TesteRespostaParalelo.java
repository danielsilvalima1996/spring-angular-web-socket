package com.example.backendws;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TesteRespostaParalelo {

	// responsável por enviar as mensagens ao cliente
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	// guardar os usuário e sessões
	@Qualifier("sessoes")
	@Autowired
	private Map<String, String> sessoes;

	@Async
	public void enviarMensagem(String message, String login) throws InterruptedException {
		// adiciona uma pausa de 2 segundos ao método
		Thread.sleep(20000L);
		// pega o id da sessão através do usuário
		String sessionId = sessoes.get(login);
		// topico de retorno
		String topico = String.format("/topico/retorno/%s", sessionId);
		// mensagem de retorno
		String mensagem = String.format("A sessão %s enviou a seguinte: %s!", sessionId, message);
		// envio da mensagem ao cliente
		messagingTemplate.convertAndSend(topico, mensagem);
	}

}