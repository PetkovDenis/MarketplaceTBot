package ru.ws.marketplace.state.dialog;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.ws.marketplace.model.TChannel;

@Component
public class Name implements DialogueState {

    @Override
    public void nextState(DialogueContext context) {
        context.setState(new Description());
    }

    @Override
    public String getStatus() {
        return DialogueStateEnum.NAME.getStatus();
    }

    @Override
    public SendMessage execute(TChannel channel, SendMessage message) {
        channel.setName(message.getText());
        message.setText("Введите описание канала(цель канала, для кого предназначена информация публикуемая в канале и т.д");
        return message;
    }
}
