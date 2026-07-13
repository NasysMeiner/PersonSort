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
        loadStateMenu();

        while (!isExit) { 
            view.showMainMenu();
            String command = userInput.getInput();
            selectCommand(command);
            waitForEnter();
        }

        if(dataBaseService.getSize() > 0) {
            String path = dataPersister.saveSystem();
            view.showMessage("State save in: " + path + "\n\nEnd programm!");
        }
    }

    private void loadStateMenu() {
        boolean isWork = true;
        while (isWork) { 
            view.showSaveStateMenu();
            String command = userInput.getInput();

            switch (command) {
                case "1" -> {
                    loadFileMenu();
                    isWork = false;
                }

                case "0" -> isWork = false;

                default -> view.showMessage("Unknown command. Try Again!");
            }
        }   
    }

    private void loadFileMenu() {
        String[] allFile = null;

        if(dataPersister.isStateExists()) {
            allFile = dataPersister.getAllStateExists();
        }

        int idx = 0;

        if(allFile != null) {
            for (String fileName : allFile) {
                view.showMessage((idx + 1) + ": " + fileName);
                idx++;
            }
        }

        view.showMessage((idx++ + 1) + ": not load");

        int command = Integer.parseInt(userInput.getInput());

        if(command <= idx - 1) {
            dataPersister.loadSystem(allFile[command - 1]);
            view.setCurrentNameState(allFile[command - 1]);
        }
        else if(command > idx){
            view.showMessage("File not found!");   
        }
    }

    private void selectCommand(String command) {
        switch (command) {
            case "1" -> dataMenu();

            case "2" -> sortMenu();

            case "3" -> view.showData(dataBaseService.getAll());

            case "0" -> isExit = true;

            default -> isBack = true;
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

            default -> isBack = true;
        }

        if(!isBack)
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

            default -> isBack = true;
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
