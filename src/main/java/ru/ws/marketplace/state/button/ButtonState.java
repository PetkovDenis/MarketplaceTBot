package ru.ws.marketplace.state.button;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public interface ButtonState {
    SendMessage execute(SendMessage message);
}
