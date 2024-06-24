package com.example.investbot.service.mapper;

import com.example.investbot.adapter.dto.currency.CurrencyRateRequest;
import com.example.investbot.adapter.dto.stock.PriceRequest;
import com.example.investbot.adapter.dto.stock.StockRequest;
import org.springframework.stereotype.Component;

import java.math.MathContext;
import java.time.format.DateTimeFormatter;

@Component
public class StockMapper {
    public String stockToString(StockRequest stockRequest, PriceRequest priceRequest){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyyг. hh:mm:ss");
        String date = dtf.format(priceRequest.timeLastPrice());
        return String.format("%s (%s)\nМинимальное количество к покупке: %d\nСтоимость: %.5f %s\nИнформация актуальна на %s (UTC+3)",
                stockRequest.name(),
                stockRequest.ticker(),
                stockRequest.lot(),
                priceRequest.lastPrice().doubleValue(),
                priceRequest.currency().toUpperCase(),
                date);
    }
}
