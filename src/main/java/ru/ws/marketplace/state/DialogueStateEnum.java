package ru.ws.marketplace.state;

public enum DialogueStateEnum {
    NAME("NAME"),
    DESCRIPTION("DESCRIPTION"),
    LINK("LINK"),
    PRICE("PRICE"),
    END("END");

    String status;

    DialogueStateEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
