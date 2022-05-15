package ru.ws.marketplace.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class TChannelDTO {
    private Long id;
    private String category;
    private String name;
    private String description;
    private String link;
    private Integer price;
}
