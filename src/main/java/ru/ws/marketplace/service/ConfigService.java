package ru.ws.marketplace.service;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.ws.marketplace.bot.TBot;
import ru.ws.marketplace.service.HandleIncomingMessageService;
import ru.ws.marketplace.service.MainMenuService;
import ru.ws.marketplace.status.DialogueContext;

@Configuration
public class ConfigService {

    @SneakyThrows
    @Bean
    public void start() {
        MainMenuService mainMenuService = new MainMenuService();
        DialogueContext context = new DialogueContext();
        TBot bot = new TBot(mainMenuService, new HandleIncomingMessageService(mainMenuService,context));
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(bot);
    }
}
