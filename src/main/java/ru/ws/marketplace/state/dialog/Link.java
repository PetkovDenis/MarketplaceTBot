package ru.ws.marketplace.state.dialog;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.ws.marketplace.model.TChannel;

@Component
public class Link implements DialogueState {

    @Override
    public void nextState(DialogueContext context) {
        context.setState(new Price());
    }

    @Override
    public String getStatus() {
        return DialogueStateEnum.LINK.getStatus();
    }

    @Override
    public SendMessage execute(TChannel channel, SendMessage message) {
        channel.setLink(message.getText());
        message.setText("Введите цену подписки за месяц");
        return message;
    }
}