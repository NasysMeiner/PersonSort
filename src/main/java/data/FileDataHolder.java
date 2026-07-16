package data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;
import model.Person;

public class FileDataHolder implements DataHolder {
    private final String filePath;
    private final int MAX = 10000;

    private final Consumer<String> errorView;

    public FileDataHolder(String filePath, Consumer<String> errorView) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }

        this.filePath = filePath;
        this.errorView = errorView;
    }

    public FileDataHolder() {
        this.filePath = "person.txt";
        this.errorView = null;
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
            int numberLine = 0;

            while((line = reader.readLine()) != null && loadedCount < count) {
                numberLine++;

                if(line.trim().isEmpty())
                    continue;

                String[] parseData = line.split(",");

                parseData = Stream.of(parseData).map(String::trim).toArray(String[]::new);

                if(parseData.length != 3) {
                    if(errorView != null)
                        errorView.accept("Number line: " + numberLine + " is empty. format(name, password, mail)");

                    continue;
                }

                try {
                    Person newPerson = new Person.Builder()
                                    .setName(parseData[0])
                                    .setMail(parseData[1])
                                    .setPassword(parseData[2])
                                    .build();

                    persons.add(newPerson);
                    loadedCount++;
                } catch (IllegalStateException ex) {
                    if(errorView != null)
                        errorView.accept("Number line: " + numberLine + " error: " + ex.getMessage());
                }
            }
        } catch(IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return persons.stream().toArray(Person[]::new);
    }
    
}
