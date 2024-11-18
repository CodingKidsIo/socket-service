package com.example.socket.service.strategy.service;

public interface SubscriptionService {
    public void subscribe(String sessionId, Object data);
    public void unsubscribe(String sessionId, Object data);
}
