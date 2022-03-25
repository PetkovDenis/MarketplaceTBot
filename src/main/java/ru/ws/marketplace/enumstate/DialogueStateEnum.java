package ru.ws.marketplace.enumstate;

public enum DialogueStateEnum {
    NAME("NAME"),
    DESCRIPTION("DESCRIPTION"),
    LINK("LINK"),
    END("END");

    String status;

    DialogueStateEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
