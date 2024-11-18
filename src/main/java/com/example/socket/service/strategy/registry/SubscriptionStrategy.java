package com.example.socket.service.strategy.registry;

import com.example.socket.service.enumeration.SubscriptionType;

public interface SubscriptionStrategy {
    public void subscribe(SubscriptionType subscriptionType, String sessionId, Object data);
    public void unsubscribe(SubscriptionType subscriptionType, String sessionId, Object data);
}
