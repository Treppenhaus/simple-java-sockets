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
---

### another example

this is a counting example:
the client sends "1" (string) to the server, which adds 1 and sends it back.
so the server responds with 2, client with 3, server with 4 etc etc.
below is the example code

#### server code
```java
public static void main(String[] args) {
        SocketServer server = new SocketServer(6666);
        server.addHandler(new SocketHandler() {
            @Override
            public String getKey() {
                return "count";
            }

            @Override
            public void handle(DataInputStream inputStream, DataOutputStream outputStream) throws IOException {
                // always wait for number, then send 1 higher back to client
                while(true) {
                    String response = new BufferedReader(new InputStreamReader(inputStream)).readLine();
                    System.out.println("response: " + response);
                    int number = Integer.parseInt(response);
                    outputStream.write((++number + "\n").getBytes());
                }
            }
        });
        server.startListening();
    }
```
---
#### client code
```java
public static void main(String[] args) {
    SocketClient client = new SocketClient("localhost", 6666);
    client.send(new ServerTask() {
        @Override
        public String getKey() {
            return "count";
        }
        @Override
        public void connection(DataInputStream inputStream, DataOutputStream outputStream) throws IOException {
            outputStream.write((1 + "\n").getBytes()); // starting at 1
            while(true) {
                String response = new BufferedReader(new InputStreamReader(inputStream)).readLine();
                System.out.println("response: " + response);
                int number = Integer.parseInt(response);
                outputStream.write((++number + "\n").getBytes()); // respond with response + 1 (++number)
            }
        }
    });
}
```
> NOTE: using while(true) is usually a bad idea
