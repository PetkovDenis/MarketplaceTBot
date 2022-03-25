package ru.ws.marketplace.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.ws.marketplace.bot.TBot;

@Configuration
public class BotConfig {

    @SneakyThrows
    @Bean
    public void botConfigMethod() {
        TBot bot = new TBot();
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(bot);
    }
}
// архитектура spring приложения(почитать)
//mapstract