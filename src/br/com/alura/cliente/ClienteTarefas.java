package br.com.alura.cliente;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTarefas {

  private static final int PORTA = 12345;

  public static void main(final String[] args) throws Exception {
    final Socket socket = new Socket("localhost", PORTA);
    System.out.format("Conexão estabelecida com o servidor na porta %d %n", socket.getPort());

    final Scanner scanner = new Scanner(System.in);

    final String valor = scanner.nextLine();

    final PrintStream printStream = new PrintStream(socket.getOutputStream());

    printStream.append("ps aux | grep -i screen").flush();

    printStream.close();

    System.out.format("Encerrando atividade o cliente: %s %n", Thread.currentThread().getName());

    Thread.getAllStackTraces().keySet().forEach(t -> System.out.println(t.getName()));

    scanner.close();
    socket.close();
  }
}
