package com.example.investbot.adapter.dto.stock;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceRequest (String currency, BigDecimal lastPrice, LocalDateTime timeLastPrice) {
}
