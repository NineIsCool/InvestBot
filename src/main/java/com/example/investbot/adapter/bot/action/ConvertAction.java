package com.example.investbot.adapter.bot.action;

import com.example.investbot.service.InvestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ConvertAction implements BotAction {
    InvestService investService;
    ConcurrentHashMap<String, Message> previousMessage = new ConcurrentHashMap<>();

    @Override
    public BotApiMethod handle(Update update) {
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        previousMessage.put(chatId,(Message) update.getCallbackQuery().getMessage());
        String text = "Введите трехбуквенный код валюты:";
        return new SendMessage(chatId, text);
    }

    @Override
    public BotApiMethod callback(Update update) {
        if (update.hasCallbackQuery()){
            return handle(update);
        }
        Message msg = update.getMessage();
        String chatId = msg.getChatId().toString();
        Message mes = previousMessage.get(chatId);
        String keySearch = mes.getText().split("[()]")[1];
        String convertKeySearch = msg.getText();
        if (keySearch.length()==3){
            return new SendMessage(chatId,investService.convertCurrency(keySearch,convertKeySearch));
        }else {
            String stock = mes.getText();
            return new SendMessage(chatId,investService.convertStock(stock, convertKeySearch));
        }
    }
}
