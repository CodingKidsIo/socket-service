package com.example.socket.service.data.manager;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.socket.WebSocketSession;

public class SessionsDataManager {
    private static final Map<String, WebSocketSession> sessions = new HashMap<>();

    public static WebSocketSession getSession(String id) {
        return sessions.get(id);
    }

    public static void addSession(WebSocketSession session) {
        sessions.put(session.getId(), session);
    }

    public static void removeSession(String id) {
        sessions.remove(id);
    }
}
