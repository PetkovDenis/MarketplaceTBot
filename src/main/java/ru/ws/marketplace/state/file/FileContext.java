package ru.ws.marketplace.state.file;

import org.springframework.stereotype.Component;

@Component
public class FileContext {

    FileState state;

    public void setState(FileState state) {
        this.state = state;
    }

    public FileState getState() {
        return state;
    }

    public void nextState() {
        state.nextState(this);
    }

    public String getStatusName() {
        return state.getStatus();
    }
}
