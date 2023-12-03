package edu.hw8.task1.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class Server {
    private final ExecutorService executorService;
    private final Semaphore semaphore;
    private final ServerSocket server;
    private final ExecutorService serverExecutorService = Executors.newFixedThreadPool(2);
    private final BlockingQueue<Socket> sockets = new LinkedBlockingQueue<>();
    private final int port = 3345;

    public Server(int threadsNumber) {
        try {
            this.server = new ServerSocket(port);
            this.executorService = Executors.newFixedThreadPool(threadsNumber);
            this.semaphore = new Semaphore(threadsNumber, true);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void start() {
        serverExecutorService.submit(this::putSocketsToQueue);
        serverExecutorService.submit(this::executeTasks);
    }

    public void shutdown() {
        serverExecutorService.shutdownNow();
        executorService.shutdownNow();
        try {
            server.close();
        } catch (IOException e) {
            throw new RuntimeException();
        }
        // не придумал, как прервать все потоки
        System.exit(0);
    }

    @SuppressWarnings("RegexpSinglelineJava")
    private void putSocketsToQueue() {
        try {
            while (true) {
                Socket client = server.accept();
                sockets.put(client);
            }
        } catch (SocketException e) {
            System.out.println("Сервер завершил работу");
        } catch (IOException | InterruptedException exception) {
            throw new RuntimeException();
        }
    }

    private void executeTasks() {
        try {
            while (true) {
                Socket client = sockets.take();
                semaphore.acquire();
                executorService.submit(new ClientHandler(client, semaphore));
            }
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }
}
