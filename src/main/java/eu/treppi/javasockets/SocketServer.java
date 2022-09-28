package eu.treppi.javasockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketServer {
    private int port;
    private ArrayList<SocketHandler> handlers;

    public SocketServer(int port) {
        this.port = port;
        this.handlers = new ArrayList<>();
    }

    public void startListening() {
        while(true) {
            try(ServerSocket serverSocket = new ServerSocket(port)){
                System.out.println("listening to port: " + port);
                Socket clientSocket = serverSocket.accept();
                System.out.println("[+] " + clientSocket.getLocalAddress().getHostAddress());
                DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

                String arg0 = dataInputStream.readUTF();
                handlers.stream().filter(h -> h.getKey().equalsIgnoreCase(arg0)).forEach(h -> {
                    try {
                        h.handle(dataInputStream, dataOutputStream);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                dataInputStream.close();
                dataOutputStream.close();
                clientSocket.close();
            } catch (Exception e){
                e.printStackTrace();
            }
            try {
                Thread.sleep(10000);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addHandler(SocketHandler handler) {
        handlers.add(handler);
    }
}