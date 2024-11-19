package com.example.socket.service.controller;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.example.socket.service.data.manager.SessionRequestsManager;
import com.example.socket.service.data.manager.SessionsDataManager;
import com.example.socket.service.strategy.registry.SubscriptionStrategy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WebSocketController extends TextWebSocketHandler {

	private final SubscriptionStrategy subscriptionStrategy;
 
    @Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		SessionsDataManager.addSession(session);
	}

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		if (SessionsDataManager.getSession(session.getId()) != null) {
			SessionRequestsManager.addSessionRequest(session.getId(), message.getPayload());
			subscriptionStrategy.subscribe(session.getId(), message.getPayload());
		}
	}


	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		if (SessionsDataManager.getSession(session.getId()) != null) {
			SessionsDataManager.removeSession(session.getId());
			unsubscribeSessionRequests(session.getId());
			SessionRequestsManager.removeSessionRequests(session.getId());
		}
	}

	private void unsubscribeSessionRequests(String sessionId) {
		SessionRequestsManager.getSessionRequests(sessionId).forEach(request -> subscriptionStrategy.unsubscribe(sessionId, request));
	}
    
}
