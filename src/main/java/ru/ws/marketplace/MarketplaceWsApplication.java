package ru.ws.marketplace;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.ws.marketplace.bot.TBot;
import ru.ws.marketplace.service.HandleIncomingMessageService;
import ru.ws.marketplace.service.MainMenuService;

@SpringBootApplication
public class MarketplaceWsApplication {

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(MarketplaceWsApplication.class, args);
        MainMenuService mainMenuService = new MainMenuService();
        TBot bot = new TBot(mainMenuService,
                            new HandleIncomingMessageService(mainMenuService));
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(bot);
    }

}
