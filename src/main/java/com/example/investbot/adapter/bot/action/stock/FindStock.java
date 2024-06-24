package com.example.investbot.adapter.bot.action.stock;

import com.example.investbot.adapter.bot.action.BotAction;
import com.example.investbot.adapter.dto.stock.StockShortRequest;
import com.example.investbot.service.InvestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FindStock implements BotAction {
    ConcurrentHashMap<Long, List<StockShortRequest>> foundStocks;
    InvestService investService;
    @Override
    public BotApiMethod handle(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        String index = msg.getText();
        return getStock(Integer.parseInt(index),chatId);
    }

    @Override
    public BotApiMethod callback(Update update) {
        return handle(update);
    }
    private SendMessage getStock(int index, Long chatId){
        List<StockShortRequest> stocks = foundStocks.get(chatId);
        if (stocks.size()<=index-1||index-1<0){
            return new SendMessage(chatId.toString(),"Данный индекс не найден");
        }
        String text = investService.getStockByUID(stocks.get(index-1).uid());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        return sendMessage;
    }
}
