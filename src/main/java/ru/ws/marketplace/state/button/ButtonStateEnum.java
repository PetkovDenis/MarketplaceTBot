package ru.ws.marketplace.state.button;

public enum ButtonStateEnum {
    BUTTON_SUBSCRIBE("buttonSubscribe"),
    BUTTON_ADD_RESOURCE("buttonAddResources");

    String status;

    ButtonStateEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
