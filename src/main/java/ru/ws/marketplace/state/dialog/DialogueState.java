package ru.ws.marketplace.state.dialog;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.ws.marketplace.model.TChannel;

public interface DialogueState {

    void nextState(DialogueContext context);

    String getStatus();

    BotApiMethod<?> execute(TChannel channel, SendMessage message);
}
