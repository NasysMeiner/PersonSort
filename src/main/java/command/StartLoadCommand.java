package command;

import application.DataPersister;
import input.UserInput;
import java.io.IOException;
import router.MenuType;
import ui.View;

public class StartLoadCommand extends Command{
    private final DataPersister dataPersister;

    public StartLoadCommand(View view, UserInput userInput, DataPersister dataPersister) {
        super(view, userInput);

        this.dataPersister = dataPersister;
    }

    @Override
    public MenuType execute() {
        if(!dataPersister.isStateExists())
            return MenuType.MAIN_MENU;

        view.showSaveStateMenu();
        String command = userInput.getInput();

        MenuType nextMenu;
        switch (command) {
            case "1" -> nextMenu = loadFileMenu();

            case "0" -> nextMenu = MenuType.MAIN_MENU;

            default -> {
                view.showMessage("Unknown command. Try Again!");
                nextMenu = MenuType.START_LOAD_MENU;
            }
        }

        return nextMenu;
    }
    
    private MenuType loadFileMenu() {
        String[] allFile = dataPersister.getAllStateExists();

        int idx = 0;
        if(allFile != null) {
            for (String fileName : allFile) {
                view.showMessage((idx + 1) + ": " + fileName);
                idx++;
            }
        }

        view.showMessage((idx++ + 1) + ": not load");

        int command = Integer.parseInt(userInput.getInput());

        try {
            if(command <= idx - 1 && command > 0) {
                dataPersister.loadSystem(allFile[command - 1]);
                view.setCurrentNameState(allFile[command - 1]);
            }
            else if(command <= 0 || command > idx){
                view.showMessage("File not found!");   
                return MenuType.START_LOAD_MENU;
            }
        } catch (IOException | ClassNotFoundException e) {
            view.showMessage(e.getMessage());
        }

        return MenuType.MAIN_MENU;
    }
}
