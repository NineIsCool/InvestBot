package com.example.investbot.adapter.dto.currency;

import java.math.BigDecimal;

public record MoneyValueRequest (String charCode, BigDecimal value) {
}
