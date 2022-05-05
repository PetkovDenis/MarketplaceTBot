package ru.ws.marketplace.handler.preCheckout;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PreCheckoutPaymentTest {

    private PreCheckoutQuery preCheckoutQuery = new PreCheckoutQuery();

    private PreCheckoutPaymentHandler preCheckoutPaymentHandler = mock(PreCheckoutPaymentHandler.class);

    private String preCheckoutQueryId = "100";
    private String invoicePayload = "text";

    @Test
    public void resultPreCheckout() {
        //Arrange
        preCheckoutQuery.setId(preCheckoutQueryId);
        preCheckoutQuery.setInvoicePayload(invoicePayload);

        //Act
        preCheckoutPaymentHandler.resultPreCheckout(preCheckoutQuery);

        ArgumentCaptor<PreCheckoutQuery> checkoutQueryArgumentCaptor = ArgumentCaptor.forClass(PreCheckoutQuery.class);

        verify(preCheckoutPaymentHandler).resultPreCheckout(checkoutQueryArgumentCaptor.capture());

        PreCheckoutQuery value = checkoutQueryArgumentCaptor.getValue();
        //Assert
        verify(preCheckoutPaymentHandler,times(1)).resultPreCheckout(any());

        assertThat(value.getInvoicePayload()).isEqualTo(invoicePayload);
        assertThat(value.getId()).isEqualTo(preCheckoutQueryId);
    }
}
