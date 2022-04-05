package ru.ws.marketplace.state.button;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class Subscribe implements ButtonState {

    @Override
    public SendMessage execute(SendMessage message) {
        message.setText("Введите ссылку на канал");
        return message;
    }
}
