import java.io.*;
import java.net.Socket;

public class ClientMain {

    private static Socket clientSocket;
    private static BufferedReader consolReader;
    private static BufferedReader inStream;
    private static BufferedWriter outStream;

    public static void main(String[] args) {
        try {
            try {
                clientSocket = new Socket("localhost", 8189);
                System.out.println("If you want to close client then please write Exit ");
                while (true) {
                    consolReader = new BufferedReader(new InputStreamReader(System.in));

                    inStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    outStream = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                    System.out.println("Please say something:");
                    String word = consolReader.readLine(); // Wait when  client will write something

                    outStream.write(word + "\n");
                    outStream.flush();

                    String serverWord = inStream.readLine(); // Wait when server will answer

                    System.out.println(serverWord);
                    if (word.equals("Exit")) {
                        break;
                    }
                }
            } finally {
                System.out.println("Client has been closed...");
                clientSocket.close();
                inStream.close();
                outStream.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}