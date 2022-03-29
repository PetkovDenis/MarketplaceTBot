package ru.ws.marketplace.service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainMenuService {

    public SendMessage getMainMessage(long chatId, String textMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard();
        SendMessage mainMenuMessage = createMessageWithKeyboard(chatId, textMessage, replyKeyboardMarkup);
        return mainMenuMessage;
    }

    private SendMessage createMessageWithKeyboard(long chatId, String textMessage, ReplyKeyboardMarkup replyKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textMessage);
        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
        return sendMessage;
    }

    public ReplyKeyboardMarkup getMainMenuKeyboard() {

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        keyboardMarkup.setSelective(true);
        keyboardMarkup.setOneTimeKeyboard(true);
        keyboardMarkup.setResizeKeyboard(true);

        List<KeyboardRow> keyboardMarkups = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        row1.add(new KeyboardButton("Инструкция"));
        row2.add(new KeyboardButton("Как оформить подписку"));
        keyboardMarkups.add(row1);
        keyboardMarkups.add(row2);

        keyboardMarkup.setKeyboard(keyboardMarkups);


        return keyboardMarkup;
    }

    @Bean
    public InlineKeyboardMarkup getKeyboard() {

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonAddResources = new InlineKeyboardButton();
        buttonAddResources.setText("Добавить канал в список платных ресурсов");
        buttonAddResources.setCallbackData("buttonAddResources");

        InlineKeyboardButton buttonSubscribe = new InlineKeyboardButton();
        buttonSubscribe.setText("Оформить подписку на ресурс");
        buttonSubscribe.setCallbackData("buttonSubscribe");

        InlineKeyboardButton buttonHelp = new InlineKeyboardButton();
        buttonHelp.setText("Инструкция по эксплуатации бота");
        buttonHelp.setCallbackData("buttonHelp");

        List<InlineKeyboardButton> keyboardButtons1 = new ArrayList<>();
        keyboardButtons1.add(buttonSubscribe);

        List<InlineKeyboardButton> keyboardButtons2 = new ArrayList<>();
        keyboardButtons2.add(buttonHelp);
        keyboardButtons2.add(buttonAddResources);

        List<List<InlineKeyboardButton>> listsKeyboards = new ArrayList<>();
        listsKeyboards.add(keyboardButtons1);
        listsKeyboards.add(keyboardButtons2);

        keyboard.setKeyboard(listsKeyboards);

        return keyboard;
    }
}
