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

    public MyServer() throws IOException, InterruptedException {
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
        Thread.sleep(1000);
    }

    public static void main(String[] args) {
        //This should start the server from the cli
    }

    public void shutdown() throws IOException {
        stopped = true;
    }

    public boolean isUp() {
        return !stopped;
    }
}

class Worker implements Runnable {

    public Worker(Socket accept) throws IOException {
        System.out.println("Connection accepted");
        var reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
        String input;
        System.out.println("Server reading client data");
        while ((input = reader.readLine()) != null) {
            System.out.println("Received : " + input);
            PrintWriter printWriter = new PrintWriter(accept.getOutputStream(), true);
            printWriter.println("got it");
            System.out.println("Data written back to client: ");
        }
        reader.close();
        accept.close();
        System.out.println("Work ending");
    }

    @Override
    public void run() {

    }
}
