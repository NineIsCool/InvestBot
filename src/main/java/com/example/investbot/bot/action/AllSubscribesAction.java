package com.example.investbot.bot.action;

import com.example.investbot.domain.SubscribeEntity;
import com.example.investbot.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AllSubscribesAction implements BotAction{
    UserService userService;
    @Override
    public BotApiMethod handle(Update update) {
        var msg = update.getMessage();
        var chatId = msg.getChatId();
        StringBuilder textBuilder = new StringBuilder();
        List<SubscribeEntity> subscribes = userService.getAllSubscribes(chatId);
        if (subscribes==null || subscribes.isEmpty()){
            textBuilder.append("У вас нет активных подписок");
        }else {
            for (SubscribeEntity sub:subscribes) {
                textBuilder.append(sub.getName()+"\n");
            }
        }
        return new SendMessage(chatId.toString(), textBuilder.toString());
    }

    @Override
    public BotApiMethod callback(Update update) {
        return handle(update);
    }
}
