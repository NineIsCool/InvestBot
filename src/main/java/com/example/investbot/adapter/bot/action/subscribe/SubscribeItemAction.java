package com.example.investbot.adapter.bot.action.subscribe;

import com.example.investbot.adapter.bot.action.BotAction;
import com.example.investbot.service.SubscribeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SubscribeItemAction implements BotAction {
    SubscribeService subscribeService;

    @Override
    public BotApiMethod handle(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        Message mes = (Message) update.getCallbackQuery().getMessage();
        String[] text = mes.getText().split("[()]");
        String name = text[0];
        String keySearch = text[1];
        String type = keySearch.length() == 3 ? "currency" : "stock";
        subscribeService.subscribeItem(chatId, type, keySearch, name);
        return new SendMessage(chatId.toString(), "Вы успешно подписались на обновления этой валюты!");
    }

    @Override
    public BotApiMethod callback(Update update) {
        return handle(update);
    }
}
