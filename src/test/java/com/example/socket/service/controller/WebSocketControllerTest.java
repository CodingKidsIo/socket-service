package com.example.socket.service.controller;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.example.socket.service.data.manager.PriceFeedSubscriptionsManager;
import com.example.socket.service.data.manager.SessionRequestsManager;
import com.example.socket.service.data.manager.SessionsDataManager;
import com.example.socket.service.strategy.registry.implementation.SubscriptionStrategyImplementation;


public class WebSocketControllerTest {

    private WebSocketSession webSocketSession;
    private WebSocketController webSocketController;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        webSocketSession = mock(WebSocketSession.class);
        webSocketController = new WebSocketController(new SubscriptionStrategyImplementation());
    }

    @Test
    public void testAfterConnectionEstablished() throws Exception {
        when(webSocketSession.getId()).thenReturn(UUID.randomUUID().toString());
        webSocketController.afterConnectionEstablished(webSocketSession);
        assertTrue(SessionsDataManager.getSession(webSocketSession.getId()).equals(webSocketSession));
    }

    @Test
    public void testHandleTextMessageBeforeConnection() throws Exception {
        when(webSocketSession.getId()).thenReturn(UUID.randomUUID().toString());
        String message = "{\"type\": \"pricefeed\", \"data\": {\"stocks\": [\"RELIANCE\"]}}";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> webSocketController.handleTextMessage(webSocketSession, new TextMessage(message)));
        assertEquals("Session not found: " + webSocketSession.getId(), exception.getMessage());
    }

    @Test
    public void testHandleTextMessageAfterConnection() throws Exception {
        when(webSocketSession.getId()).thenReturn(UUID.randomUUID().toString());
        webSocketController.afterConnectionEstablished(webSocketSession);
        String message = "{\"type\": \"pricefeed\", \"data\": {\"stocks\": [\"RELIANCE\"]}}";
        webSocketController.handleTextMessage(webSocketSession, new TextMessage(message));
        assertTrue(!SessionRequestsManager.getSessionRequests(webSocketSession.getId()).isEmpty());
        Set<String> subscriptions = PriceFeedSubscriptionsManager.getSubscriptions("RELIANCE");
        assertTrue(!subscriptions.isEmpty());
        assertTrue(subscriptions.contains(webSocketSession.getId()));
    }


    @Test
    public void testAfterConnectionClosedBeforeConnection() throws Exception {
        when(webSocketSession.getId()).thenReturn(UUID.randomUUID().toString());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> webSocketController.afterConnectionClosed(webSocketSession, CloseStatus.NORMAL));
        assertEquals("Session not found: " + webSocketSession.getId(), exception.getMessage());
    }

    @Test
    public void testAfterConnectionClosedAfterConnection() throws Exception {
        when(webSocketSession.getId()).thenReturn(UUID.randomUUID().toString());
        webSocketController.afterConnectionEstablished(webSocketSession);
        String message = "{\"type\": \"pricefeed\", \"data\": {\"stocks\": [\"RELIANCE\"]}}";
        webSocketController.handleTextMessage(webSocketSession, new TextMessage(message));
        webSocketController.afterConnectionClosed(webSocketSession, CloseStatus.NORMAL);
        assertNull(SessionsDataManager.getSession(webSocketSession.getId()));
    }
}