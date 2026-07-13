package persistence;

import java.io.File;

public interface StateSaver {
    String saveState(byte[] data);
    boolean isStateExists();
    File[] getAllStateExists();
    String getAbsolutePath();
}