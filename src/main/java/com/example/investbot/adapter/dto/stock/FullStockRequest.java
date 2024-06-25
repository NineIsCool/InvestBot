package com.example.investbot.adapter.dto.stock;

import java.time.LocalDateTime;

public record FullStockRequest (
        String name,
        String uid,
        int lot,
        double lastPrice,
        String currency,
        String date

) {
}
