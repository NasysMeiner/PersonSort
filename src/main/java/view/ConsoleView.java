package view;

import model.Person;

public class ConsoleView implements View {

    @Override
    public void showMenu() {
        System.out.println("========== MENU ==========");
        System.out.println("1. Fill from file");
        System.out.println("2. Fill randomly");
        System.out.println("3. Fill manually");
        System.out.println("4. Sort by name");
        System.out.println("5. Sort by password");
        System.out.println("6. Sort by email");
        System.out.println("7. Show all persons");
        System.out.println("0. Exit");
        System.out.print("Choose option: ");
    }

    @Override
    public void showData(Person[] people) {
        if (people == null || people.length == 0) {
            System.out.println("No data.");
            return;
        }

        for (Person person : people) {
            System.out.println(person);
        }
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }
}