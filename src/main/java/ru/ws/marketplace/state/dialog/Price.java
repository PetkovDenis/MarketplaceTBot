package ru.ws.marketplace.state.dialog;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.ws.marketplace.init.button.ButtonInitialization;
import ru.ws.marketplace.model.TChannel;

@Component
public class Price implements DialogueState {

    private final ButtonInitialization buttonInitialization = new ButtonInitialization();

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
        message.setText("Канал успешно добавлен в список платных ресурсов! Воспользуйтесь вкладкой для получения информации о пользователях");
        message.setReplyMarkup(buttonInitialization.getAdminKeyboard());
        return message;
    }
}
