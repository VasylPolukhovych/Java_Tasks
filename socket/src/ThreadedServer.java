import java.io.*;
import java.net.*;
import java.util.*;

public class ThreadedServer {
    private static ServerSocket server;

    public static void main(String[] args) {
        try {
            try {
                int i = 1;
                server = new ServerSocket(8189, 10);
                System.out.println("Server has been started!");
                while (true) {
                    Socket clientSocket = server.accept();
                    System.out.println("Client " + i);
                    Runnable r = new ThreadedEchoHandler(clientSocket, i);
                    Thread t = new Thread(r);
                    t.start();
                    i++;
                }
            } finally {
                System.out.println("Server has been closed!");
                server.close();
            }
        } catch (IOException e) {
            System.err.println(e);

        }
    }
}