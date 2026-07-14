package application;

import java.io.IOException;
import model.Person;

public interface DataPersister {
    String[] getAllStateExists();
    String saveSystem() throws IOException;
    void loadSystem(String fileName) throws IOException, ClassNotFoundException;
    Person[] getData();
    String getAbsolutePath();
    boolean isStateExists();

    String appendResult(String title, Person[] data) throws IOException;
    String appendResult(String title, Person person) throws IOException;
}