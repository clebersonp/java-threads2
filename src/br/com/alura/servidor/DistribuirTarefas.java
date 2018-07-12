package br.com.alura.servidor;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class DistribuirTarefas implements Runnable {

  private final Socket socket;
  private final String nome;

  public DistribuirTarefas(final Socket socket, final String nome) {
    this.socket = socket;
    this.nome = nome;
  }

  @Override
  public void run() {

    try {
      System.out.format("Distribuindo tarefas para o socket: %s %n", this.socket);

      System.out.format("Recebendo informação do cliente: %s %n", this.nome);

      final Scanner entradaCliente = new Scanner(this.socket.getInputStream());

      final PrintStream saidaCliente = new PrintStream(this.socket.getOutputStream());

      while (entradaCliente.hasNextLine()) {
        final String comando = entradaCliente.nextLine();
        System.out.println("Comando recebido:  " + comando);

        switch (comando) {
          case "c1": {
            saidaCliente.println("Confirmação comando c1");
            break;
          }
          case "c2": {
            saidaCliente.println("Confirmação comando c2");
            break;
          }
          default: {
            saidaCliente.println("Comando não encontrado!");
          }
        }
      }

      entradaCliente.close();
      saidaCliente.close();
      System.out.format("Finalizando a tarefa do cliente: %s %n", this.nome);
    } catch (final Exception e) {
      e.printStackTrace();
    }

  }
}
