package ru.ws.marketplace.state.file;

public interface FileState {

    void nextState(FileContext fileContext);

    String getStatus();
}
