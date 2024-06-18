package com.example.investbot.adapter.dto.stock;

public record StockRequest (String name,
                            String uid,
                            String ticker,
                            String currency,
                            int lot) {
}
