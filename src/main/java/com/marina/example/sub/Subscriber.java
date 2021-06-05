package com.marina.example.sub;
/*
- subscriberul primeste mesajele de la canalele la care a facut subscribe
 */

import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class Subscriber {

  private static final Logger LOGGER = LoggerFactory.getLogger(Subscriber.class);
  private String sentMessage = null;
  // Define what happens to the messages arriving in the message channel.

  @ServiceActivator(inputChannel = "cookingInputMessageChannel")
  public void messageReceiverCooking(
          String payload,
          @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {

    if (!payload.equals(sentMessage)) {
      LOGGER.info("<<Cooking>>: " + payload);
    }
    message.ack();
  }

  @ServiceActivator(inputChannel = "artInputMessageChannel")
  public void messageReceiverArt(
          String payload,
          @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {

    if (!payload.equals(sentMessage)) {
      LOGGER.info("<<Art>>: " + payload);
    }
    message.ack();
  }

  public String getSentMessage() {
    return sentMessage;
  }

  public void setSentMessage(String sentMessage) {
    this.sentMessage = sentMessage;
  }
}
