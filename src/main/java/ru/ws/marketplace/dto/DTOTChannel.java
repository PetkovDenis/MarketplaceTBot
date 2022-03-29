package ru.ws.marketplace.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class DTOTChannel {
    private Long id;
    private String name;
    private String description;
    private String link;
    private Integer price;

    @Override
    public String toString() {
        return  "/"+name +"\n";
    }
}
