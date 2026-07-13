package ui;

import model.Person;

public class ConsoleView implements View {
    private String currentStateName = "";

    @Override
    public void setCurrentNameState(String stateName) {
        currentStateName = stateName;
    }

    @Override
    public void showSaveStateMenu() {
        StringBuilder builder = new StringBuilder("Load current state?\n");
        builder.append("1. Yes\n");
        builder.append("0. No\n");

        showMessage(builder.toString());
    }

    @Override
    public void showMainMenu() {
        StringBuilder builder = new StringBuilder("========== Main Menu");

        if(!currentStateName.isEmpty())
            builder.append("(").append(currentStateName).append(")");

        builder.append("==========\n");
        builder.append("1. Go to 'Fill data menu'\n");
        builder.append("2. Go to 'Sort Menu'\n");
        builder.append("3. Show all data\n");
        builder.append("0. Exit\n");
        builder.append("\nChoose option: ");

        showMessage(builder.toString());
    }

    @Override
    public void showFillDataMenu() {
        StringBuilder builder = new StringBuilder("========== Fill Data Menu ==========\n");
        builder.append("1. Fill file\n");
        builder.append("2. Fill randomly\n");
        builder.append("3. Fill manually\n");
        builder.append("0. back\n");

        showMessage(builder.toString());
    }

    @Override
    public void showSortMenu() {
        StringBuilder builder = new StringBuilder("========== Sort Menu ==========\n");
        builder.append("1. Sort by name\n");
        builder.append("2. Sort by password\n");
        builder.append("3. Sort by email\n");
        builder.append("0. back\n");

        showMessage(builder.toString());
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

    @Override
    public void showDataPersisterMenu() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}