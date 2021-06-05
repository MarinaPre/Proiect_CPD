package com.marina.example.pub;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface PubsubOutboundGateway {
  @Gateway(requestChannel = "cookingOutputChannel")
  void sendToPubsubCooking(String text);

  @Gateway(requestChannel = "artOutputChannel")
  void sendToPubsubArt(String text);

}
