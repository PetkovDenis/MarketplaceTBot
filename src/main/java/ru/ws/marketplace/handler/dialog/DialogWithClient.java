package ru.ws.marketplace.handler.dialog;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ws.marketplace.init.command.MapCommandState;
import ru.ws.marketplace.model.TChannel;
import ru.ws.marketplace.service.impl.CRUDChannelServiceImpl;
import ru.ws.marketplace.state.dialog.DialogueContext;
import ru.ws.marketplace.state.dialog.DialogueState;

import java.util.Map;

@Component
@AllArgsConstructor
public class DialogWithClient {

    private final MapCommandState commandState;
    private final TChannel channel = new TChannel();
    private final SendMessage sendMessage = new SendMessage();

    public SendMessage dialog(Message message, DialogueContext context, CRUDChannelServiceImpl crudChannelService) {
        Map<String, DialogueState> commandMap = commandState.getCommandMap();
        DialogueState dialogueState = commandMap.get(context.getStatusName());
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(message.getText());
        SendMessage execute = dialogueState.execute(channel, sendMessage);
        if (context.getStatusName().equals("PRICE")) {
            channel.setChatId(message.getChatId());
            crudChannelService.add(channel);
        }
        context.nextState();
        return execute;
    }
}
