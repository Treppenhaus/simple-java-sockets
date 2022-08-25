package eu.treppi.socketclient;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        SocketClient client = new SocketClient("localhost", 6666);
        client.send(new ServerTask() {
            @Override
            public String getKey() {
                return "add";
            }

            @Override
            public void connection(DataInputStream inputStream, DataOutputStream outputStream) throws IOException {
                String response = new BufferedReader(new InputStreamReader(inputStream)).readLine();
                System.out.println("response: " + response);
            }
        });
    }
}
