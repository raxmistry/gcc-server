package dev.rakeshmistry.server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.FutureTask;

import static org.assertj.core.api.Assertions.assertThat;

public class ServerTest {
    MyServer server;

    @BeforeEach
    void beforeEach() throws IOException {
    }

    @AfterEach
    void afterEach() throws Exception {
//        server.shutdown();
    }

    @Test
    void should_start_server() throws IOException, InterruptedException {
        server = new MyServer();
        try (Socket socket = new Socket("localhost", 3000)) {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter.write("Test");
            printWriter.flush();
            String serverResponse;
//            while ((serverResponse = reader.readLine()) != null) {
//                assertThat(serverResponse).isEqualTo("got it");
//            }
            printWriter.close();
            assertThat(server.isUp());
            reader.close();
        }
    }

    @Test
    void should_indicate_server_is_up() throws Exception {
        assertThat(server.isUp()).isEqualTo(true);
    }
}
