package test.tests;

import application.FileDataPersister;
import model.DataBase;
import model.Person;
import persistence.FileStateLoader;
import persistence.FileStateSaver;
import test.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AppendResultTest extends Test {

    @Override
    public void run() {
        try {
            Path resultsFile = Paths.get("results.log");
            Files.deleteIfExists(resultsFile);

            FileStateSaver saver = new FileStateSaver();
            FileStateLoader loader = new FileStateLoader();
            DataBase db = new DataBase();
            FileDataPersister persister = new FileDataPersister(saver, loader, db);

            Person[] sortedPersons = new Person[]{
                    new Person.Builder().setName("Anna").setMail("anna@mail.ru").setPassword("123456").build(),
                    new Person.Builder().setName("Igor").setMail("igor@mail.ru").setPassword("654321").build()
            };

            persister.appendResult("Sorted by name", sortedPersons);

            Person found = new Person.Builder().setName("Petr").setMail("petr@mail.ru").setPassword("petrpass").build();
            persister.appendResult("Found by name", found);

            if (!Files.exists(resultsFile)) {
                setFail();
                setMessage("Файл results.log не создан");
                return;
            }

            long linesBefore = Files.lines(resultsFile).count();

            persister.appendResult("Sorted by name", sortedPersons);

            long linesAfter = Files.lines(resultsFile).count();

            if (linesAfter <= linesBefore) {
                setFail();
                setMessage("Файл не увеличился после второго вызова (режим append не работает)");
                return;
            }

            setSuccess();
            setMessage("Запись в режиме добавления работает, файл: " + linesAfter + " строк");
        } catch (IOException e) {
            setFail();
            setMessage("Исключение: " + e.getMessage());
        }
    }
}