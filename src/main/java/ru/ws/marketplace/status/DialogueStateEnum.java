package ru.ws.marketplace.status;

public enum DialogueStateEnum {
    NAME("NAME"),
    DESCRIPTION("DESCRIPTION"),
    LINK("LINK");

    String status;

    DialogueStateEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
