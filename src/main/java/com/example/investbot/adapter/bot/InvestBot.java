package com.example.investbot.adapter.bot;


import com.example.investbot.adapter.bot.action.BotAction;
import com.example.investbot.adapter.bot.action.FindAction;
import com.example.investbot.adapter.bot.action.StartAction;
import com.example.investbot.adapter.bot.action.currency.ConvertCurrency;
import com.example.investbot.adapter.bot.action.currency.FindCurrency;
import com.example.investbot.adapter.bot.action.stock.FindInstrumentList;
import com.example.investbot.adapter.bot.action.subscribe.AllSubscribesAction;
import com.example.investbot.adapter.bot.action.subscribe.SubscribeItemAction;
import com.example.investbot.service.InvestService;
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
    InvestService investService;
    UserService userService;
    SubscribeService subscribeService;
    Map<String, String> bindingBy = new ConcurrentHashMap<>();
    Map <String, BotAction> actions;

    public InvestBot(@Value("${bot.token}") String botToken,
                     InvestService investService, UserService userService, SubscribeService subscribeService) {
        super(botToken);
        this.investService = investService;
        this.userService = userService;
        this.subscribeService = subscribeService;
        this.actions=Map.of(
                "/start", new StartAction(
                        List.of(
                                "/start - Команды бота",
                                "/find - Поиск инвестиционного инструмента",
                                "/mySubscribes - Просмотр текущих инструментов в подписке"),
                        userService
                ),
                "/mySubscribes", new AllSubscribesAction(subscribeService),
                "/find", new FindAction(investService),
                "/subscribe", new SubscribeItemAction(subscribeService),
                "/findCurrency", new FindCurrency(investService),
                "/convertCurrency", new ConvertCurrency(investService),
                "/findInstrumentList", new FindInstrumentList(investService)
        );
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            String key = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();
            if (actions.containsKey(key)) {
                BotApiMethod msg = actions.get(key).handle(update);
                bindingBy.put(chatId, key);
                sendMessage(msg);
            } else if (bindingBy.containsKey(chatId)) {
                var msg = actions.get(bindingBy.get(chatId)).callback(update);
                //bindingBy.remove(chatId);
                sendMessage(msg);
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
            BotApiMethod msg;
            if (callbackData.equals("/goInstrumentList")){
                msg = actions.get("/findInstrumentList").handle(update);
                bindingBy.remove(chatId);
                bindingBy.put(chatId,"/findInstrumentList");
            }else {
                msg = actions.get(callbackData).handle(update);
                bindingBy.remove(chatId);
                bindingBy.put(chatId,callbackData);
            }
            sendMessage(msg);
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
