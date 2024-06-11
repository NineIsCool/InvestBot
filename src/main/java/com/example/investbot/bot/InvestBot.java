package com.example.investbot.bot;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class InvestBot extends TelegramLongPollingBot {

    public InvestBot(@Value("${bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()){
            return;
        }
        String textMessage = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        switch (textMessage){
            case "":

        }
    }

    @Override
    public String getBotUsername() {
        return "Invest32094_Bot";
    }
}
