package ru.ws.marketplace.state.file;

public class Start implements FileState {
    @Override
    public void nextState(FileContext fileContext) {
        fileContext.setState(new End());
    }

    @Override
    public String getStatus() {
        return FileStateEnum.START.getStatus();
    }
}
