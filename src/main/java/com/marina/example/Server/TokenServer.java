package com.marina.example.Server;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class TokenServer extends Thread {
    private String token ;
    private AtomicBoolean haveToken ;
    private AtomicLong whenReceived ;

    public TokenServer(){
        this.token = "token123";
        this.haveToken = new AtomicBoolean(true);
        this.whenReceived = new AtomicLong(System.currentTimeMillis());
        this.start();

    }

    public void run()  {

        ServerSocket tokenChannel = null;
        try {
            tokenChannel = new ServerSocket(130);
            System.out.println("tokenServer run 130");

        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true)
        {
            Socket socketForClient = null;
            try {
                socketForClient = tokenChannel.accept();
                System.out.println("tokenServer run 130 accept");

                System.out.println("");
            } catch (IOException e) {
                e.printStackTrace();
            }
            new ClientHandler(socketForClient, this).start();

        }
    }

    public void setToken(String tokenval){
        token = tokenval;
    }

    public String getToken(){
        return token;
    }

    public AtomicBoolean getHaveToken() {
        return haveToken;
    }

    public void setHaveToken(AtomicBoolean haveToken) {
        this.haveToken = haveToken;
    }

    public AtomicLong getWhenReceived() {
        return whenReceived;
    }

    public void setWhenReceived(AtomicLong whenReceived) {
        this.whenReceived = whenReceived;
    }


}
