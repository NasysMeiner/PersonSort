package command;

import input.UserInput;
import java.util.function.Function;
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
            waitForEnter();
            return MenuType.MAIN_MENU;
        }

        view.showSearchDataMenu();
        String command = userInput.getInput();

        MenuType nextMenu;
        switch (command) {
            case "1" -> nextMenu = search(target -> (person) -> person.getName().equals(target));

            case "2" -> nextMenu = search(target -> (person) -> person.getPassword().equals(target));

            case "3" -> nextMenu = search(target -> (person) -> person.getMail().equals(target));

            case "0" -> nextMenu = MenuType.MAIN_MENU;

            default -> {
                view.showMessage("Unknown command. Return main menu");
                nextMenu = MenuType.SEARCH_DATA_MENU;
            }
        }

        return nextMenu;
    }
    
    private MenuType search(Function<String, Predicate<Person>> function) {
        String target = getTarget();
        long searched = searchService.searchElements(function.apply(target), dataBaseService.getAll());
        view.showMessage("Target: '" + target + "' count in collection: " + searched);
        waitForEnter();

        return MenuType.MAIN_MENU;
    }

    private String getTarget() {
        view.showMessage("Target: ");

        String target = userInput.getInput().trim();
        if (target.isEmpty()) {
            view.showMessage("Search target cannot be empty. Try again!");
            waitForEnter();

            return null;
        }

        return target;
    }
}
