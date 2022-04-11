package ru.ws.marketplace.state.dialog;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.ws.marketplace.model.TChannel;

public class Category implements DialogueState{
    @Override
    public void nextState(DialogueContext context) {
    context.setState(new Name());
    }

    @Override
    public String getStatus() {
        return DialogueStateEnum.CATEGORY.getStatus();
    }

    @Override
    public SendMessage execute(TChannel channel, SendMessage message) {
        channel.setCategory(message.getText());
        message.setText("Введите название канала");
        return message;
    }
}
