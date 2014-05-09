package lv.dmppka.chatserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class ClientThread extends Thread {
    
    private String clientName = null;
    private DataInputStream inputStream = null;
    private PrintStream outputStream = null;
    private Socket clientSocket = null;
    private final ClientThread[] threads;
    private int maxConnections;
    
    public ClientThread(Socket clientSocket, ClientThread[] threads) {
        this.clientSocket = clientSocket;
        this.threads = threads;
        maxConnections = threads.length;
    }
    
    public void run() {
        int maxConnections = this.maxConnections;
        ClientThread[] threads = this.threads;
        
        try {
            inputStream = new DataInputStream(clientSocket.getInputStream());
            outputStream = new PrintStream(clientSocket.getOutputStream());
            
            clientName = inputStream.readLine().trim();
            
            while (true) {
                String line = inputStream.readLine();
                
                if (line.startsWith("/quite")) {
                    break;
                }
                
                synchronized(this) {
                    for (int i=0; i<maxConnections; i++) {
                        if (threads[i] != null && threads[i].clientName != null) {
                            threads[i].outputStream.println("["+clientName+"] "+line);
                        }
                    }
                }
            }
            
            synchronized (this) {
                for (int i = 0; i < maxConnections; i++) {
                    if (threads[i] != null && threads[i] != this&& threads[i].clientName != null) {
                        threads[i].outputStream.println("User "+clientName+" leaves");
                    }
                }
            }
           
            synchronized (this) {
                for (int i = 0; i < maxConnections; i++) {
                    if (threads[i] == this) {
                        threads[i] = null;
                    }
                }
            }
            inputStream.close();
            outputStream.close();
            clientSocket.close();
        } catch (IOException e) {
            
            // Chill
        }
    }
    
}
