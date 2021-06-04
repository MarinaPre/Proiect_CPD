package com.marina.example.Client;

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

    public TokenServer getTokenServer(){
        return  tokenServer;
    }
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

        //toServer.println("ClientA");
       // return fromServer.readLine();
    }

    public String sendToken() throws IOException {
        toServer.println(tokenServer.getToken());
        tokenServer.setHaveToken(new AtomicBoolean(false));
        tokenServer.setToken(null);
        return fromServer.readLine();
    }

    public void exitSite() throws IOException {
        //toServer.println("Out");

        toServer.close();
        fromServer.close();
        socketClient.close();
    }
    @Override
    public void run() {

        System.out.println("client run");
        while(true) {
            if(tokenServer.getHaveToken().get()) {

                long currentTime = System.currentTimeMillis();
                if (currentTime - tokenServer.getWhenReceived().get() >= 10000) {
                    System.out.println("client run have token and time ");

                    try {
                        this.enterSite("127.0.0.1", 110);
                        System.out.println(this.sendToken());
                        System.out.println("A: nu mai am tokenul");
                        System.out.println("Din client: am token? : " + tokenServer.getHaveToken().get());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
