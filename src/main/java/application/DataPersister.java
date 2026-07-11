package application;

import model.Person;

public interface DataPersister {
    String[] getAllStateExists();
    void saveSystem();
    void loadSystem(String fileName);
    Person[] getData();
}