package command;

import input.UserInput;
import model.DataBaseService;
import router.MenuType;
import sorter.SorterSelection;
import ui.View;

public class SortMenuCommand extends Command {
    private final DataBaseService dataBaseService;
    private final SorterSelection sorterSelection;

    public SortMenuCommand(View view, UserInput userInput, DataBaseService dataBaseService, SorterSelection sorterSelection) {
        super(view, userInput);

        this.dataBaseService = dataBaseService;
        this.sorterSelection = sorterSelection;
    }

    @Override
    public MenuType execute() {
        if(dataBaseService.getAll().length < 2) {
            view.showMessage("The amount of data is less than 2!\nUse command '1.Go to 'Fill data menu''");
            return MenuType.MAIN_MENU;
        }

        view.showSortMenu();
        String command = userInput.getInput();

        MenuType nextMenu;
        switch (command) {
            case "1" -> {
                nextMenu = MenuType.MAIN_MENU;
                view.showData(sorterSelection.sortCollectionToName(dataBaseService.getAll()));
                waitForEnter();
            }

            case "2" -> {
                nextMenu = MenuType.MAIN_MENU;
                view.showData(sorterSelection.sortCollectionToPassword(dataBaseService.getAll()));
                waitForEnter();
            }

            case "3" -> {
                nextMenu = MenuType.MAIN_MENU;
                view.showData(sorterSelection.sortCollectionToMail(dataBaseService.getAll()));
                waitForEnter();
            }

            case "0" -> nextMenu = MenuType.MAIN_MENU;

            default -> {
                view.showMessage("Incorrect input!");
                nextMenu = MenuType.MAIN_MENU;
            }
        }

        return nextMenu;
    }
    
}
