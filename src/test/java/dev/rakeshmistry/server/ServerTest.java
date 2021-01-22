package dev.rakeshmistry.server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static org.assertj.core.api.Assertions.assertThat;

public class ServerTest {
    MyServer server;

    @BeforeEach
    void beforeEach() {
        server = new MyServer();
    }

    @AfterEach
    void afterEach() throws Exception {
        server.shutdown();
    }

    @Test
    void should_start_server() {
        try (Socket socket = new Socket("localhost", 3000)) {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("writing to server");
            printWriter.println("Test");
            readServerResponse(reader);
            printWriter.close();
            assertThat(server.isUp());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void should_indicate_server_is_up() {
        assertThat(server.isUp()).isTrue();
    }

    private void readServerResponse(BufferedReader reader) throws IOException {
        System.out.println("about to read from server");
        String s = reader.readLine();
        System.out.println(s);
    }
}
