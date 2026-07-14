package command;

import application.DataPersister;
import input.UserInput;
import java.io.IOException;
import model.DataBaseService;
import model.Person;
import router.MenuType;
import sorter.SorterSelection;
import ui.View;

public class SortMenuCommand extends Command {
    private final DataBaseService dataBaseService;
    private final SorterSelection sorterSelection;
    private final DataPersister dataPersister;

    public SortMenuCommand(View view, UserInput userInput, DataBaseService dataBaseService, SorterSelection sorterSelection, DataPersister dataPersister) {
        super(view, userInput);

        this.dataBaseService = dataBaseService;
        this.sorterSelection = sorterSelection;
        this.dataPersister = dataPersister;
    }

    @Override
    public MenuType execute() {
        if(dataBaseService.getAll().length < 2) {
            view.showMessage("The amount of data is less than 2!\nUse command '1.Go to 'Fill data menu''");
            return MenuType.MAIN_MENU;
        }

        view.showSortMenu();
        String command = userInput.getInput();

        Person[] sortedArray = null;
        String titleSort = "";

        MenuType nextMenu = MenuType.MAIN_MENU;
        switch (command) {
            case "1" -> {
                sortedArray = sorterSelection.sortCollectionToName(dataBaseService.getAll());
                titleSort = "Sort to Name";
            }

            case "2" -> {
                sortedArray = sorterSelection.sortCollectionToPassword(dataBaseService.getAll());
                titleSort = "Sort to Password";
            }

            case "3" -> {
                sortedArray = sorterSelection.sortCollectionToMail(dataBaseService.getAll());
                titleSort = "Sort to Mail";
            }

            case "0" -> {}

            default -> {
                view.showMessage("Incorrect input!");
            }
        }

        if(sortedArray != null && sortedArray.length != 0) {
            view.showData(sortedArray);

            try {
                String path = dataPersister.appendResult(titleSort, sortedArray);
                view.showMessage("\nSave result to: " + path + "\n");
            } catch (IOException e) {
                view.showMessage(e.getMessage());
            } 

            waitForEnter();
        }

        return nextMenu;
    }
    
}
