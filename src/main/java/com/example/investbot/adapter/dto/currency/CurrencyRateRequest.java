package com.example.investbot.adapter.dto.currency;

import java.time.LocalDate;

public record CurrencyRateRequest(String charCode, String name, int nominal, MoneyValueRequest rate, LocalDate date) {
}
