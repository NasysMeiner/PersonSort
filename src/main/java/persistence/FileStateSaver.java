package persistence;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class FileStateSaver implements StateSaver {

    private final String path = "src/main/java/states";

    @Override
    public void saveState(List<Integer> data) {
        File file = new File(path);

        if(!file.exists())
            file.mkdirs();

        LocalDateTime now = LocalDateTime.now();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy-HH:mm:ss");
        String formatted = now.format(formatter);
        
        System.out.println(formatted+".txt");

    }

    @Override
    public boolean isStateExists() {
        File file = new File(path);

        if(!file.exists())
            return false;

        File[] files = file.listFiles();
        

        Arrays.stream(files).forEach(f -> System.out.println(f.getName()));

        return true;
    }

    @Override
    public String[] getAllStateExists() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
