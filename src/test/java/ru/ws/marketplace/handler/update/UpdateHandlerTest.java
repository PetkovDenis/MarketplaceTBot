package ru.ws.marketplace.handler.update;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ws.marketplace.handler.button.ButtonClickHandler;
import ru.ws.marketplace.handler.message.MessageHandler;
import ru.ws.marketplace.handler.preCheckoutPayment.PreCheckoutPaymentHandler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateHandlerTest {

    private CallbackQuery callbackQuery = new CallbackQuery();
    private Message message = new Message();
    private Update update = new Update();
    private BotApiMethod<?> botApiMethod;

    private ButtonClickHandler buttonClickHandler = mock(ButtonClickHandler.class);
    private MessageHandler messageHandler = mock(MessageHandler.class);
    private PreCheckoutPaymentHandler preCheckoutPayment = mock(PreCheckoutPaymentHandler.class);
    private UpdateHandler updateHandler = new UpdateHandler(messageHandler, buttonClickHandler, preCheckoutPayment);


    private String callbackId = "testID";
    private String callbackData = "testData";

    private String messageText = "testText";
    private Integer messageId = 100;

    @Test
    void handleButtonClick() {

        // Arrange
        callbackQuery.setId(callbackId);
        callbackQuery.setData(callbackData);

        update.setCallbackQuery(callbackQuery);

        // Act
        botApiMethod = updateHandler.handleUpdate(update);

        ArgumentCaptor<CallbackQuery> callbackArgumentCaptor = ArgumentCaptor.forClass(CallbackQuery.class);

        verify(buttonClickHandler).handleCallback(callbackArgumentCaptor.capture(), any());

        CallbackQuery value = callbackArgumentCaptor.getValue();

        // Assert
        verify(buttonClickHandler, times(1)).handleCallback(any(), any());

        assertThat(value.getId()).isEqualTo(callbackId);
        assertThat(value.getData()).isEqualTo(callbackData);
    }

    @Test
    public void handleMessage() {

        //Arrange
        message.setMessageId(messageId);
        message.setText(messageText);

        update.setMessage(message);

        //Act
        botApiMethod = updateHandler.handleUpdate(update);

        ArgumentCaptor<Message> messageArgumentCaptor = ArgumentCaptor.forClass(Message.class);

        verify(messageHandler).sortedMessage(messageArgumentCaptor.capture(), any());

        Message value = messageArgumentCaptor.getValue();

        //Assert
        verify(messageHandler, times(1)).sortedMessage(any(), any());

        assertThat(value.getMessageId()).isEqualTo(messageId);
        assertThat(value.getText()).isEqualTo(messageText);
    }
}