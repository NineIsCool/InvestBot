package com.example.investbot.service;

import com.example.investbot.bot.InvestBot;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Log4j2
@Transactional
public class InvestBotService {
    UserService userService;

    public String startBot(Long chatId){
        String text = "Hello";
        userService.registerUser(chatId);
        return text;
    }
}
