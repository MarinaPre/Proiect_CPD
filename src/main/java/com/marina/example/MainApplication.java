package com.marina.example;

import com.marina.example.Client.Client;
import com.marina.example.Server.TokenServer;
import com.marina.example.pub.Publisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@SpringBootApplication
@EnableScheduling
public class MainApplication {

  public static void main(String[] args) throws IOException {

   /* TokenServer tokenServer = new TokenServer();
    tokenServer.start();

    Client client = new Client(tokenServer);
    client.start();*/
    //Publisher publisher = new Publisher(tokenServer);

    SpringApplication.run(MainApplication.class, args);

    /*System.out.println("Get token: " + client.sendToken());
    client.exitSite();*/

  }
}
