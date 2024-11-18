package com.example.socket.service.data.manager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PriceFeedSubscriptionsManager {
    private static final Map<String, Set<String>> subscriptions = new HashMap<>();

    public static void addSubscription(String stock, String sessionId) {
        if(!subscriptions.containsKey(stock)) {
            subscriptions.put(stock, new HashSet<>());
        }
        subscriptions.get(stock).add(sessionId);
    }

    public static void removeSubscription(String stock, String sessionId) {
        if(subscriptions.containsKey(stock)) {
            subscriptions.get(stock).remove(sessionId);
        }
    }

    public static Set<String> getSubscriptions(String stock) {
        return subscriptions.getOrDefault(stock, new HashSet<>());
    }
    
}
