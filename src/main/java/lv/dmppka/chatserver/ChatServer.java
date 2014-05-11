package lv.dmppka.chatserver;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    private static ServerSocket serverSocket = null;
    private static Socket clientSocket = null;
    
    private static final int MAX_CONNECTIONS = 10;
    private static final int PORT = 1234;

    private static final ClientThread[] threads = new ClientThread[MAX_CONNECTIONS];
    
    public static void main(String[] args) {
        
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
             System.out.println(e);
        }
        
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                int i = 0;
                for (i=0; i<MAX_CONNECTIONS; i++) {
                    if (threads[i] == null) {
                        (threads[i] = new ClientThread(clientSocket, threads)).start();
                         break;
                    }
                }
                
                if (i == MAX_CONNECTIONS) {
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
