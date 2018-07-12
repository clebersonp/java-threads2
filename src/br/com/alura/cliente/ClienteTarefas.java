package br.com.alura.cliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTarefas {

  private static final int PORTA = 12345;

  public static void main(final String[] args) throws Exception {
    final Socket socket = new Socket("localhost", PORTA);
    System.out.format("Conexão estabelecida com o servidor na porta %d %n", socket.getPort());

    final Thread threadEnviaServidor = new Thread(() -> {
      try {
        System.out.println("Pode enviar commandos.");
        final PrintStream printStream = new PrintStream(socket.getOutputStream());
        final Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
          final String linha = scanner.nextLine();

          if (linha.trim().equals("")) {
            break;
          }
          printStream.println(linha);
        }

        printStream.close();
        scanner.close();
      } catch (final IOException e) {
        throw new RuntimeException();
      }
    });


    final Thread threadRecebeServidor = new Thread(() -> {
      try {
        System.out.println("Recebendo dados do servidor.");
        final Scanner recebeServidor = new Scanner(socket.getInputStream());
        while (recebeServidor.hasNextLine()) {
          final String linha = recebeServidor.nextLine();
          System.out.println(linha);
        }

        recebeServidor.close();
      } catch (final IOException e) {
        throw new RuntimeException();
      }
    });

    threadEnviaServidor.start();
    threadRecebeServidor.start();

    // a thread main aguarda até essa thread finalizar.
    threadEnviaServidor.join();
    System.out.println("Fechando o socket do cliente");
    socket.close();
  }
}
