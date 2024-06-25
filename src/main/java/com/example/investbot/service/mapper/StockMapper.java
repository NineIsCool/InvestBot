package com.example.investbot.service.mapper;

import com.example.investbot.adapter.dto.currency.CurrencyRateRequest;
import com.example.investbot.adapter.dto.stock.FullStockRequest;
import com.example.investbot.adapter.dto.stock.PriceRequest;
import com.example.investbot.adapter.dto.stock.StockRequest;
import org.springframework.stereotype.Component;

import java.math.MathContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StockMapper {
    public String stockToString(StockRequest stockRequest, PriceRequest priceRequest){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyyг. hh:mm:ss");
        String date = dtf.format(priceRequest.timeLastPrice());
        return String.format("%s(%s)\nМинимальное количество к покупке: %d\nСтоимость: %.5f %s\nИнформация актуальна на %s (UTC+3)",
                stockRequest.name(),
                stockRequest.uid(),
                stockRequest.lot(),
                priceRequest.lastPrice().doubleValue(),
                priceRequest.currency().toUpperCase(),
                date);
    }
    public FullStockRequest stringToStock(String stringStock){
        String regex = "(.+)\\((.+)\\)\\nМинимальное количество к покупке: (\\d+)\\nСтоимость: (\\d+\\,\\d+) (.{3})\\nИнформация актуальна на (.+) \\(UTC\\+3\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(stringStock);
        if (matcher.find()) {
            String name = matcher.group(1).trim();
            String uid = matcher.group(2).trim();
            int lot = Integer.parseInt(matcher.group(3).trim());
            double price = Double.parseDouble(matcher.group(4).trim().replace(",","."));
            String currency = matcher.group(5).trim();
            String ds = matcher.group(6).trim();
            String dateTime = matcher.group(6).trim();
            FullStockRequest stockRequest = new FullStockRequest(name, uid, lot, price, currency, dateTime);
            return stockRequest;
        }
        return null;
    }

    public String fullStockToString(FullStockRequest fullStockRequest){
        return String.format("%s(%s)\nМинимальное количество к покупке: %d\nСтоимость: %.5f %s\nИнформация актуальна на %s (UTC+3)",
                fullStockRequest.name(),
                fullStockRequest.uid(),
                fullStockRequest.lot(),
                fullStockRequest.lastPrice(),
                fullStockRequest.currency().toUpperCase(),
                fullStockRequest.date());
    }
}
