package persistence;

import java.util.List;

public interface StateSaver {
    void saveState(List<Integer> data);
    boolean isStateExists();
    String[] getAllStateExists();
}