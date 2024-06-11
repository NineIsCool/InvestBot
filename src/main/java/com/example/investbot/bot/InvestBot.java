package com.example.investbot.bot;


import com.example.investbot.service.InvestBotService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class InvestBot extends TelegramLongPollingBot {
    InvestBotService investBotService;

    public InvestBot(@Value("${bot.token}") String botToken, InvestBotService investBotService) {
        super(botToken);
        this.investBotService = investBotService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()){
            return;
        }
        String textMessage = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        switch (textMessage){
            case "/start":
                sendMessage(chatId, investBotService.startBot(chatId));
                break;
            case "/find":
                //todo
        }
    }

    @Override
    public String getBotUsername() {
        return "Invest32094_Bot";
    }

    public void sendMessage(Long chatId, String text){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
