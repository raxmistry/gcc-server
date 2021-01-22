package dev.rakeshmistry.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

class Worker implements Runnable {

    private final Socket socket;
    private final Consumer<Integer> decrement;

    public Worker(Socket socket, Consumer<Integer> decrement) {
        this.socket = socket;
        this.decrement = decrement;
    }

    @Override
    public void run() {
        System.out.println("Connection accepted");
        try {
            var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String input = reader.readLine();
            System.out.println("Received : " + input);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("got it");
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            decrement.accept(1);
        }
    }
}
