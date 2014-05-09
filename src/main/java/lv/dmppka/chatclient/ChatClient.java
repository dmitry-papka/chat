package lv.dmppka.chatclient;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient implements Runnable {
    
    private static Socket clientSocket = null;
    private static PrintStream outputStream = null;
    private static DataInputStream inputStream = null;
    private static boolean closed = false;
    private static BufferedReader inputLine = null;
    
    public static void main(String[] args) throws IOException {
        int port = 1234;
        String host = "localhost";
        
        try {
            clientSocket = new Socket(host, port);
            inputLine = new BufferedReader(new InputStreamReader(System.in));
            outputStream = new PrintStream(clientSocket.getOutputStream());
            inputStream = new DataInputStream(clientSocket.getInputStream());
        } catch (UnknownHostException e) {
        
            // Chill
        }
        
        if (clientSocket != null && outputStream != null && outputStream != null) {
            try {
                new Thread(new ChatClient()).start();
                while (!closed) {
                    outputStream.println(inputLine.readLine().trim());
                }

                outputStream.close();
                inputStream.close();
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
            }
        }
    }

    @Override
    public void run() {
        String responseLine;
        try {
            while ((responseLine = inputStream.readLine()) != null) {
            System.out.println(responseLine);
        }
        closed = true;
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }
    }
    
}
