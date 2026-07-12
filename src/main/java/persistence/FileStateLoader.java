package persistence;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileStateLoader implements StateLoader {
    private final File folder;
    private static final String PATH = "states";

    public FileStateLoader() {
        folder = new File(PATH);
    }

    @Override
    public byte[] loadState(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("Имя файла не указано");
        }
        File file = new File(folder, fileName);
        if (!file.exists()) {
            throw new IllegalArgumentException("Файл не найден: " + fileName);
        }
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            System.out.println("Ошибка чтения: " + e.getMessage());
            return new byte[0];
        }
    }
}