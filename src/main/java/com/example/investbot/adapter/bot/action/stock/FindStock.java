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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FindStock implements BotAction {
    List<StockShortRequest> foundStocks;
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
        if (foundStocks.size()<=index-1||index-1<0){
            return new SendMessage(chatId.toString(),"Данный индекс не найден");
        }
        String text = investService.getStockByUID(foundStocks.get(index-1).uid());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsButtons = new ArrayList<>();
        List<InlineKeyboardButton> rowButtons = new ArrayList<>();
        InlineKeyboardButton subscribeButton = new InlineKeyboardButton();
        subscribeButton.setText("Подписаться");
        subscribeButton.setCallbackData("/subscribe");
        InlineKeyboardButton convertButton = new InlineKeyboardButton();
        convertButton.setText("Конвертировать");
        convertButton.setCallbackData("/convertStock");
        rowButtons.add(subscribeButton);
        rowButtons.add(convertButton);
        rowsButtons.add(rowButtons);
        keyboardMarkup.setKeyboard(rowsButtons);
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }
}
