package persistence;

import java.io.File;

public interface StateSaver {
    boolean saveState(byte[] data);
    boolean isStateExists();
    File[] getAllStateExists();
}