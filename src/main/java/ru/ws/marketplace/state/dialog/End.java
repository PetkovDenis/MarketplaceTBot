package ru.ws.marketplace.state.dialog;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.ws.marketplace.model.TChannel;

@Component
public class End implements DialogueState {

    @Override
    public void nextState(DialogueContext context) {
        System.out.println("Next state not found");
    }

    @Override
    public String getStatus() {
        return DialogueStateEnum.END.getStatus();
    }

    @Override
    public BotApiMethod<?> execute(TChannel channel, SendMessage message) {
        message.setText("Воспользуйтесь ссылкой - /start для продолжения работы с ботом");
        return message;
    }
}
