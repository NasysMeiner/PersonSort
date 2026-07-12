package test.tests;

import application.FileDataPersister;
import model.DataBase;
import model.Person;
import persistence.FileStateLoader;
import persistence.FileStateSaver;
import test.Test;

public class FileDataPersisterTest extends Test {

    @Override
    public void run() {
        try {
            FileStateSaver saver = new FileStateSaver();
            FileStateLoader loader = new FileStateLoader();
            DataBase db = new DataBase();
            FileDataPersister persister = new FileDataPersister(saver, loader, db);

            db.add(new Person.Builder()
                    .setName("TestUser")
                    .setMail("test@mail.ru")
                    .setPassword("password123")
                    .build());

            persister.saveSystem();

            String[] states = persister.getAllStateExists();
            if (states.length == 0) {
                setFail();
                setMessage("Не найдено сохранённых состояний");
                return;
            }

            String lastState = states[states.length - 1];
            persister.loadSystem(lastState);

            Person[] loaded = persister.getData();
            if (loaded.length == 0) {
                setFail();
                setMessage("После загрузки данные пусты");
                return;
            }

            setSuccess();
            setMessage("Сохранение и загрузка работают, загружено записей: " + loaded.length);
        } catch (Exception e) {
            setFail();
            setMessage("Исключение: " + e.getMessage());
        }
    }
}