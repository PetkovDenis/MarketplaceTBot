package ru.ws.marketplace.handler.message;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ws.marketplace.init.command.MapCommandState;
import ru.ws.marketplace.model.TAdmin;
import ru.ws.marketplace.model.TChannel;
import ru.ws.marketplace.service.file.CreateExcelFileHandler;
import ru.ws.marketplace.service.impl.CRUDAdminServiceImpl;
import ru.ws.marketplace.service.impl.CRUDChannelServiceImpl;
import ru.ws.marketplace.state.dialog.DialogueContext;
import ru.ws.marketplace.state.dialog.DialogueState;

import java.util.Map;

@Component
@AllArgsConstructor
public class MessageHandler {

    private final TChannel channel = new TChannel();
    private final CRUDChannelServiceImpl crudChannelService;
    private final CreateMessageWithPayment createMessage;
    private final MapCommandState commandState;
    private final SendMessage sendMessage = new SendMessage();
    private final CreateExcelFileHandler createExcelFileHandler;
    private final CRUDAdminServiceImpl crudAdminService;

    @SneakyThrows
    public BotApiMethod<?> sortedMessage(Message message, DialogueContext dialogueContext) {

        BotApiMethod<?> result;


        try {
            if (dialogueContext.getStatusName() != null && !dialogueContext.getStatusName().equals("END")) {
                result = dialogWithClient(message, dialogueContext);
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
            channel.setChatId(message.getChatId());
            crudChannelService.add(channel);
        }
        context.nextState();
        return execute;
    }

    public SendDocument createFile(Message message) {

        Chat chat = message.getChat();
        Long id = chat.getId();
        TChannel channel = crudChannelService.getByChatId(id);

        SendDocument readyExcelList;
        if (channel.getName() != null) {
            TAdmin admin = crudAdminService.getByChatId(message.getChatId());
            admin.setChannelId(Math.toIntExact(channel.getId()));
            admin.setChannelName(channel.getName());
            crudAdminService.add(admin);
            readyExcelList = createExcelFileHandler.getReadyExcelList(Math.toIntExact(channel.getId()), message);

        } else {
            readyExcelList = null;
        }
        return readyExcelList;
    }
}
