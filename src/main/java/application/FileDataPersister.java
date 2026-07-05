package application;

import persistence.StateSaver;

public class FileDataPersister implements DataPersister {
    private final StateSaver saver;

    public FileDataPersister(StateSaver saver) {
        this.saver = saver;
    }

    @Override
    public String[] getAllStateExists() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void saveSystem() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void loadSystem(String fileName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
