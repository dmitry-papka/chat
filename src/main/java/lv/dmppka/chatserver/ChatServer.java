package lv.dmppka.chatserver;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    private static ServerSocket serverSocket = null;
    private static Socket clientSocket = null;
    
    private static final int maxConnections = 10;
    private static final ClientThread[] threads = new ClientThread[maxConnections];
    
    public static void main(String[] args) {
        int portNumber = 1234;
        
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
             System.out.println(e);
        }
        
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                int i = 0;
                for (i=0; i<maxConnections; i++) {
                    if (threads[i] == null) {
                        (threads[i] = new ClientThread(clientSocket, threads)).start();
                         break;
                    }
                }
                
                if (i == maxConnections) {
                    PrintStream os = new PrintStream(clientSocket.getOutputStream());
                    os.println("Too much connections");
                    os.close();
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
