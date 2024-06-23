package com.example.investbot.service;

import com.example.investbot.adapter.client.CurrencyClient;
import com.example.investbot.adapter.client.StockClient;
import com.example.investbot.adapter.dto.currency.CurrencyRateRequest;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Log4j2
@Transactional
public class InvestService {
    UserService userService;
    CurrencyClient currencyClient;
    StockClient stockClient;

    public String findInstrument(String keySearch, String type){
        return stockClient.findInstrument(keySearch,type).get(0).name();
    }

    public String findCurrency(String charCode){
        CurrencyRateRequest currencyRateRequest = currencyClient.getCurrency(charCode);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = dtf.format(currencyRateRequest.date());
        String currencyResponse = String.format("%s (%s)\nНоминал: %d\nСтоимость: %s %s\nИнформация актуальна на %sг.",
                currencyRateRequest.name(),
                currencyRateRequest.charCode(),
                currencyRateRequest.nominal(),
                currencyRateRequest.rate().value(),
                currencyRateRequest.rate().charCode(),
                date);
        return currencyResponse;
    }
}
