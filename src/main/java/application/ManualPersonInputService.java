package application;

import java.util.Scanner;
import model.DataBase;
import model.Person;

public class ManualPersonInputService implements PersonInputService {

    @Override
    public void fill(DataBase database) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ручной ввод данных для Person.");
        while (true) {
            String name = readField(scanner, "Введите имя (пусто для выхода): ");
            if (name.isEmpty()) {
                break;
            }

            String mail = readField(scanner, "Введите email: ");
            if (mail.isEmpty()) {
                System.out.println("Email не может быть пустым. Повторите ввод.");
                continue;
            }

            String password = readField(scanner, "Введите пароль: ");
            if (password.isEmpty()) {
                System.out.println("Пароль не может быть пустым. Повторите ввод.");
                continue;
            }

            Person person = new Person.Builder()
                    .setName(name)
                    .setMail(mail)
                    .setPassword(password)
                    .build();

            database.add(person);
            System.out.println("Добавлен: " + person);

            if (!confirmContinue(scanner)) {
                break;
            }
        }
    }

    private String readField(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private boolean confirmContinue(Scanner scanner) {
        while (true) {
            String answer = readField(scanner, "Добавить ещё одну запись? (y/n): ");
            if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) {
                return true;
            }
            if (answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("no") || answer.isEmpty()) {
                return false;
            }
            System.out.println("Пожалуйста, введите y или n.");
        }
    }
}
