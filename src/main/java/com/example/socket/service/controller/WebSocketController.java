package com.example.socket.service.controller;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.example.socket.service.data.manager.SessionRequestsManager;
import com.example.socket.service.data.manager.SessionsDataManager;
import com.example.socket.service.request.BaseRequest;
import com.example.socket.service.strategy.registry.SubscriptionStrategy;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WebSocketController extends TextWebSocketHandler {

	private static final ObjectMapper objectMapper = new ObjectMapper();
	private final SubscriptionStrategy subscriptionStrategy;
 
    @Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		SessionsDataManager.addSession(session);
	}

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		if (SessionsDataManager.getSession(session.getId()) == null) {
			throw new IllegalArgumentException("Session not found: " + session.getId());
		}
		BaseRequest<Object> subscriptionRequest = objectMapper.readValue(message.getPayload(), new TypeReference<BaseRequest<Object>>() {});
		SessionRequestsManager.addSessionRequest(session.getId(), subscriptionRequest);
		subscriptionStrategy.subscribe(subscriptionRequest.getType(), session.getId(), subscriptionRequest.getData());
	}


	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		if (SessionsDataManager.getSession(session.getId()) == null) {
			throw new IllegalArgumentException("Session not found: " + session.getId());
		}
		SessionsDataManager.removeSession(session.getId());
		SessionRequestsManager.getSessionRequests(session.getId()).forEach(request -> subscriptionStrategy.unsubscribe(request.getType(), session.getId(), request.getData()));
		SessionRequestsManager.removeSessionRequests(session.getId());
	}
    
}
