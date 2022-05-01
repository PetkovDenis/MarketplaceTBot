package ru.ws.marketplace.bot;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMemberCount;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.payments.SuccessfulPayment;
import ru.ws.marketplace.handler.update.UpdateHandler;
import ru.ws.marketplace.handler.user.UserHandler;
import ru.ws.marketplace.model.TChannel;
import ru.ws.marketplace.service.impl.CRUDChannelServiceImpl;

import java.util.List;

@Component
public class TBot extends TelegramLongPollingBot {

    private final UpdateHandler handleUpdate;
    private final UserHandler userHandler;
    private final CRUDChannelServiceImpl crudChannelService;

    public TBot(UpdateHandler handleIncomingMessageService, UserHandler userHandler, CRUDChannelServiceImpl crudChannelService) {
        this.handleUpdate = handleIncomingMessageService;
        this.userHandler = userHandler;
        this.crudChannelService = crudChannelService;
    }

    @Override
    public String getBotUsername() {
        return "@WSMarketplace_bot";
    }

    @Override
    public String getBotToken() {
        return "5277995877:AAFxEZE5tVi1XTcxIGKIV6N7M-2hKuXfhjE";
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {

        if (update.hasChannelPost()) {
            Message channelPost = update.getChannelPost();
            List<User> newChatMembers = channelPost.getNewChatMembers();
            for(User user : newChatMembers){
                userHandler.createTUser(user);
            }
            execute(new SendMessage(channelPost.getChatId().toString(), " Сообщения принято на обработку"));
        }

        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasSuccessfulPayment()) {
                SuccessfulPayment successfulPayment = message.getSuccessfulPayment();
                TChannel tChannel = crudChannelService.get(Long.valueOf(successfulPayment.getInvoicePayload()));
                SendMessage replyMessage = new SendMessage(message.getChatId().toString(), "Платеж успешно завершен!\n Ссылка на канал:" + tChannel.getLink());
                execute(replyMessage);
            }
            if (message.hasText()) {
                if (message.getText().equals("/start")) {
                    execute(userHandler.handleMessage(message));
                } else {
                    execute(handleUpdate.handleUpdate(update));
                }
            }
        } else {
            execute(handleUpdate.handleUpdate(update));
        }
    }
}
