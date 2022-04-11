package ru.ws.marketplace.handler.message;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ws.marketplace.bot.TBot;
import ru.ws.marketplace.handler.update.UpdateHandler;
import ru.ws.marketplace.service.impl.CRUDChannelServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReleaseTBotTest {

    private final UpdateHandler handleIncomingMessageService = mock(UpdateHandler.class);
    private final CRUDChannelServiceImpl crudChannelService = mock(CRUDChannelServiceImpl.class);

    private static final Long chatId = 3L;
    private static final String nameChannel = "ChannelS";
    private static final Integer replyMessageId = 43;

    TBot tBot = Mockito.mock(TBot.class);


    // Тест направлен на команду /start
    @Test
    public void testCommandStart() {
        //Arrange
        Update update = createdUpdate();
        //Act
        BotApiMethod<?> botApiMethod = handleIncomingMessageService.handleUpdate(update);
        //Assert
    }

    private Message mockMessage() {
        Message message = mock(Message.class);

        when(message.getChatId()).thenReturn(chatId);
        when(message.getText()).thenReturn(nameChannel);
        when(message.getMessageId()).thenReturn(replyMessageId);

        return message;
    }

    private Update createdUpdate() {
        Update update = mock(Update.class);
        update.setMessage(mockMessage());

        return update;
    }
}