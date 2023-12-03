package edu.hw8.task1.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

@SuppressWarnings("RegexpSinglelineJava")
public class Client implements Runnable {
    private final int port = 3345;

    @Override
    public void run() {

        try (Socket client = new Socket("localhost", port);
             DataOutputStream out = new DataOutputStream(client.getOutputStream());
             DataInputStream in = new DataInputStream(client.getInputStream())) {
            System.out.println("Ждем начала работы");
            in.readBoolean();
            System.out.println("Начинаем работу");
            while (true) {
                System.out.println("Введите слово:");
                Scanner scanner = new Scanner(System.in);
                String word = scanner.nextLine();
                out.writeUTF(word);
                out.flush();
                if (word.equals("STOP")) {
                    System.out.println("Работа завершена");
                    break;
                }
                System.out.println(in.readUTF());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
