package ru.ws.marketplace.init.button;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.ws.marketplace.dto.TChannelDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class ButtonInitialization {

    public SendMessage getMainMessage(long chatId, String textMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard();
        return createMessageWithKeyboard(chatId, textMessage, replyKeyboardMarkup);
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


    public SendMessage convertDTOInButton(long chatId, List<TChannelDTO> dtoChannels) {

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        keyboardMarkup.setSelective(true);
        keyboardMarkup.setOneTimeKeyboard(true);
        keyboardMarkup.setResizeKeyboard(true);

        List<KeyboardRow> keyboardMarkups = new ArrayList<>();

        for (TChannelDTO dtotChannel : dtoChannels) {
            KeyboardRow row1 = new KeyboardRow();
            row1.add(new KeyboardButton(dtotChannel.getName()));
            keyboardMarkups.add(row1);
        }
        keyboardMarkup.setKeyboard(keyboardMarkups);

        return createMessageWithKeyboard(chatId, "Выберите один из каналов", keyboardMarkup);
    }


    public ReplyKeyboardMarkup getMainMenuKeyboard() {

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        keyboardMarkup.setSelective(true);
        keyboardMarkup.setOneTimeKeyboard(true);
        keyboardMarkup.setResizeKeyboard(true);

        List<KeyboardRow> keyboardMarkups = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();

        row1.add(new KeyboardButton("Получить отчет"));
        keyboardMarkups.add(row1);

        keyboardMarkup.setKeyboard(keyboardMarkups);

        return keyboardMarkup;
    }

    @Bean
    public InlineKeyboardMarkup getDefaultKeyboard() {

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonAddResources = new InlineKeyboardButton();
        buttonAddResources.setText("Добавить канал в список платных ресурсов");
        buttonAddResources.setCallbackData("buttonAddResources");

        InlineKeyboardButton buttonSubscribe = new InlineKeyboardButton();
        buttonSubscribe.setText("Оформить подписку на ресурс");
        buttonSubscribe.setCallbackData("buttonSubscribe");

        List<InlineKeyboardButton> keyboardButtons1 = new ArrayList<>();
        keyboardButtons1.add(buttonSubscribe);

        List<InlineKeyboardButton> keyboardButtons2 = new ArrayList<>();
        keyboardButtons2.add(buttonAddResources);

        List<List<InlineKeyboardButton>> listsKeyboards = new ArrayList<>();
        listsKeyboards.add(keyboardButtons1);
        listsKeyboards.add(keyboardButtons2);

        keyboard.setKeyboard(listsKeyboards);

        return keyboard;
    }

    @Bean
    public InlineKeyboardMarkup getAdminKeyboard() {

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonReport = new InlineKeyboardButton();
        buttonReport.setText("Получить отчет");
        buttonReport.setCallbackData("buttonReport");

        List<InlineKeyboardButton> keyboardButton1 = new ArrayList<>();
        keyboardButton1.add(buttonReport);

        List<List<InlineKeyboardButton>> listsKeyboards = new ArrayList<>();
        listsKeyboards.add(keyboardButton1);

        keyboard.setKeyboard(listsKeyboards);

        return keyboard;
    }

    public ReplyKeyboardMarkup getAllCategories() {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setResizeKeyboard(true);

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();

        keyboardRow1.add(new KeyboardButton("Спорт \uD83E\uDD3C\u200D♂"));
        keyboardRow1.add(new KeyboardButton("Кулинария \uD83C\uDF54"));
        keyboardRow2.add(new KeyboardButton("Прогнозы на спорт \uD83D\uDCC8⛹"));
        keyboardRow2.add(new KeyboardButton("Технологии ⌚"));

        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }
}