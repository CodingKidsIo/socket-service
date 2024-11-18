package com.example.socket.service.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StockPrice {
    private String stock;
    private Double price;
}
