package ru.ws.marketplace.state.file;

public enum FileStateEnum {

    START("START"),
    END("END");

    String status;

    FileStateEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
