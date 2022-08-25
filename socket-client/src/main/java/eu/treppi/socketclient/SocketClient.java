package eu.treppi.socketclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class SocketClient {
    private final int port;
    private final String host;
    public SocketClient(String host, int port) {
        this.port = port;
        this.host = host;
    }

    public void send(ServerTask task) {
        try(Socket socket = new Socket(this.host,this.port)) {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(task.getKey());
            task.connection(dataInputStream, dataOutputStream);
            dataInputStream.close();
            dataInputStream.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
