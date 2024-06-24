package com.example.investbot.service;

import com.example.investbot.adapter.client.CurrencyClient;
import com.example.investbot.adapter.client.StockClient;
import com.example.investbot.adapter.dto.currency.CurrencyRateRequest;
import com.example.investbot.adapter.dto.stock.PriceRequest;
import com.example.investbot.adapter.dto.stock.StockRequest;
import com.example.investbot.adapter.dto.stock.StockShortRequest;
import com.example.investbot.service.mapper.CurrencyMapper;
import com.example.investbot.service.mapper.StockMapper;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Log4j2
@Transactional
public class InvestService {
    CurrencyClient currencyClient;
    StockClient stockClient;
    CurrencyMapper currencyMapper;
    StockMapper stockMapper;

    public List<StockShortRequest> findInstrument(String keySearch, String type){
        return stockClient.findInstrument(keySearch,type);
    }

    public String findCurrency(String charCode){
        CurrencyRateRequest currencyRateRequest = currencyClient.getCurrency(charCode);
        return currencyMapper.currencyToString(currencyRateRequest);
    }
    public String convertCurrency(String charCode, String convertCharCode){
        CurrencyRateRequest convertedCurrency = currencyClient.convertCurrency(charCode,convertCharCode);
        return currencyMapper.currencyToString(convertedCurrency);
    }

    public String getStockByUID(String uid){
        StockRequest stock=  stockClient.getStock(uid);
        PriceRequest price = stockClient.priceStock(uid);
        return stockMapper.stockToString(stock,price);
    }
}
