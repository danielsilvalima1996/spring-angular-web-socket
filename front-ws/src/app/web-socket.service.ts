import { Injectable, OnDestroy } from '@angular/core';
import { CompatClient, Stomp } from '@stomp/stompjs';
import { Observable, Subject } from 'rxjs';
import * as SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService implements OnDestroy {

  private stompClient: CompatClient;
  private mensagemSubject = new Subject<string>();
  private sessionId: string = '';
  private isConectado: boolean = false;

  constructor() {
    // sessionId
    this.sessionId = this.gerarSessionId();

    // endpoint de conexão na classe WebSocketConfig
    const socket = new SockJS('http://localhost:8080/ws', [], {
      sessionId: () => {
        return this.sessionId;
      }
    });

    this.stompClient = Stomp.over(socket);
  }

  ngOnDestroy(): void {
    // chamada do método para desconectar do backend
    this.disconnect();
  }

  /**
   * método de conexão
   */
  public connect(): void {

    if (!this.isConectado) {
      const header = this.obterHeaderConexao();

      this.stompClient.connect(header, (frame) => {

        this.isConectado = true;

        console.log(`sessionId: ${this.sessionId}`);

        this.stompClient
          // endpoint de retorno
          .subscribe(`/topico/retorno/${this.sessionId}`, (message) => {
            this.mensagemSubject.next(message.body);
          });
      });

      this.stompClient.onWebSocketClose = this.close;

    }

  }

  private close() {
    console.log('evento');
  }

  /**
   * Função para gerar um UUID como sessionId
   * @returns hash de string
   */
  private gerarSessionId() {
    return crypto.randomUUID();
  }

  /**
   * @returns retorna o header da conexão com o usuário
   */
  private obterHeaderConexao(): Object {
    const headers = {
      login: this.obterUsuario()
    };
    return headers;
  }

  /**
   * retorna o usuário, porém está mockado
   * @returns 
   */

  private obterUsuario(): string {
    return 'Daniel';
  }

  // método para desconectar do backend
  public disconnect(): void {
    if (this.isConectado) {
      this.stompClient.disconnect();
    }
  }

  // endpoint para enviar a mensagem
  public enviarMensagem(mensagem: string): void {
    if (this.isConectado) {
      const header = this.obterHeaderConexao();
      // endpoint envio
      this.stompClient.send('/app/mensagem', header, mensagem);
    }
  }

  // método para receber as mensagens
  public getMensagens(): Observable<string> {
    return this.mensagemSubject.asObservable();
  }

}
