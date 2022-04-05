package ru.ws.marketplace.state.dialog;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.ws.marketplace.model.TChannel;

@Component
public class Description implements DialogueState {

    @Override
    public void nextState(DialogueContext context) {
        context.setState(new Link());
    }

    @Override
    public String getStatus() {
        return DialogueStateEnum.DESCRIPTION.getStatus();
    }

    @Override
    public SendMessage execute(TChannel channel, SendMessage message) {
        channel.setDescription(message.getText());
        message.setText("Введите ссылку на канал");
        return message;
    }
}
