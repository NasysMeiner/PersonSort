package application;

import model.Person;

public interface DataPersister {
    String[] getAllStateExists();
    String saveSystem();
    void loadSystem(String fileName);
    Person[] getData();
    String getAbsolutePath();
    boolean isStateExists();
}