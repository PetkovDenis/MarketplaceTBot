package ru.ws.marketplace.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.ws.marketplace.bot.TBot;

@Configuration
public class BotConfig {

    final TBot tBot;

    public BotConfig(TBot tBot) {
        this.tBot = tBot;
    }

    @SneakyThrows
    @Bean
    public void botConfigMethod() {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(tBot);
    }
}