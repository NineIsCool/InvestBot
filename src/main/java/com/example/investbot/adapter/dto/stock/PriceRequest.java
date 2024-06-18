package com.example.investbot.adapter.dto.stock;

import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record PriceRequest (String charCode, BigDecimal lastPrice, LocalDateTime time) {
}
