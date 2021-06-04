/*package com.marina.example.pub;

import com.marina.example.Client.Client;
import com.marina.example.Server.TokenServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalTime;

@Component
public class Publisher extends Thread{

  @Autowired
  PubsubOutboundGateway gateway;

  private final TokenServer tokenServer;

  public Publisher(TokenServer tokenServer){
    this.tokenServer = tokenServer;
    System.out.println("Se executa");

      this.start();
  }
  private static Logger LOGGER = LoggerFactory.getLogger(Publisher.class);

  //@Scheduled(fixedDelay = 3000)
  @Override
  public void run(){
    System.out.println("Publisher run");
    System.out.println("Din publisher " + tokenServer.getHaveToken().get());
    while(true) {

      if (tokenServer.getHaveToken().get()) {
        gateway.sendToPubsubCooking("news on cooking");
        LOGGER.info("keep alive Cooking");
      }
    }
  }

  */








 /* @Scheduled(fixedDelay = 10000, initialDelay = 10000)
  public void endHavingToken() throws IOException {
    Client client = new Client();
    System.out.println(Time.valueOf(LocalTime.now()));

    //System.out.println("c2: Enter client:" +
    client.enterSite("127.0.0.1", 20);
    if(TokenServer.getHaveToken()) {
      System.out.println("c2: " +   client.sendToken());
      client.exitSite();
    }
   // System.out.println("c2: Ended having token client2");
  }*/

  /*@Scheduled(fixedDelay = 3000)
  public void keepAliveCooking() {
    if(TokenServer.getHaveToken()) {
      gateway.sendToPubsubCooking("news on cooking");
      LOGGER.info("keep alive Cooking");
    }
  }*/
  /*@Scheduled(fixedDelay = 7000)
  public void keepAliveArt() {
    if(TokenServer.getHaveToken()) {
      gateway.sendToPubsubArt("news on Art");
      LOGGER.info("keep alive Art");
    }
  }*/
//}
