package com.example.investbot.config;

import com.example.investbot.adapter.bot.InvestBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfig {
    @Bean
    public TelegramBotsApi telegramBotsApi(InvestBot investBot){
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(investBot);
            return api;
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
