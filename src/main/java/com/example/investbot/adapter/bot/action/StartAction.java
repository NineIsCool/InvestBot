package com.example.investbot.adapter.bot.action;

import com.example.investbot.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class StartAction implements BotAction{
     List<String> actions;
     UserService userService;

    @Override
    public BotApiMethod handle(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        userService.registerUser(chatId);
        StringBuilder out = new StringBuilder();
        out.append("Команды:").append("\n");
        for (String action : actions) {
            out.append(action).append("\n");
        }
        return new SendMessage(chatId.toString(), out.toString());
    }

    @Override
    public BotApiMethod callback(Update update) {
        return handle(update);
    }
}
