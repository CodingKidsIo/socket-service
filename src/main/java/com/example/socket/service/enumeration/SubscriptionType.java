package com.example.socket.service.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SubscriptionType {
    PRICEFEED("pricefeed");

    private final String type;
    
    SubscriptionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @JsonCreator
    public static SubscriptionType fromType(String type) {
        for (SubscriptionType subscriptionType : values()) {
            if (subscriptionType.type.equalsIgnoreCase(type)) {
                return subscriptionType;
            }
        }
        throw new IllegalArgumentException("Unknown type: " + type);
    }
}
