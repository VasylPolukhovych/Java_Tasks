import java.io.*;
import java.net.*;
import java.util.*;

class ThreadedEchoHandler implements Runnable {
    private Socket clientSocket;
    private int clientNumber;
    private static BufferedReader in;
    private static BufferedWriter out;

    public ThreadedEchoHandler(Socket i, int j) {
        clientSocket = i;
        this.clientNumber = j;
    }

    public void run() {
        try {
            try {
                while (true) {
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                    String word = in.readLine(); // wait for client
                    System.out.println(word);

                    out.write("Hello, it is Server! Client " + clientNumber + " written : " + word + "\n");
                    out.flush();
                    if (word.equals("Exit")) {
                        clientSocket.close();
                        in.close();
                        out.close();
                        break;
                    }

                }

            } finally {
                System.out.println("Client has been closed");
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

