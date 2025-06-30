import { Component, OnInit } from '@angular/core';
import { WebSocketService } from './web-socket.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  mensagensRecebidas: string[] = [];

  /** injetando o WebSocketService no component */
  constructor(private webSocketService: WebSocketService) {}

  ngOnInit(): void {
    // chama o método para conectar ao websocket
    this.webSocketService.connect();
    // fica escutando o websocket para pegar os retornos
    this.webSocketService.getMensagens().subscribe((message: string) => {
      // joga para o array as mensagens recebidas
      this.mensagensRecebidas.push(message);
    });

  }

  public enviarMensagem(mensagem: string): void {
    // envia a mensagem para o backend, no exemplo da CCEE
    // acredito que não há irá mensagem, mas sim os dados
    // das organizações
    this.webSocketService.enviarMensagem(mensagem);
  }

}
