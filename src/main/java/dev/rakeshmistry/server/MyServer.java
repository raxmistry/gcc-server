package dev.rakeshmistry.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public class MyServer {

    private boolean stopped = false;
    private ServerSocket server;
    private final int port = 3000;
    private int connectionsAllowed = 2;

    public MyServer() {
        new Thread(() -> {
            try {
                server = new ServerSocket(port);
                System.out.println("Server started on port:" + port);
                stopped = false;

                AtomicInteger currentConnections = new AtomicInteger();
                while (!stopped) {
                    if (currentConnections.get() <= connectionsAllowed) {
                        System.out.println("New Connection created, current number of connections: " + currentConnections);
                        try {
                            Socket socket = server.accept();
                            new Thread(new Worker(socket, (val) -> currentConnections.getAndDecrement())).start();
                        } catch (SocketTimeoutException e) {
                            System.out.println("Socket timeout.");
                            currentConnections.getAndDecrement();
                        }
                        currentConnections.getAndIncrement();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    public static void main(String[] args) {
        new MyServer();
    }

    public void shutdown() {
        stopped = true;
    }

    public boolean isUp() {
        return !stopped;
    }
}

