package application;

import java.io.IOException;
import model.Person;

public interface DataPersister {
    String[] getAllStateExists();
    String saveSystem();
    void loadSystem(String fileName);
    Person[] getData();
    String getAbsolutePath();
    boolean isStateExists();

    void appendResult(String title, Person[] data) throws IOException;
    void appendResult(String title, Person person) throws IOException;
}