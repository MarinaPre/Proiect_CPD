package com.marina.example.pub;
/*
- facem pub la mesaje in functie de topic
 */
import com.marina.example.Server.TokenServer;
import com.marina.example.sub.Subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Publisher extends Thread {
    @Autowired
    PubsubOutboundGateway gateway;
    private static Logger LOGGER =  LoggerFactory.getLogger(Publisher.class);
    private final TokenServer tokenServer;
    private final Subscriber subscriber;
    private boolean askedToSendToken;

    public Publisher(TokenServer tokenServer, Subscriber subscriber){
        this.tokenServer = tokenServer;
        this.subscriber = subscriber;
        this.askedToSendToken = false;
    }

    @Override
    public void run() {
        Scanner scan = new Scanner(System.in);
        String message;
        String[] messageSplit;

        while(true) {
            message = scan.nextLine();

            if (tokenServer.getHaveToken().get()) {
                messageSplit= message.split(":");

                if(messageSplit[0].equals("Cooking")) {
                    gateway.sendToPubsubCooking(messageSplit[1]);
                    subscriber.setSentMessage(messageSplit[1]);

                }else if(messageSplit[0].equals("Art")){
                    gateway.sendToPubsubArt(messageSplit[1]);
                    subscriber.setSentMessage(messageSplit[1]);

                }else if(message.equalsIgnoreCase("Send Token")){
                   this.askedToSendToken = true;
                }else{
                    System.out.println("You are not subscribed to: " + messageSplit[0]);
                }
            }else{
                System.out.println("It's not your time to talk!");
            }
        }
    }

    public boolean isAskedToSendToken() {
        return askedToSendToken;
    }

    public void setAskedToSendToken(boolean askedToSendToken) {
        this.askedToSendToken = askedToSendToken;
    }
}
