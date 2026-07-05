package bootstrap;

import application.FileDataPersister;
import persistence.FileStateSaver;

public class MainInitializer {
     public void initialize() {
          FileStateSaver stateSaver = new FileStateSaver();

          FileDataPersister dataPersister = new FileDataPersister(stateSaver);
     }
}