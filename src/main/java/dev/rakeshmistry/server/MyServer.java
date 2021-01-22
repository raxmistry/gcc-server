package dev.rakeshmistry.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {

    private boolean stopped = false;
    private ServerSocket server;
    private final int port = 3000;

    public MyServer() {
        new Thread(() -> {
            try {
                server = new ServerSocket(port);
                System.out.println("Server started on port:" + port);

                while (!stopped) {
                    Socket accept = server.accept();
                    new Thread(new Worker(accept)).start();
                }
                server.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        new MyServer();
    }

    public void shutdown() throws IOException {
        stopped = true;
    }

    public boolean isUp() {
        return !stopped;
    }
}

class Worker implements Runnable {

    private Socket accept;

    public Worker(Socket accept) {
        this.accept = accept;
    }

    @Override
    public void run() {
        System.out.println("Connection accepted");
        try {
            var reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
            String input;
            while ((input = reader.readLine()) != null) {
                System.out.println("Received : " + input);
                PrintWriter printWriter = new PrintWriter(accept.getOutputStream(), true);
                printWriter.println("got it");
            }
            reader.close();
            accept.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
