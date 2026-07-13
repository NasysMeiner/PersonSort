package ui;

import model.Person;

public interface View {
    void showMainMenu();
    void showFillDataMenu();
    void showSortMenu();
    void showDataPersisterMenu();
    void showData(Person[] people);
    void showMessage(String message);
}