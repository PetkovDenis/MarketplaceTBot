package ru.ws.marketplace.handler.message;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ws.marketplace.init.command.MapCommandState;
import ru.ws.marketplace.model.TChannel;
import ru.ws.marketplace.service.impl.CRUDChannelServiceImpl;
import ru.ws.marketplace.state.dialog.DialogueContext;
import ru.ws.marketplace.state.dialog.DialogueState;

import java.util.Map;

@Component
public class MessageHandler {

    private final TChannel channel = new TChannel();
    private final CRUDChannelServiceImpl crudChannelService;
    private final CreateMessage createMessage;
    private final MapCommandState commandState;
    private final SendMessage sendMessage = new SendMessage();

    @Autowired
    public MessageHandler(CRUDChannelServiceImpl crudChannelService, CreateMessage createMessage, MapCommandState commandState) {
        this.crudChannelService = crudChannelService;
        this.createMessage = createMessage;
        this.commandState = commandState;
    }

    @SneakyThrows
    public BotApiMethod<?> sortedMessage(Message message, DialogueContext context) {
        BotApiMethod<?> result;
        try {
            if (context.getStatusName() != null && !context.getStatusName().equals("END")) {
                result = dialogWithClient(message, context);
            } else {
                result = getTChannel(message);
            }
        } catch (NullPointerException exception) {
            exception.printStackTrace();
            result = getTChannel(message);
        }
        return result;
    }


    public BotApiMethod<?> getTChannel(Message message) {

        BotApiMethod<?> result;

        try {
            TChannel byName = crudChannelService.findByName(message.getText());
            result = createMessage.getPayment(message.getChatId(), byName);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            result = new SendMessage(message.getChatId().toString(), "Неизвестная команда - " + message.getText());
        }
        return result;
    }

    public SendMessage dialogWithClient(Message message, DialogueContext context) {
        Map<String, DialogueState> commandMap = commandState.getCommandMap();
        DialogueState dialogueState = commandMap.get(context.getStatusName());
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(message.getText());
        SendMessage execute = dialogueState.execute(channel, sendMessage);
        if (context.getStatusName().equals("PRICE")) {
            crudChannelService.add(channel);
        }
        context.nextState();
        return execute;
    }
}
