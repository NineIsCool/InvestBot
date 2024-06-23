package com.example.investbot.adapter.bot.action;

import com.example.investbot.adapter.bot.action.currency.FindCurrency;
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
public class FindAction implements BotAction {
    InvestService investService;
    @Override
    public BotApiMethod handle(Update update) {
        Message msg = update.getMessage();
        String chatId = msg.getChatId().toString();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Что вы бы хотели найти?");
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsButtons = new ArrayList<>();
        List<InlineKeyboardButton> rowButtons = new ArrayList<>();
        InlineKeyboardButton currencyButton = new InlineKeyboardButton();
        currencyButton.setText("Валюты");
        currencyButton.setCallbackData("/findCurrency");
        InlineKeyboardButton stocksButton = new InlineKeyboardButton();
        stocksButton.setText("Акции");
        stocksButton.setCallbackData("/findStock");
        rowButtons.add(currencyButton);
        rowButtons.add(stocksButton);
        rowsButtons.add(rowButtons);
        keyboardMarkup.setKeyboard(rowsButtons);
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    @Override
    public BotApiMethod callback(Update update) {
        var chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        if (update.getCallbackQuery().getData().equals("/findCurrency")){
            FindCurrency findCurrency = new FindCurrency(investService);
            return findCurrency.handle(update);
        }else {
            System.out.println("dslk");
            return null;
        }
    }
}
