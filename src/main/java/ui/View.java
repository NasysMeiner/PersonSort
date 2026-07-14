package ui;

import model.Person;

public interface View {
    void setCurrentNameState(String stateName);
    void showSaveStateMenu();
    void showMainMenu();
    void showFillDataMenu();
    void showSortMenu();
    void showDataPersisterMenu();
    void showSearchDataMenu();
    void showData(Person[] people);
    void showMessage(String message);
}