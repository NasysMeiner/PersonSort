package command;

import input.UserInput;
import router.MenuType;
import ui.View;

public class MainMenuCommand extends Command {
    public MainMenuCommand(View view, UserInput userInput) {
        super(view, userInput);
    }

    @Override
    public MenuType execute() {
        view.showMainMenu();
        String command = userInput.getInput();

        MenuType nextMenu;
        switch (command) {
            case "1" -> nextMenu = MenuType.FILL_MENU;

            case "2" -> nextMenu = MenuType.SORT_MENU;

            case "3" -> nextMenu = MenuType.SHOW_DATA_MENU;

            case "0" -> nextMenu = null;

            default -> {
                view.showMessage("Unknown command. Return main menu");
                nextMenu = MenuType.MAIN_MENU;
            }
        }

        return nextMenu;
    }
}
