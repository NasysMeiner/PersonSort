package data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import model.Person;

public class FileDataHolder implements DataHolder {
    private final String filePath;
    private final int MAX = 10000;

    public FileDataHolder(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }

        this.filePath = filePath;
    }

    public FileDataHolder() {
        this.filePath = "person.txt";
    }

    @Override
    public Person[] getData(int count) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("File not founded: (" + filePath + ")");
        }

        count = MAX;

        List<Person> persons = new ArrayList<>();
        int loadedCount = 0;

        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while((line = reader.readLine()) != null && loadedCount < count) {
                if(line.trim().isEmpty())
                    continue;

                String[] parseData = line.split(",");

                parseData = Stream.of(parseData).map(String::trim).toArray(String[]::new);

                if(parseData.length != 3)
                    continue;

                Person newPerson = new Person.Builder()
                                    .setName(parseData[0])
                                    .setMail(parseData[1])
                                    .setPassword(parseData[2])
                                    .build();

                persons.add(newPerson);
                loadedCount++;
            }
        } catch(IOException ex) {
            throw new RuntimeException(ex.getMessage());
        } catch(IllegalStateException ex) {
            throw new IllegalStateException("Incorrect input file! " + ex.getMessage());
        }

        return persons.stream().toArray(Person[]::new);
    }
    
}
