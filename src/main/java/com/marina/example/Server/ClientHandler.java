package com.marina.example.Server;

/*
- serverul asteapta primirea tokenului
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Time;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class ClientHandler extends Thread{

    private Socket socketForClientClient;
    private BufferedReader fromClient;
    private PrintWriter toClient;
    private  TokenServer tokenServer;

    public ClientHandler(Socket socketForClient, TokenServer tokenServer) {
        this.socketForClientClient = socketForClient;
        this.tokenServer = tokenServer;
    }

    @Override
    public void run() {
        try {
            toClient = new PrintWriter(socketForClientClient.getOutputStream(), true);
            fromClient = new BufferedReader(new InputStreamReader(socketForClientClient.getInputStream()));

            String message;

            while ((message = fromClient.readLine()) != null ) {

                if(message.contains("token")) {
                    tokenServer.setWhenReceived(new AtomicLong(System.currentTimeMillis()));
                    tokenServer.setToken(message);
                    tokenServer.setHaveToken(new AtomicBoolean(true));

                    System.out.println("Client1: Got the token: " + message);
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
