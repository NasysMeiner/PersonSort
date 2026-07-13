package application;

import input.UserInput;
import model.DataBase;
import model.Person;
import ui.View;

public class ManualPersonInputService implements PersonInputService {
    private final UserInput userInput;
    private final View view;
    private final DataBase dataBase;

    public ManualPersonInputService(UserInput userInput, View view, DataBase dataBase) {
        this.userInput = userInput;
        this.view = view;
        this.dataBase = dataBase;
    }

    @Override
    public void fill() {
        System.out.println("Manual data entry for Person");

        while (true) {
            view.showMessage("Enter your name (blank to exit): ");
            String name = userInput.getInput();

            if (name.isEmpty()) {
                break;
            }

            view.showMessage("Enter email: ");
            String mail = userInput.getInput();

            if (mail.isEmpty()) {
                view.showMessage("Email cannot be empty. Please re-enter.");
                continue;
            }

            view.showMessage("Enter your password: ");
            String password = userInput.getInput();

            if (password.isEmpty()) {
                view.showMessage("Password cannot be empty. Please re-enter.");
                continue;
            }

            Person person = new Person.Builder()
                    .setName(name)
                    .setMail(mail)
                    .setPassword(password)
                    .build();

            dataBase.add(person);

            view.showMessage("Added: " + person);

            if (!confirmContinue()) {
                break;
            }
        }
    }

    private boolean confirmContinue() {
        while (true) {
            view.showMessage("Add another entry? (y/n): ");
            String answer = userInput.getInput();

            if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) {
                return true;
            }

            if (answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("no") || answer.isEmpty()) {
                return false;
            }

            view.showMessage("Enter 'y' to continue and 'n' to exit: ");
        }
    }
}
