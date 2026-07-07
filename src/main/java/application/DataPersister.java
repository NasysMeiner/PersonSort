package application;

public interface DataPersister {
    String[] getAllStateExists();
    void saveSystem();
    void loadSystem(String fileName);
}
