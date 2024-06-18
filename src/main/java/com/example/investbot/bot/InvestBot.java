package com.example.investbot.bot;


import com.example.investbot.bot.action.AllSubscribesAction;
import com.example.investbot.bot.action.BotAction;
import com.example.investbot.bot.action.StartAction;
import com.example.investbot.service.InvestBotService;
import com.example.investbot.service.SubscribeService;
import com.example.investbot.service.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class InvestBot extends TelegramLongPollingBot {
    InvestBotService investBotService;
    UserService userService;
    SubscribeService subscribeService;
    Map<String, String> bindingBy = new ConcurrentHashMap<>();
    Map <String, BotAction> actions;

    public InvestBot(@Value("${bot.token}") String botToken, InvestBotService investBotService,
                     UserService userService, SubscribeService subscribeService) {
        super(botToken);
        this.investBotService = investBotService;
        this.userService = userService;
        this.subscribeService = subscribeService;
        this.actions=Map.of(
                "/start", new StartAction(
                        List.of(
                                "/start - Команды бота",
                                "/find - Поиск инвестиционного инструмента",
                                "/mySubscribes - Просмотр текущих инструментов в подписке")
                ),
                "/mySubscribes", new AllSubscribesAction(userService)
        );
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            var key = update.getMessage().getText();
            var chatId = update.getMessage().getChatId().toString();
            if (actions.containsKey(key)) {
                var msg = actions.get(key).handle(update);
                bindingBy.put(chatId, key);
                sendMessage(msg);
            } else if (bindingBy.containsKey(chatId)) {
                var msg = actions.get(bindingBy.get(chatId)).callback(update);
                bindingBy.remove(chatId);
                sendMessage(msg);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "Invest32094_Bot";
    }

    private void sendMessage(BotApiMethod msg) {
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
