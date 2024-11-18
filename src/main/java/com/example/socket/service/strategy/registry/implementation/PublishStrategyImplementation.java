package com.example.socket.service.strategy.registry.implementation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.socket.service.enumeration.SubscriptionType;
import com.example.socket.service.strategy.registry.PublishStrategy;
import com.example.socket.service.strategy.service.PublisherService;
import com.example.socket.service.strategy.service.implementation.PriceFeedPublisherService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PublishStrategyImplementation implements PublishStrategy {
    private final Map<SubscriptionType, PublisherService> publishStrategies;

    public PublishStrategyImplementation() {
        publishStrategies = new HashMap<>();
        publishStrategies.put(SubscriptionType.PRICEFEED, new PriceFeedPublisherService());
    }

    @Override
    public void publish(SubscriptionType subscriptionType, String data) {
        PublisherService service = publishStrategies.get(subscriptionType);
        service.publish(data);
    }
    
}
