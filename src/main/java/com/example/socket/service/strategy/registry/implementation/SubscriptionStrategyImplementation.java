package com.example.socket.service.strategy.registry.implementation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.socket.service.enumeration.SubscriptionType;
import com.example.socket.service.request.BaseRequest;
import com.example.socket.service.strategy.registry.SubscriptionStrategy;
import com.example.socket.service.strategy.service.SubscriptionService;
import com.example.socket.service.strategy.service.implementation.PriceFeedSubscriptionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SubscriptionStrategyImplementation implements SubscriptionStrategy {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<SubscriptionType, SubscriptionService> subscriptionServices;

    public SubscriptionStrategyImplementation() {
        subscriptionServices = new HashMap<>();
        subscriptionServices.put(SubscriptionType.PRICEFEED, new PriceFeedSubscriptionService());
    }

    @Override
    public void subscribe(String sessionId, String data) {
        BaseRequest<Object> baseRequest = getBaseRequest(data);
        SubscriptionService service = subscriptionServices.get(baseRequest.getType());
        service.subscribe(sessionId, baseRequest.getData());
    }

    @Override
    public void unsubscribe(String sessionId, String data) {
        BaseRequest<Object> baseRequest = getBaseRequest(data);
        SubscriptionService service = subscriptionServices.get(baseRequest.getType());
        service.unsubscribe(sessionId, baseRequest.getData());
    }

    private BaseRequest<Object> getBaseRequest(String data) {
        try {
            return objectMapper.readValue(data, new TypeReference<BaseRequest<Object>>() {});
        } catch (JsonProcessingException exception) {
            throw new IllegalArgumentException("Failed to parse data", exception);
        }
    }

}
