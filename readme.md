### simple java server- and clientsocket

examples below

---
#### client socket
```java
// client connects to localhost:6666
// query ServerTask to only send key "add" to server
// you can create more advanced ServerTasks using the connection() method and responding to input/output stream
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
```
---
#### server socket
```java
// server socket listening on port 6666
// create new handler for key "add" and just respond with stringified json
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
```