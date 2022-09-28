package eu.treppi.javasockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface ServerTask {
    public String getKey();
    public void connection(DataInputStream inputStream, DataOutputStream outputStream) throws IOException;
}
