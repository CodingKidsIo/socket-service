package com.example.socket.service.strategy.registry.implementation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.socket.service.enumeration.SubscriptionType;
import com.example.socket.service.strategy.registry.SubscriptionStrategy;
import com.example.socket.service.strategy.service.SubscriptionService;
import com.example.socket.service.strategy.service.implementation.PriceFeedSubscriptionService;

@Service
public class SubscriptionStrategyImplementation implements SubscriptionStrategy {
    private final Map<SubscriptionType, SubscriptionService> subscriptionServices;

    public SubscriptionStrategyImplementation() {
        subscriptionServices = new HashMap<>();
        subscriptionServices.put(SubscriptionType.PRICEFEED, new PriceFeedSubscriptionService());
    }

    @Override
    public void subscribe(SubscriptionType subscriptionType, String sessionId, Object data) {
        SubscriptionService service = subscriptionServices.get(subscriptionType);
        service.subscribe(sessionId, data);
    }

    @Override
    public void unsubscribe(SubscriptionType type, String sessionId, Object data) {
        SubscriptionService service = subscriptionServices.get(type);
        service.unsubscribe(sessionId, data);
    }
}
