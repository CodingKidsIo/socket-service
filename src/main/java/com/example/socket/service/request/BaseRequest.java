package com.example.socket.service.request;

import com.example.socket.service.enumeration.SubscriptionType;

import lombok.Data;

@Data
public class BaseRequest<T> {
    private SubscriptionType type;
    private T data;  
}
