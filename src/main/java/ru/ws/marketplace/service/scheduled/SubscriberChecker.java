package ru.ws.marketplace.service.scheduled;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ws.marketplace.bot.TBot;

@Service
@EnableScheduling
public class SubscriberChecker {


    private TBot tBot;

    @Scheduled(cron = "0 0 10 1 * ?")
    public void checkSubscribers() throws TelegramApiException {
        // под вопросом
        tBot.execute(new SendMessage("1", "You blocked"));
    }
}
