package com.example.investbot.adapter.bot.action.stock;

import com.example.investbot.adapter.bot.action.BotAction;
import com.example.investbot.adapter.dto.stock.StockShortRequest;
import com.example.investbot.service.InvestService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FindInstrumentList implements BotAction {
    final InvestService investService;
    final ConcurrentHashMap<Long, List<StockShortRequest>> foundStockMap = new ConcurrentHashMap<>();
    int page;

    @Override
    public BotApiMethod handle(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        if (foundStockMap.containsKey(chatId)){
            return callback(update);
        }
        String text = "Введите название акции:";
        return new SendMessage(chatId.toString(), text);
    }

    @Override
    public BotApiMethod callback(Update update) {
        if (update.hasMessage()){
            Message msg = update.getMessage();
            Long chatId = msg.getChatId();
            String findKey = msg.getText();
            if (foundStockMap.containsKey(chatId)){
                if (!findKey.matches("\\d+")){
                    return new SendMessage(chatId.toString(),"Данный индекс неверен");
                }
                return new FindStock(foundStockMap,investService).handle(update);
            }else {
                return getListStocks(findKey,chatId);
            }
        }else if (update.hasCallbackQuery()){
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            Message message = (Message) update.getCallbackQuery().getMessage();
            if (update.getCallbackQuery().getData().equals("/goInstrumentList")){
                page+=1;
            }else {
                page-=1;
            }
            Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
            EditMessageText editMessageText = new EditMessageText();
            editMessageText.setChatId(chatId);
            editMessageText.setMessageId(messageId);
            List<StockShortRequest> stocks = foundStockMap.get(chatId);
            editMessageText.setText(stocksToString(stocks));
            editMessageText.setReplyMarkup(addButtons(stocks.size()));
            return editMessageText;
        }
        return null;
    }


    private SendMessage getListStocks(String findKey, Long chatId){
        List<StockShortRequest> foundStock = investService.findInstrument(findKey,"share");
        String text = stocksToString(foundStock);
        foundStockMap.put(chatId,foundStock);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(addButtons(foundStock.size()));
        return sendMessage;
    }

    private InlineKeyboardMarkup addButtons(int listSize){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsButtons = new ArrayList<>();
        List<InlineKeyboardButton> rowButtons = new ArrayList<>();
        if ((page-1)*15>=0){
            InlineKeyboardButton backButton = new InlineKeyboardButton();
            backButton.setText("<-");
            backButton.setCallbackData("/findInstrumentList");
            rowButtons.add(backButton);
        }
        if (15+(page+1)*15<listSize){
            InlineKeyboardButton goButton = new InlineKeyboardButton();
            goButton.setText("->");
            goButton.setCallbackData("/goInstrumentList");
            rowButtons.add(goButton);
        }
        rowsButtons.add(rowButtons);
        keyboardMarkup.setKeyboard(rowsButtons);
        return keyboardMarkup;
    }

    private String stocksToString(List<StockShortRequest> foundStock){
        if (foundStock.isEmpty()){
            return "По вашему запросу ничего не найдено.";
        }
        StringBuilder text = new StringBuilder("По вашему запросу найдено("+(page+1)+" стр.):\n");
        for (int i = page*15; i < 15+page*15; i++) {
            if (i>=foundStock.size()){
                break;
            }
            text.append(String.format("%d. %s(%s)\n",i+1,foundStock.get(i).name(),foundStock.get(i).ticker()));
        }
        return text.toString();
    }


}
