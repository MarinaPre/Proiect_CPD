package com.marina.example.pub;

import com.marina.example.Server.TokenServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;

import java.io.PrintWriter;
import java.net.Socket;

@Component
public class Publisher extends Thread {
    @Autowired
    PubsubOutboundGateway gateway;
    private static Logger LOGGER =  LoggerFactory.getLogger(Publisher.class);

    private final TokenServer tokenServer;

    public Publisher(TokenServer tokenServer){
        this.tokenServer = tokenServer;
        System.out.println("Se executa");

       // this.start();
    }

    @Override
    public void run() {

        System.out.println("Publisher run");
        System.out.println("Din publisher " + tokenServer.getHaveToken().get());

        while(true) {
            if (tokenServer.getHaveToken().get()) {
                gateway.sendToPubsubCooking("news on cooking");
                LOGGER.info("keep alive Cooking");
            }

            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
