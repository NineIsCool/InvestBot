package com.example.investbot.adapter.bot.action.currency;

import com.example.investbot.adapter.bot.action.BotAction;
import com.example.investbot.service.InvestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FindCurrency implements BotAction {
    InvestService investService;
    @Override
    public BotApiMethod handle(Update update) {
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        String text = "Введите трехбуквенный код валюты:";
        return new SendMessage(chatId, text);
    }

    @Override
    public BotApiMethod callback(Update update) {
        var msg = update.getMessage();
        var chatId = msg.getChatId();
        var findKey = msg.getText();
        String currency = investService.findCurrency(findKey);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(currency);
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsButtons = new ArrayList<>();
        List<InlineKeyboardButton> rowButtons = new ArrayList<>();
        InlineKeyboardButton subscribeButton = new InlineKeyboardButton();
        subscribeButton.setText("Подписаться");
        subscribeButton.setCallbackData("/subscribe");
        InlineKeyboardButton convertButton = new InlineKeyboardButton();
        convertButton.setText("Конвертировать");
        convertButton.setCallbackData("/convertCurrency");
        rowButtons.add(subscribeButton);
        rowButtons.add(convertButton);
        rowsButtons.add(rowButtons);
        keyboardMarkup.setKeyboard(rowsButtons);
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }
}
