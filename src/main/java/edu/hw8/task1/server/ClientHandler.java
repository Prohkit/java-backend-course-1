package edu.hw8.task1.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class ClientHandler implements Runnable {
    private final Socket client;
    private final Semaphore semaphore;
    QuotesService quotesService;

    public ClientHandler(Socket client, Semaphore semaphore) {
        this.client = client;
        this.semaphore = semaphore;
        quotesService = new QuotesService();
    }

    @Override
    public void run() {
        try (DataOutputStream out = new DataOutputStream(client.getOutputStream());
             DataInputStream in = new DataInputStream(client.getInputStream())) {
            out.writeBoolean(true);
            while (true) {
                String word = in.readUTF();
                if (word.equals("STOP")) {
                    break;
                }
                String quote = quotesService.getQuote(word);
                out.writeUTF(quote);
                out.flush();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        semaphore.release();
    }
}
