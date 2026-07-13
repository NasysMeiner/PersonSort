package command;

import input.UserInput;
import model.DataBaseService;
import router.MenuType;
import ui.View;

public class ShowDataMenuCommand extends Command{
    private final DataBaseService dataBaseService;

    public ShowDataMenuCommand(View view, UserInput userInput, DataBaseService dataBaseService) {
        super(view, userInput);

        this.dataBaseService = dataBaseService;
    }

    @Override
    public MenuType execute() {
        view.showData(dataBaseService.getAll());
        waitForEnter();

        return MenuType.MAIN_MENU;
    }
    
}
