package runner;

import application.DataPersister;
import application.PersonInputService;
import data.DataHolder;
import data.FileDataHolder;
import data.RandomDataHolder;
import input.UserInput;
import model.DataBaseService;
import model.Person;
import sorter.SorterSelection;
import ui.View;

public class MainRunner {
    private final PersonInputService personInputService;
    private final DataPersister dataPersister;
    private final DataBaseService dataBaseService;
    private final SorterSelection sorterSelection;
    private final View view;
    private final UserInput userInput;

    private final DataHolder randomDataHolder;

    private boolean isExit = false;
    private boolean isBack = false;

    public MainRunner(DataPersister dataPersister, 
        DataBaseService dataBaseService, 
        SorterSelection sorterSelection, 
        View view, 
        UserInput userInput, 
        PersonInputService personInputService
    ) {
        this.personInputService = personInputService;
        this.dataPersister = dataPersister;
        this.dataBaseService = dataBaseService;
        this.sorterSelection = sorterSelection;
        this.view = view;
        this.userInput = userInput;

        randomDataHolder = new RandomDataHolder();
    }

    public void run() {
        while (!isExit) { 
            view.showMainMenu();
            String command = userInput.getInput();
            selectCommand(command);
            waitForEnter();
        }

        view.showMessage("End programm");
    }

    private void selectCommand(String command) {
        switch (command) {
            case "1" -> dataMenu();

            case "2" -> sortMenu();

            case "3" -> view.showData(dataBaseService.getAll());

            case "0" -> isExit = true;
        }
    }

    private void dataMenu() {
        view.showFillDataMenu();
        String command = userInput.getInput();

        switch (command) {
            case "1" -> {
                String filePatch = userInput.getInput();
                ShowData(dataBaseService.AddAllPerson(new FileDataHolder(filePatch).getData(-1)));
            }

            case "2" -> ShowData(dataBaseService.AddAllPerson(randomDataHolder.getData(GetCountData())));

            case "3" -> personInputService.fill();

            case "0" -> isBack = true;
        }

        view.showMessage("DataBase size: " + dataBaseService.getSize());
    }

    private void sortMenu() {
        if(dataBaseService.getAll().length < 2) {
            view.showMessage("The amount of data is less than 2!\nUse command 1.Go to 'Fill data menu'");
            return;
        }

        view.showSortMenu();
        String command = userInput.getInput();

        switch (command) {
            case "1" -> {
                ShowData(sorterSelection.sortCollectionToName(dataBaseService.getAll()));
            }

            case "2" -> {
                ShowData(sorterSelection.sortCollectionToPassword(dataBaseService.getAll()));
            }

            case "3" -> {
                ShowData(sorterSelection.sortCollectionToMail(dataBaseService.getAll()));
            }

            case "0" -> isBack = true;
        }
    }

    private void waitForEnter() {
        if(isBack || isExit) {
            isBack = false;
            return;
        }

        view.showMessage("Press Enter to continue...");
        userInput.getInput();
    }

    private int GetCountData() {
        view.showMessage("Enter count data: ");

        return Integer.parseInt(userInput.getInput());
    }

    private void ShowData(Person[] persons) {
        if(persons == null)
            return;

        view.showData(persons);
    }
}
