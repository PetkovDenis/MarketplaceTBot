package ru.ws.marketplace.state.dialog;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.ws.marketplace.model.TChannel;

@Component
public class Price implements DialogueState {

    @Override
    public void nextState(DialogueContext context) {
        context.setState(new End());
    }

    @Override
    public String getStatus() {
        return DialogueStateEnum.PRICE.getStatus();
    }

    @Override
    public SendMessage execute(TChannel channel, SendMessage message) {
        channel.setPrice(Integer.valueOf(message.getText()));
        message.setText("Анкета заполнена! Воспользуйтесь вкладкой - Оформить подписку для просмотра добавленного канала. \n /start - для удобного перехода");
        return message;
    }

}
