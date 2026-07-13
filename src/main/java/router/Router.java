package router;

import command.Command;
import registry.MenuRegistry;

public class Router {
    private final MenuRegistry menuRegistry;

    public Router(MenuRegistry menuRegistry) {
        this.menuRegistry = menuRegistry;
    }

    public void run(MenuType startMenu) {
        MenuType currentMenu = startMenu;

        while (true) { 
             Command currentCommand = menuRegistry.getCommand(currentMenu);

             if(currentCommand == null)
                break;

             currentMenu = currentCommand.execute();
        }
    }
}
