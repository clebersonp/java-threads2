package br.com.alura.servidor;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorTarefas {

  private static final int PORTA = 12345;
  private static int NUMBER_THREADS = 1;

  public static void main(final String[] args) {

    try (final ServerSocket server = new ServerSocket(PORTA)) {
      System.out.println("--- Iniciando Servidor ---");

      // final ExecutorService poolService = Executors.newFixedThreadPool(2); // limita em 2 threads
      // no
      // maximo

      final ExecutorService poolService = Executors.newCachedThreadPool(); // nao tem limite, mas é
                                                                           // auto gerenciado e
                                                                           // reaproveitavel
      // Ficar ouvindo varios clientes
      while (true) {
        final Socket socket = server.accept();
        System.out.format("Aceitando novo cliente na porta %d %n", socket.getPort());

        final String nome = "Thread_" + NUMBER_THREADS++;
        // para cada nova aceitação de clientes, cria uma nova thread para cada cliente
        final DistribuirTarefas distribuidor = new DistribuirTarefas(socket, nome);
        // ao inves de criar novas threads na mao, usando a api concurrent
        // final Thread threadCliente =
        // new Thread(distribuidor, String.format("Cliente_%d", NUMBER_THREADS++));
        // threadCliente.start();

        poolService.execute(distribuidor);
        Thread.getAllStackTraces().keySet().forEach(t -> System.out.println(t.getName()));
      }
    } catch (final Exception ex) {
      ex.printStackTrace();
    }
  }
}
