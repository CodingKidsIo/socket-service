package com.example.socket.service.strategy.registry;

import com.example.socket.service.enumeration.SubscriptionType;

public interface PublishStrategy {
    public void publish(SubscriptionType subscriptionType, String data);
}
