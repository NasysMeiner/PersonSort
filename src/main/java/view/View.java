package view;

import model.Person;

public interface View {

    void showMenu();

    void showData(Person[] people);

    void showMessage(String message);
}