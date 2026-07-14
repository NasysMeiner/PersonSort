package application;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import model.DataBase;
import model.Person;
import persistence.StateLoader;
import persistence.StateSaver;

public class FileDataPersister implements DataPersister {
    private final StateSaver saver;
    private final StateLoader loader;
    private final DataBase db;
    private static final String RESULTS_FILE = "results.log";
    private static final DateTimeFormatter TS_FMT =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

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
    public String saveSystem() {
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
            System.out.println("Ошибка сериализации: " + e.getMessage());
            return "";
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

    @Override
    public String getAbsolutePath() {
        return saver.getAbsolutePath();
    }

    @Override
    public boolean isStateExists() {
        return saver.isStateExists();
    }

    @Override
    public String appendResult(String title, Person[] data) throws IOException {
        if (data == null || data.length == 0) {
            return "";
        }

        String timestamp = LocalDateTime.now().format(TS_FMT);
        String block = Stream.concat(
                Stream.of("=== " + title + " (" + timestamp + ") ==="),
                Arrays.stream(data).map(Person::toString)
        ).collect(Collectors.joining("\n")) + "\n\n";

        Path path = Paths.get(RESULTS_FILE);
        Files.write(path, block.getBytes(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        return path.toFile().getCanonicalPath();
    }

    @Override
    public String appendResult(String title, Person person) throws IOException {
        if (person == null) {
            return "";
        }

        String timestamp = LocalDateTime.now().format(TS_FMT);
        String block = "=== " + title + " (" + timestamp + ") ===\n"
                + person.toString() + "\n\n";

        Path path = Paths.get(RESULTS_FILE);
        Files.write(path, block.getBytes(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        return path.toFile().getCanonicalPath();
    }
}