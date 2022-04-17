package ru.ws.marketplace.handler.bot;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ws.marketplace.bot.TBot;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TBotTest {

    private Update update = new Update();
    private Message message = new Message();

    private static final Long chatId = 3L;
    private static final Integer messageId = 100;

    private String token = "token";

    TBot tBot = mock(TBot.class);


    @Test
    public void updateWithMessage() {
        //Arrange
        message.setMessageId(messageId);

        update.setMessage(message);
        //Act
        tBot.onUpdateReceived(update);

        ArgumentCaptor<Update> updateCaptor = ArgumentCaptor.forClass(Update.class);

        verify(tBot, times(1)).onUpdateReceived(updateCaptor.capture());

        Update updateResult = updateCaptor.getValue();
        //Assert
        assertThat(updateResult.getMessage()).isEqualTo(message);
    }
}