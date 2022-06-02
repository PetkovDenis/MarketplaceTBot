package ru.ws.marketplace.handler.message;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ws.marketplace.handler.dialog.DialogWithClient;
import ru.ws.marketplace.model.TAdmin;
import ru.ws.marketplace.model.TChannel;
import ru.ws.marketplace.service.file.CreateExcelFileHandler;
import ru.ws.marketplace.service.impl.CRUDAdminServiceImpl;
import ru.ws.marketplace.service.impl.CRUDChannelServiceImpl;
import ru.ws.marketplace.state.dialog.DialogueContext;

@Component
@AllArgsConstructor
public class MessageHandler {

    private final CRUDChannelServiceImpl crudChannelService;
    private final CreateMessageWithPayment createMessage;
    private final CreateExcelFileHandler createExcelFileHandler;
    private final CRUDAdminServiceImpl crudAdminService;
    private final DialogWithClient dialogWithClient;

    @SneakyThrows
    public BotApiMethod<?> sortedMessage(Message message, DialogueContext dialogueContext) {

        BotApiMethod<?> result;

        try {
            if (dialogueContext.getStatusName() != null && !dialogueContext.getStatusName().equals("END")) {
                result = dialogWithClient.dialog(message, dialogueContext, crudChannelService);
            } else {
                result = getTChannel(message);
            }
        } catch (NullPointerException exception) {
            result = getTChannel(message);
        }
        return result;
    }


    public BotApiMethod<?> getTChannel(Message message) {

        BotApiMethod<?> result;

        try {
            TChannel byName = crudChannelService.findByName(message.getText());
            result = createMessage.getPayment(message, byName);
        } catch (NullPointerException ex) {
            result = new SendMessage(message.getChatId().toString(), "Неизвестная команда - " + message.getText());
        }
        return result;
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
            readyExcelList = createExcelFileHandler.sendFileToUser(Math.toIntExact(channel.getId()), message);
        } else {
            readyExcelList = null;
        }
        return readyExcelList;
    }
}
