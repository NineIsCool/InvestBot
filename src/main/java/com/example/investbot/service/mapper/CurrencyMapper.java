package com.example.investbot.service.mapper;

import com.example.investbot.adapter.dto.currency.CurrencyRateRequest;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class CurrencyMapper {
    public String currencyToString(CurrencyRateRequest currencyRateRequest){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = dtf.format(currencyRateRequest.date());
        return String.format("%s (%s)\nНоминал: %d\nСтоимость: %s %s\nИнформация актуальна на %sг.",
                currencyRateRequest.name(),
                currencyRateRequest.charCode(),
                currencyRateRequest.nominal(),
                currencyRateRequest.rate().value(),
                currencyRateRequest.rate().charCode(),
                date);
    }
}
