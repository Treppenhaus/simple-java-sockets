package eu.treppi.socketserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        SocketServer server = new SocketServer(6666);
        server.addHandler(new SocketHandler() {
            @Override
            public String getKey() {
                return "add";
            }

            @Override
            public void handle(DataInputStream inputStream, DataOutputStream outputStream) throws IOException {
                outputStream.write("{\"status\":\"ok\",\"id\":\"AAAAAAAAAAAAA-example\"}\n".getBytes());
            }
        });
        server.startListening();
    }
}
