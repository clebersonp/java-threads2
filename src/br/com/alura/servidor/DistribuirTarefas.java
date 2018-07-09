package br.com.alura.servidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class DistribuirTarefas implements Runnable {

  private final Socket socket;
  private final String nome;

  public DistribuirTarefas(final Socket socket, final String nome) {
    this.socket = socket;
    this.nome = nome;
  }

  @Override
  public void run() {

    try (final BufferedReader reader =
        new BufferedReader(new InputStreamReader(this.socket.getInputStream()))) {
      System.out.format("Distribuindo tarefas para o socket: %s %n", this.socket);

      System.out.format("Recebendo informação do cliente: %s %n", this.nome);

      reader.lines().forEach(System.out::println);

      Thread.sleep(20_000L);
      System.out.format("Finalizando a tarefa do cliente: %s %n", this.nome);
    } catch (final Exception e) {
      e.printStackTrace();
    }

  }
}
