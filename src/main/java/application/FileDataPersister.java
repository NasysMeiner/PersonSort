package application;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.stream.Stream;
import model.DataBase;
import model.Person;
import persistence.StateLoader;
import persistence.StateSaver;

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
    public String saveSystem() throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(db);
            oos.flush();

            String path = saver.saveState(baos.toByteArray());
            if (path.isEmpty()) {
                System.out.println("Не удалось сохранить состояние");
            }

            return path;
        } catch (IOException e) {
            throw new IOException("Error serialized");
        }
    }

    @Override
    public void loadSystem(String fileName) throws IOException, ClassNotFoundException{
        byte[] bytes = loader.loadState(fileName);
        if (bytes == null || bytes.length == 0) {
            throw new IOException("File is empty or does not exist!");
        }
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            DataBase loaded = (DataBase) ois.readObject();
            Stream.of(loaded.getAll())
                .forEach(db::add);
        } catch (IOException e) {
            throw new IOException("Error deserialize: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Incompatible save version!");
        }
    }

    @Override
    public Person[] getData() {
        return db.getAll();
    }

    @Override
    public String getAbsolutePath() {
        return saver.getAbsolutePath();
    }

    @Override
    public boolean isStateExists() {
        return saver.isStateExists();
    }
}