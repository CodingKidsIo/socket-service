package com.example.socket.service.data.manager;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import com.example.socket.service.enumeration.SubscriptionType;

public class QueuesManager {
    private static final Map<SubscriptionType, String> queues = new HashMap<>();

    public static String getQueue(SubscriptionType subscriptionType) {
        if(!queues.containsKey(subscriptionType)) {
            queues.put(subscriptionType, subscriptionType.getType() + getIP());
        }
        return queues.get(subscriptionType);
    }

    private static String getIP() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (UnknownHostException exception) {
            throw new RuntimeException("Failed to get IP address", exception);
        }
    }
    
}
