package command;

import input.UserInput;
import java.util.function.Predicate;
import model.DataBaseService;
import model.Person;
import router.MenuType;
import service.SearchService;
import ui.View;

public class SearchDataMenu extends Command {
    private final SearchService searchService;
    private final DataBaseService dataBaseService;

    public SearchDataMenu(View view, UserInput userInput, SearchService searchService, DataBaseService dataBaseService) {
        super(view, userInput);

        this.searchService = searchService;
        this.dataBaseService = dataBaseService;
    }

    @Override
    public MenuType execute() {
        if(dataBaseService.getSize() == 0) {
            view.showMessage("No Data!");
            return MenuType.MAIN_MENU;
        }

        view.showSearchDataMenu();
        String command = userInput.getInput();

        view.showMessage("Target: ");

        String target = userInput.getInput().trim();
        if (target.isEmpty()) {
            view.showMessage("Search target cannot be empty. Try again!");
            waitForEnter();

            return MenuType.SEARCH_DATA_MENU;
        }

        MenuType nextMenu;
        switch (command) {
            case "1" -> nextMenu = Search((person) -> person.getName().equals(target), target);

            case "2" -> nextMenu = Search((person) -> person.getPassword().equals(target), target);

            case "3" -> nextMenu = Search((person) -> person.getMail().equals(target), target);

            case "0" -> nextMenu = null;

            default -> {
                view.showMessage("Unknown command. Return main menu");
                nextMenu = MenuType.SEARCH_DATA_MENU;
            }
        }

        return nextMenu;
    }
    
    private MenuType Search(Predicate<Person> predicate, String target) {
        long searched = searchService.searchElements(predicate);
        view.showMessage("Target: '" + target + "' count in collection: " + searched);
        waitForEnter();

        return MenuType.MAIN_MENU;
    }
}
