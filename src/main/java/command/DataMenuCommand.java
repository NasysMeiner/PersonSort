package command;

import application.PersonInputService;
import data.FileDataHolder;
import data.RandomDataHolder;
import input.UserInput;
import model.DataBaseService;
import model.Person;
import router.MenuType;
import ui.View;


public class DataMenuCommand extends Command {
    private static final int MAX_VALUE = 1000;

    private final DataBaseService dataBaseService;
    private final RandomDataHolder randomDataHolder;
    private final PersonInputService personInputService;

    public DataMenuCommand(View view, 
        UserInput userInput, 
        DataBaseService dataBaseService, 
        RandomDataHolder randomDataHolder, 
        PersonInputService personInputService) {
        super(view, userInput);

        this.dataBaseService = dataBaseService;
        this.randomDataHolder = randomDataHolder;
        this.personInputService = personInputService;
    }

    @Override
    public MenuType execute() {
        view.showFillDataMenu();
        String command = userInput.getInput();

        MenuType nextMenu = MenuType.MAIN_MENU;
        switch (command) {
            case "1" -> nextMenu = loadFromFile();

            case "2" -> nextMenu = loadRandom();

            case "3" -> personInputService.fill();

            case "0" -> nextMenu = MenuType.MAIN_MENU;

            default -> {
                view.showMessage("Unknown command. Try again.");
                nextMenu = MenuType.FILL_MENU;
            }
        }

        return nextMenu;
    }

    private MenuType loadFromFile() {
        try {
            view.showMessage("Enter your absolute file path:");
            String filePath = userInput.getInput();
            FileDataHolder fileDataHolder = new FileDataHolder(filePath, message -> view.showMessage("Error read in file: " + message));
            showAddedData((dataBaseService.AddAllPerson(fileDataHolder.getData(-1))));
            return MenuType.MAIN_MENU;
        } catch (RuntimeException e) {
            view.showMessage("File error: " + e.getMessage());
            waitForEnter();
            return MenuType.FILL_MENU;
        }
    }

    private MenuType loadRandom() {
        view.showMessage("Enter count data: ");
        int countData = validationInput(userInput.getInput());

        if(countData == -1){
            waitForEnter();
            return MenuType.FILL_MENU;
        }

        showAddedData(dataBaseService.AddAllPerson(randomDataHolder.getData(countData)));

        return MenuType.MAIN_MENU;
    }

    private int validationInput(String input) {
        int countData;

        if(input.isEmpty()) {
            view.showMessage("Empty input!");
            return -1;
        }

        try {
            countData = Integer.parseInt(input);

            if(countData <= 0) {
                view.showMessage("Incorrect input!");
                return -1;
            }

            countData = Math.min(countData, MAX_VALUE);
        } catch (NumberFormatException e) {
            view.showMessage("Invalid number!");
            return -1;
        }

        return countData;
    }

    private void showAddedData(Person[] addedData) {
        view.showData(addedData);
        waitForEnter();
    }
}
