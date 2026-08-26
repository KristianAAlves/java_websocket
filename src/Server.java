import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static void main(String[] args){
        Server socket = null;

        try {
            InetAddress addr = InetAddress.getByName("0.0.0.0");
            socket = new Server(5000, 2, addr);
        }catch(IOException ioError){
            System.out.println(ioError.getMessage());
        }

    }
    private ServerSocket serverSocket = null;

    //private List<User> clients = null;

    public Server(int port, int backlog, InetAddress bindAddr) throws IOException{
        try {

            serverSocket = new ServerSocket(port, backlog, bindAddr);
            System.out.println("server started");
            while (true) {

                Socket clientSocket = serverSocket.accept();

                System.out.println(
                        "Client connected: " +
                                clientSocket.getRemoteSocketAddress()
                );

                Thread clientThread = new Thread(
                        new ClientHandler(clientSocket)
                );

                clientThread.start();
            }


        }catch(IOException ioError){
            System.out.println(ioError.getMessage());
        }
    }

    static class ClientHandler implements Runnable {

        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

            try (
                    DataInputStream input =
                            new DataInputStream(
                                    new BufferedInputStream(
                                            socket.getInputStream()
                                    )
                            )
            ) {

                String message;

                while ((message = input.readUTF()) != null) {

                    System.out.println(
                            "Client " +
                                    socket.getRemoteSocketAddress() +
                                    ": " +
                                    message
                    );

                    if (message.equals("bumbip")) {
                        break;
                    }
                }

            } catch (IOException e) {

                System.out.println(
                        "Client disconnected: " +
                                e.getMessage()
                );

            } finally {

                try {
                    socket.close();
                } catch (IOException ignored) {
                }
            }
        }
    }


}

