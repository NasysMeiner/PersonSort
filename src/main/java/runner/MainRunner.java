package runner;

import application.ManualPersonInputService;
import application.PersonInputService;
import model.DataBase;
import model.Person;

public class MainRunner {
    public void run() {
        DataBase database = new DataBase();
        PersonInputService inputService = new ManualPersonInputService();

        inputService.fill(database);

        System.out.println("\nСписок пользователей в базе:");
        for (Person person : database) {
            System.out.println(person);
        }
    }
}
