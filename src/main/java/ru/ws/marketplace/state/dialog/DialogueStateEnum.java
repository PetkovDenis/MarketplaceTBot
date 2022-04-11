package ru.ws.marketplace.state.dialog;

public enum DialogueStateEnum {
    NAME("NAME"),
    CATEGORY("CATEGORY"),
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
