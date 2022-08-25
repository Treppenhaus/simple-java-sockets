package eu.treppi.socketserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface SocketHandler {
    public String getKey();
    public void handle(DataInputStream inputStream, DataOutputStream outputStream) throws IOException;
}
