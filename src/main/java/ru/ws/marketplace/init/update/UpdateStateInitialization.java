package ru.ws.marketplace.init.update;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.AnswerPreCheckoutQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import ru.ws.marketplace.handler.button.ButtonClickHandler;
import ru.ws.marketplace.handler.dialog.GreetingPerson;
import ru.ws.marketplace.handler.message.MessageHandler;
import ru.ws.marketplace.handler.preCheckout.PreCheckoutQueryHandler;
import ru.ws.marketplace.model.TChannel;
import ru.ws.marketplace.service.impl.CRUDChannelServiceImpl;
import ru.ws.marketplace.service.scheduled.CheckerUsers;
import ru.ws.marketplace.state.dialog.DialogueContext;

import java.util.List;

@Component
public class UpdateStateInitialization {

    private final ButtonClickHandler callbackHandler;
    private final DialogueContext context;
    private final GreetingPerson greetingPerson;
    private final MessageHandler messageHandler;
    private final PreCheckoutQueryHandler preCheckoutQueryHandler;

    private final CRUDChannelServiceImpl crudChannelService;

    private BotApiMethod<?> replyMessage;

    private final CheckerUsers checkerUsers;

    @Value("${bot.token}")
    private String botToken;

    public UpdateStateInitialization(ButtonClickHandler callbackHandler, DialogueContext context, GreetingPerson greetingPerson, MessageHandler messageHandler, PreCheckoutQueryHandler preCheckoutQueryHandler, CRUDChannelServiceImpl crudChannelService, CheckerUsers checkerUsers) {
        this.callbackHandler = callbackHandler;
        this.context = context;
        this.greetingPerson = greetingPerson;
        this.messageHandler = messageHandler;
        this.preCheckoutQueryHandler = preCheckoutQueryHandler;
        this.crudChannelService = crudChannelService;
        this.checkerUsers = checkerUsers;
    }

    public List<UpdateProcessor> executeUpdate(Update update) {
        final List<UpdateProcessor> processing = Lists.newArrayList(
                UpdateProcessor.of(Update::hasCallbackQuery, Update::getCallbackQuery, this::callbackProcess),
                UpdateProcessor.of(Update::hasMessage, Update::getMessage, this::messageProcess),
                UpdateProcessor.of(Update::hasPreCheckoutQuery, Update::getPreCheckoutQuery, this::PreCheckoutQueryProcess),
                UpdateProcessor.of(Update::hasChannelPost, Update::getChannelPost, this::channelPostProcess)
        );
        return processing;
    }

    private BotApiMethod<?> channelPostProcess(BotApiObject botApiObject) {
        Message message = (Message) botApiObject;
        Chat chat = message.getChat();
        String firstName = chat.getTitle();
        Long id = chat.getId();
        TChannel channel = crudChannelService.findByName(firstName);
        channel.setGroupId(id);
        Integer requestOnTelegramAPI = checkerUsers.createRequestOnTelegramAPI(botToken, id.toString(), firstName);
        channel.setCountUsers(requestOnTelegramAPI);
        crudChannelService.add(channel);
        return new SendMessage(id.toString(), "ok"); // TODO:придумать другой результат
    }

    private BotApiMethod<?> callbackProcess(BotApiObject botApiObject) {
        CallbackQuery callbackQuery = (CallbackQuery) botApiObject;
        return callbackHandler.handleCallback(callbackQuery, context);
    }

    private BotApiMethod<?> messageProcess(BotApiObject botApiObject) {
        Message message = (Message) botApiObject;
        if (message.getText().equals("/start")) {
            replyMessage = greetingPerson.greeting(message);
        } else {
            replyMessage = messageHandler.sortedMessage(message, context);
        }
        return replyMessage;
    }

    private BotApiMethod<?> PreCheckoutQueryProcess(BotApiObject botApiObject) {
        PreCheckoutQuery preCheckoutQuery = (PreCheckoutQuery) botApiObject;
        boolean result = preCheckoutQueryHandler.handlePreCheckoutQuery(preCheckoutQuery);
        return replyMessage = new AnswerPreCheckoutQuery(preCheckoutQuery.getId(), result);
    }
}
