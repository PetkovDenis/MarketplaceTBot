package ru.ws.marketplace;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarketplaceWsApplication {

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(MarketplaceWsApplication.class, args);
    }
}
