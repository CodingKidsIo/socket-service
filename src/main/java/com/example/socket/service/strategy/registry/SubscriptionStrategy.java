package com.example.socket.service.strategy.registry;

public interface SubscriptionStrategy {
    public void subscribe(String sessionId, String data);
    public void unsubscribe(String sessionId, String data);
}
