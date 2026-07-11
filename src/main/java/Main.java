import input.ConsoleUserInput;
import input.UserInput;
import model.Person;
import sorter.EmailSorter;
import sorter.NameSorter;
import sorter.PasswordSorter;
import sorter.UserSorter;
import view.ConsoleView;
import view.View;

public class Main {

    public static void main(String[] args) {
        View view = new ConsoleView();
        UserInput input = new ConsoleUserInput();

        Person[] people = {
                new Person("Ivan", "qwerty", "ivan@mail.com"),
        new Person("Anna", "abc123", "anna@mail.com"),
                new Person("Petr", "zxc999", "petr@mail.com")
    };

        view.showMessage("Before sorting:");
        view.showData(people);

        view.showMenu();
        String command = input.getInput();

        UserSorter sorter = null;

        switch (command) {
            case "1":
                sorter = new NameSorter();
                break;
            case "2":
                sorter = new PasswordSorter();
                break;
            case "3":
                sorter = new EmailSorter();
                break;
            default:
                view.showMessage("Unknown command.");
        }

        if (sorter != null) {
            sorter.sort(people);
            view.showMessage("After sorting:");
            view.showData(people);
        }
    }
}