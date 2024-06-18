package com.example.investbot.adapter.client;

import com.example.investbot.adapter.dto.stock.PriceRequest;
import com.example.investbot.adapter.dto.stock.StockRequest;
import com.example.investbot.adapter.dto.stock.StockShortRequest;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "stockclient", url = "${service.tinkoff-invest.url}")
public interface StockClient {
    @GetMapping("/stock")
    StockRequest getStock(@RequestParam("uid") String uid);
    @GetMapping("/stock/price")
    PriceRequest priceStock(@RequestParam("uid") String uid);
    @GetMapping("/instrument/find")
    List<StockShortRequest> findInstrument(@RequestParam(value = "param") String param,
                                           @RequestParam(value = "type")String type);
}
