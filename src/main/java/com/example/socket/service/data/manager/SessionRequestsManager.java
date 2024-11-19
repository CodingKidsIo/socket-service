package com.example.socket.service.data.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionRequestsManager {
    private final static Map<String, List<String>> sessionRequests = new HashMap<>();

    public static List<String> getSessionRequests(String sessionId) {
        return sessionRequests.getOrDefault(sessionId, new ArrayList<>());
    }

    public static void addSessionRequest(String sessionId, String request) {
        if (!sessionRequests.containsKey(sessionId)) {
            sessionRequests.put(sessionId, new ArrayList<>());
        }
        sessionRequests.get(sessionId).add(request);
    }

    public static void removeSessionRequests(String sessionId) {
        sessionRequests.remove(sessionId);
    }
}
