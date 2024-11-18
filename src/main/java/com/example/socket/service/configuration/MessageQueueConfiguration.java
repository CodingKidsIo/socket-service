package com.example.socket.service.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.socket.service.data.manager.QueuesManager;
import com.example.socket.service.enumeration.SubscriptionType;

@Configuration
public class MessageQueueConfiguration {
    
    @Bean
    public FanoutExchange fanoutExchangeOfPriceFeed() {
        return new FanoutExchange("price-feed");
    }

    @Bean(name = "priceFeedQueueName")
    public String priceFeedQueueName() {
        return QueuesManager.getQueue(SubscriptionType.PRICEFEED);
    }

    @Bean
    public Queue priceFeedQueue(@Value("#{priceFeedQueueName}") String priceFeedQueueName) {
        return new Queue(priceFeedQueueName);
    }

    @Bean
    public Binding priceFeeBinding(FanoutExchange fanoutExchangeOfPriceFeed, Queue priceFeedQueue) {
        return BindingBuilder.bind(priceFeedQueue).to(fanoutExchangeOfPriceFeed);
    }

}

