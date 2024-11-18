package com.example.socket.service.messagequeue.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.example.socket.service.enumeration.SubscriptionType;
import com.example.socket.service.strategy.registry.PublishStrategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PriceFeedConsumer {

    private final PublishStrategy publishStrategy;

    @RabbitListener(queues = "#{@priceFeedQueueName}")
    public void listenPriceFeedQueue(String message) {
        log.info("message: {} from price feed queue", message);
        publishStrategy.publish(SubscriptionType.PRICEFEED, message);
    }

}
