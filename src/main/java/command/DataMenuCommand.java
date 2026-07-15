package command;

import application.PersonInputService;
import data.FileDataHolder;
import data.RandomDataHolder;
import input.UserInput;
import model.DataBaseService;
import router.MenuType;
import ui.View;


public class DataMenuCommand extends Command {
    private static final int MAX_VALUE = 1000;

    private final DataBaseService dataBaseService;
    private final RandomDataHolder randomDataHolder;
    private final PersonInputService personInputService;

    public DataMenuCommand(View view, UserInput userInput, DataBaseService dataBaseService, RandomDataHolder randomDataHolder, PersonInputService personInputService) {
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
            case "1" -> {
                nextMenu = loadFromFile();
                waitForEnter();
            }

            case "2" -> {
                nextMenu = loadRandom();
                waitForEnter();
            }

            case "3" -> {
                personInputService.fill();
                waitForEnter();
            }

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
            view.showData((dataBaseService.AddAllPerson(new FileDataHolder(filePath).getData(-1))));
            return MenuType.MAIN_MENU;
        } catch (RuntimeException e) {
            view.showMessage("File error: " + e.getMessage());
            return MenuType.FILL_MENU;
        }
    }

    private MenuType loadRandom() {
        view.showMessage("Enter count data: ");
        int countData = validationInput(userInput.getInput());

        if(countData == -1)
            return MenuType.FILL_MENU;

        view.showData(dataBaseService.AddAllPerson(randomDataHolder.getData(countData)));

        return MenuType.MAIN_MENU;
    }

    private int validationInput(String input) {
        int countData;

        if(input.isEmpty()) {
            view.showMessage("Empty input. Use default: 1");
            return -1;
        }

        try {
            countData = Integer.parseInt(input);

            if(countData <= 0) {
                view.showMessage("Incorrect input. Use default: 1");
                countData = 1;
            }

            countData = Math.min(countData, MAX_VALUE);
        } catch (NumberFormatException e) {
            view.showMessage("Invalid number!");
            return -1;
        }

        return countData;
    }
}
