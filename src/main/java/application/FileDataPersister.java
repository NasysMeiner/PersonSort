package application;

import model.DataBase;
import model.Person;
import persistence.StateLoader;
import persistence.StateSaver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class FileDataPersister implements DataPersister {
    private final StateSaver saver;
    private final StateLoader loader;
    private final DataBase db;

    public FileDataPersister(StateSaver saver, StateLoader loader, DataBase db) {
        this.saver = saver;
        this.loader = loader;
        this.db = db;
    }

    @Override
    public String[] getAllStateExists() {
        return Arrays.stream(saver.getAllStateExists())
                .map(file -> file.getName())
                .toArray(String[]::new);
    }

    @Override
    public void saveSystem() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(db);
            oos.flush();
            boolean ok = saver.saveState(baos.toByteArray());
            if (!ok) {
                System.out.println("Не удалось сохранить состояние");
            }
        } catch (IOException e) {
            System.out.println("Ошибка сериализации: " + e.getMessage());
        }
    }

    @Override
    public void loadSystem(String fileName) {
        byte[] bytes = loader.loadState(fileName);
        if (bytes == null || bytes.length == 0) {
            System.out.println("Файл пуст или не существует");
            return;
        }
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            DataBase loaded = (DataBase) ois.readObject();
            for (Person p : loaded) {
                db.add(p);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка десериализации: " + e.getMessage());
        }
    }

    @Override
    public Person[] getData() {
        return db.getAll();
    }
}