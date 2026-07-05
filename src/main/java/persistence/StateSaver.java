package persistence;

import java.io.File;
import java.util.List;

public interface StateSaver {
    boolean saveState(List<Integer> data);
    boolean isStateExists();
    File[] getAllStateExists();
}