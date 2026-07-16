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

            try {
                Person person = new Person.Builder()
                    .setName(name)
                    .setMail(mail)
                    .setPassword(password)
                    .build();

                dataBase.add(person);

                view.showMessage("Added: " + person);
            } catch (IllegalStateException e) {
                view.showMessage("Incorrect input! " + e.getMessage());
            }

            if (!confirmContinue()) {
                break;
            }
        }
    }

    private boolean confirmContinue() {
        while (true) {
            view.showMessage("Add another entry? (1/0): ");
            String answer = userInput.getInput();

            if (answer.equalsIgnoreCase("1")) {
                return true;
            }

            if (answer.equalsIgnoreCase("0") || answer.isEmpty()) {
                return false;
            }

            view.showMessage("Enter '1' to continue and '0' to exit: ");
        }
    }
}
