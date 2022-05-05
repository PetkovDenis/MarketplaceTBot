package ru.ws.marketplace.handler.preCheckout;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import ru.ws.marketplace.service.impl.CRUDChannelServiceImpl;

@Component
@AllArgsConstructor
public class PreCheckoutPaymentHandler {

    private final CRUDChannelServiceImpl crudChannelService;

    public boolean resultPreCheckout(PreCheckoutQuery preCheckoutQuery) {

        String invoicePayload = preCheckoutQuery.getInvoicePayload();

        return crudChannelService.get(Long.valueOf(invoicePayload)) != null;
    }
}
