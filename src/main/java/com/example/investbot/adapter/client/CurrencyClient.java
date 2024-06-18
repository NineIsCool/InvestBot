package com.example.investbot.adapter.client;

import com.example.investbot.adapter.dto.currency.CurrencyRateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "currencyclient", url = "${service.cbr-currencies.url}")
public interface CurrencyClient {
    @GetMapping
    CurrencyRateRequest getCurrency(@RequestParam("charCode")String charCode);

    @GetMapping("/convert")
    CurrencyRateRequest convertCurrency(@RequestParam("charCode") String charCode,
                                        @RequestParam("convertCharCode") String convertCharCode);
}
