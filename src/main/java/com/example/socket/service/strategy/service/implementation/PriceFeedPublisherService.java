package com.example.socket.service.strategy.service.implementation;

import java.io.IOException;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.example.socket.service.data.manager.PriceFeedSubscriptionsManager;
import com.example.socket.service.data.manager.SessionsDataManager;
import com.example.socket.service.request.StockPrice;
import com.example.socket.service.strategy.service.PublisherService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PriceFeedPublisherService implements PublisherService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void publish(String data) {
        StockPrice stockPrice = getStockPrice(data);
        PriceFeedSubscriptionsManager.getSubscriptions(stockPrice.getStock()).forEach(sessionId -> sendDataBySession(sessionId, stockPrice));
    }

    private StockPrice getStockPrice(String data) {
        try {
            return objectMapper.readValue(data, StockPrice.class);
        } catch (IOException exception) {
            throw new IllegalArgumentException("Failed to parse data");
        }
    }

    @Async
    private void sendDataBySession(String sessionId, StockPrice data) {
        try {
            WebSocketSession session = SessionsDataManager.getSession(sessionId);
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(data)));
        } catch (IOException exception) {
            throw new RuntimeException("Failed to send data to session: " + sessionId, exception);
        }
    }
    
}
