package com.example.investbot.service;

import com.example.investbot.adapter.client.CurrencyClient;
import com.example.investbot.adapter.client.StockClient;
import com.example.investbot.adapter.dto.currency.CurrencyRateRequest;
import com.example.investbot.service.mapper.CurrencyMapper;
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
    CurrencyClient currencyClient;
    StockClient stockClient;
    CurrencyMapper currencyMapper;

    public String findInstrument(String keySearch, String type){
        return stockClient.findInstrument(keySearch,type).get(0).name();
    }

    public String findCurrency(String charCode){
        CurrencyRateRequest currencyRateRequest = currencyClient.getCurrency(charCode);
        return currencyMapper.currencyToString(currencyRateRequest);
    }
    public String convertCurrency(String charCode, String convertCharCode){
        CurrencyRateRequest convertedCurrency = currencyClient.convertCurrency(charCode,convertCharCode);
        return currencyMapper.currencyToString(convertedCurrency);
    }


}
