package application;

import java.io.File;
import java.util.Arrays;
import persistence.StateSaver;

public class FileDataPersister implements DataPersister {
    private final StateSaver saver;

    public FileDataPersister(StateSaver saver) {
        this.saver = saver;
    }

    @Override
    public String[] getAllStateExists() {
        File[] allFiles = saver.getAllStateExists();
        String[] allFileNames = Arrays.stream(allFiles).map(file -> file.getName()).toArray(String[]::new);
        
        return allFileNames;
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
