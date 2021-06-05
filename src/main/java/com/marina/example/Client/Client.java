package com.marina.example.Client;
/*
- clientul trimite tokenul spre urmatorul participant/client
 */
import com.marina.example.Server.TokenServer;
import com.marina.example.pub.Publisher;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Component
public class Client extends Thread {

    Socket socketClient;
    private BufferedReader fromServer;
    private PrintWriter toServer;
    private final TokenServer tokenServer;
    private final Publisher publisher;

    public Client(TokenServer tokenServer, Publisher publisher){
        this.tokenServer = tokenServer;
        this.publisher = publisher;
        this.start();
        publisher.start();
    }

    public void enterSite(String ip, int port) throws IOException {
        socketClient = new Socket(ip, port);
        fromServer = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
        toServer = new PrintWriter(socketClient.getOutputStream(), true);
    }

    public void sendToken() throws IOException {
        String token = tokenServer.getToken();
        tokenServer.setHaveToken(new AtomicBoolean(false));
        tokenServer.setToken(null);
        toServer.println(token);
    }

    public void exitSite() throws IOException {
        toServer.close();
        fromServer.close();
        socketClient.close();
    }

    @Override
    public void run() {

        while(true) {
            if(tokenServer.getHaveToken().get()) {

                long currentTime = System.currentTimeMillis();
                if (currentTime - tokenServer.getWhenReceived().get() >= 30000 || publisher.isAskedToSendToken()) {
                    publisher.setAskedToSendToken(false);
                    try {
                        this.enterSite("127.0.0.1", 110);
                        this.sendToken();
                        System.out.println("Client1: I don't have the token anymore");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
