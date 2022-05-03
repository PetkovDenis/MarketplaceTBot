package ru.ws.marketplace.handler.preCheckoutPayment;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import ru.ws.marketplace.handler.date.DateHandler;
import ru.ws.marketplace.model.TChannel;
import ru.ws.marketplace.model.TUser;
import ru.ws.marketplace.service.impl.CRUDChannelServiceImpl;
import ru.ws.marketplace.service.impl.CRUDUserServiceImpl;

@Component
public class PreCheckoutQueryHandler {

    private final CRUDChannelServiceImpl crudChannelService;
    private final CRUDUserServiceImpl crudUserService;
    private final DateHandler dateHandler;
    private final PreCheckoutPaymentHandler preCheckoutPaymentHandler;

    public PreCheckoutQueryHandler(CRUDChannelServiceImpl crudChannelService, CRUDUserServiceImpl crudUserService, DateHandler dateHandler, PreCheckoutPaymentHandler preCheckoutPaymentHandler) {
        this.crudChannelService = crudChannelService;
        this.crudUserService = crudUserService;
        this.dateHandler = dateHandler;
        this.preCheckoutPaymentHandler = preCheckoutPaymentHandler;
    }

    public Boolean handlePreCheckoutQuery(PreCheckoutQuery preCheckoutQuery) {
        User user = preCheckoutQuery.getFrom();
        Long id = user.getId();
        TUser byTelegramId = crudUserService.getByChatId(id);
        fillingUserData(byTelegramId, preCheckoutQuery);
        return preCheckoutPaymentHandler.resultPreCheckout(preCheckoutQuery);
    }

    public void fillingUserData(TUser tUser, PreCheckoutQuery preCheckoutQuery) {
        TChannel tChannel = crudChannelService.get(Long.valueOf(preCheckoutQuery.getInvoicePayload()));
        tUser.setLink(tChannel.getLink());
        tUser.setStartDate(dateHandler.getStartDate());
        tUser.setEndDate(dateHandler.getEndDate());
        tUser.setPayment(preCheckoutQuery.getTotalAmount() / 100); // сумма оплаты
        tUser.setChannelId(Integer.valueOf(preCheckoutQuery.getInvoicePayload())); // id чата
        crudUserService.add(tUser);
    }
}
