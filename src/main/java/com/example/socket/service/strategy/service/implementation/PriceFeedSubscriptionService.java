package com.example.socket.service.strategy.service.implementation;

import com.example.socket.service.data.manager.PriceFeedSubscriptionsManager;
import com.example.socket.service.request.PriceFeedRequest;
import com.example.socket.service.strategy.service.SubscriptionService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PriceFeedSubscriptionService implements SubscriptionService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void subscribe(String sessionId, Object data) {
        PriceFeedRequest priceFeedRequest = getPriceFeedRequest(data);
        if (priceFeedRequest != null && priceFeedRequest.getStocks() != null) {
            priceFeedRequest.getStocks().forEach(stock -> PriceFeedSubscriptionsManager.addSubscription(stock, sessionId));
        }
    }

    @Override
    public void unsubscribe(String sessionId, Object data) {
        PriceFeedRequest priceFeedRequest = getPriceFeedRequest(data);
        if (priceFeedRequest.getStocks() != null) {
            priceFeedRequest.getStocks().forEach(stock -> PriceFeedSubscriptionsManager.removeSubscription(stock, sessionId));
        }
    }

    private PriceFeedRequest getPriceFeedRequest(Object data) {
        return objectMapper.convertValue(data, PriceFeedRequest.class);
    }
    
}
