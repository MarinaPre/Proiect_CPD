package com.marina.example.config;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.AckMode;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

@Component
public class GcpConfiguration {

  // Create a message channel for messages arriving from the subscription `CookingSub`.
  @Bean
  public MessageChannel cookingInputMessageChannel() {
    return new PublishSubscribeChannel();
  }

  // Create an inbound channel adapter to listen to the subscription `CookingSub` and send
  // messages to the input message channel.
  @Bean
  public PubSubInboundChannelAdapter inboundChannelAdapterCooking(
          @Qualifier("cookingInputMessageChannel") MessageChannel messageChannel,
          PubSubTemplate pubSubTemplate) {
    PubSubInboundChannelAdapter adapter =
            new PubSubInboundChannelAdapter(pubSubTemplate, "CookingSubClient1");
    adapter.setOutputChannel(messageChannel);
    adapter.setAckMode(AckMode.MANUAL);
    adapter.setPayloadType(String.class);
    return adapter;
  }

  @Bean
  public MessageChannel cookingOutputChannel() {
    return new PublishSubscribeChannel();
  }

  @Bean
  @ServiceActivator(inputChannel = "cookingOutputChannel")
  public MessageHandler messageSenderCooking(PubSubTemplate pubsubTemplate) {
    return new PubSubMessageHandler(pubsubTemplate, "Cooking");
  }
  // Create a message channel for messages arriving from the subscription `ArtSub`.
  @Bean
  public MessageChannel artInputMessageChannel() {
    return new PublishSubscribeChannel();
  }

  // Create an inbound channel adapter to listen to the subscription `ArtSub` and send
  // messages to the input message channel.
  @Bean
  public PubSubInboundChannelAdapter inboundChannelAdapterArt(
          @Qualifier("artInputMessageChannel") MessageChannel messageChannel,
          PubSubTemplate pubSubTemplate) {
    PubSubInboundChannelAdapter adapter =
            new PubSubInboundChannelAdapter(pubSubTemplate, "ArtSubClient1");
    adapter.setOutputChannel(messageChannel);
    adapter.setAckMode(AckMode.MANUAL);
    adapter.setPayloadType(String.class);
    return adapter;
  }

  @Bean
  public MessageChannel artOutputChannel() {
    return new PublishSubscribeChannel();
  }

  @Bean
  @ServiceActivator(inputChannel = "artOutputChannel")
  public MessageHandler messageSenderArt(PubSubTemplate pubsubTemplate) {
    return new PubSubMessageHandler(pubsubTemplate, "Art");
  }

}
