package com.marina.example.Server;

import com.marina.example.pub.Publisher;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

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
               // System.out.println("s2: From clien1: " + message);
                System.out.println(Time.valueOf(LocalTime.now()));

                if(message.contains("token")) {
                    //System.out.println("s2: raspuns din clienthandler 2: " + message);
                    tokenServer.setWhenReceived(new AtomicLong(System.currentTimeMillis()));
                    tokenServer.setToken(message);
                    tokenServer.setHaveToken(new AtomicBoolean(true));
                    toClient.println("Got the tokens1");
                    System.out.println("am tokens1 " + message);
                    System.out.println("Din client handler: " + tokenServer.getHaveToken().get());
                }
                else if(message.contains("Client")){
                   // toClient.println("Welcome!s1 " + message);
                    //System.out.println("s2: raspuns server: " + "Welcome!");

                }
                else if(message.contains("Out")){
                    //System.out.println("s2: raspuns server: " + "Bye!");
                    break;
                }
            }
            //socketForClientClient.close();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
