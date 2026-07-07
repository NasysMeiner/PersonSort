package persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class FileStateSaver implements StateSaver {
    private final File folder;

    private final String path = "src/main/java/states";

    public FileStateSaver() {
        folder = new File(path);
    }

    @Override
    public boolean saveState(List<Integer> data) {
        if(!folder.exists()) {
            if(!folder.mkdirs()) {
                System.out.println("Error create directory!");
                return false;
            }
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH-mm-ss");
        String fileName = "/" + now.format(formatter) + ".txt";
        
        File file = new File(folder, fileName);

        try {
            file.createNewFile();

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(data.toString());
            }
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean isStateExists() {
        if(!folder.exists())
            return false;

        File[] files = getAllStateExists();
        
        if(files.length == 0)
            return false;

        Arrays.stream(files).forEach(f -> System.out.println(f.getName()));

        return true;
    }

    @Override
    public File[] getAllStateExists() {
        if(!isStateExists())
            return null;

        return folder.listFiles();
    }
    
}
