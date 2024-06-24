package com.example.investbot.adapter.bot.action.subscribe;

import com.example.investbot.adapter.bot.action.BotAction;
import com.example.investbot.domain.SubscribeEntity;
import com.example.investbot.service.SubscribeService;
import com.example.investbot.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AllSubscribesAction implements BotAction {
    SubscribeService subscribeService;
    @Override
    public BotApiMethod handle(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        StringBuilder textBuilder = new StringBuilder();
        List<SubscribeEntity> subscribes = subscribeService.getAllSubscribes(chatId);
        if (subscribes==null || subscribes.isEmpty()){
            textBuilder.append("У вас нет активных подписок");
        }else {
            textBuilder.append("Ваши подписки:\n");
            for (int i = 0; i < subscribes.size(); i++) {
                textBuilder.append((i+1)+". "+subscribes.get(i).getName()+"("+subscribes.get(i).getKeySearch()+")"+"\n");
            }
            textBuilder.append("(введите номер инструмента чтобы узнать о нем подробнее)");
        }
        return new SendMessage(chatId.toString(), textBuilder.toString());
    }

    @Override
    public BotApiMethod callback(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        int findIndex = Integer.parseInt(msg.getText());
        return new SendMessage(chatId.toString(),subscribeService.findSubscribeItem(chatId,findIndex));
    }
}
