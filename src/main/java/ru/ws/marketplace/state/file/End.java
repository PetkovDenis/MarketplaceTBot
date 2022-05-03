package ru.ws.marketplace.state.file;

public class End implements FileState {
    @Override
    public void nextState(FileContext fileContext) {
    }

    @Override
    public String getStatus() {
        return FileStateEnum.END.getStatus();
    }
}
