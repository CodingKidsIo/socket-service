package com.example.socket.service.messagequeue.consumer;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.socket.service.strategy.registry.implementation.PublishStrategyImplementation;

public class PriceFeedConsumerTests {

    private PriceFeedConsumer priceFeedConsumer;

    @BeforeEach
    public void setup() {
        priceFeedConsumer = new PriceFeedConsumer(new PublishStrategyImplementation());
    }

    @Test
    public void testListenPriceFeedQueueEmptyMessage() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> priceFeedConsumer.listenPriceFeedQueue(""));
        assertTrue(exception.getMessage().equals("Failed to parse data"));
    }

    @Test
    public void testListenPriceFeedQueue() {
        String message = "{\"stock\": \"RELIANCE\", \"price\": 298.7}";
        priceFeedConsumer.listenPriceFeedQueue(message);
    }
    
}
