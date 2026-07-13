package persistence;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class FileStateSaver implements StateSaver {
    private final File folder;
    private static final String PATH = "states";

    public FileStateSaver() {
        folder = new File(PATH);
    }

    @Override
    public String saveState(byte[] data) {
        if (data == null || data.length == 0) {
            return "";
        }
        if (!folder.exists() && !folder.mkdirs()) {
            return "";
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy_HH-mm-ss");
        File file = new File(folder, now.format(formatter) + ".dat");

        try {
            return Files.write(file.toPath(), data).toFile().getAbsolutePath();
        } catch (IOException e) {
            System.out.println("Save error: " + e.getMessage());
            return "";
        }
    }

    @Override
    public boolean isStateExists() {
        if (!folder.exists()) {
            return false;
        }
        File[] files = folder.listFiles();
        return files != null && files.length > 0;
    }

    @Override
    public File[] getAllStateExists() {
        if (!isStateExists()) {
            return new File[0];
        }
        File[] files = folder.listFiles();
        if (files == null) {
            return new File[0];
        }
        return Arrays.stream(files)
                .toArray(File[]::new);
    }

    @Override
    public String getAbsolutePath() {
        return folder.getAbsolutePath() + "\\";
    }
}