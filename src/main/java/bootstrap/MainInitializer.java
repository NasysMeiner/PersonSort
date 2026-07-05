package bootstrap;

import java.util.Arrays;
import persistence.FileStateSaver;

public class MainInitializer {
     public void initialize() {
          FileStateSaver stateSaver = new FileStateSaver();

          stateSaver.saveState(Arrays.asList(1,2,3,4,5,56,76));
          stateSaver.isStateExists();
     }
}