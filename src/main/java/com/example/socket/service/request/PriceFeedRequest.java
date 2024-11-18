package com.example.socket.service.request;

import java.util.List;

import lombok.Data;

@Data
public class PriceFeedRequest {
    private List<String> stocks;
}
