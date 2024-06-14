package com.example.investbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InvestBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvestBotApplication.class, args);
    }

}
