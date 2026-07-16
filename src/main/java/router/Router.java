package router;

import command.Command;
import registry.MenuRegistry;

public class Router {
    private final MenuRegistry menuRegistry;

    public Router(MenuRegistry menuRegistry) {
        this.menuRegistry = menuRegistry;
    }

    public void run(MenuType startMenu) throws Exception {
        MenuType currentMenu = startMenu;

        while (true) { 
            Command currentCommand = menuRegistry.getCommand(currentMenu);

            if(currentCommand == null)
            break;

            clearWindowsConsole();

             currentMenu = currentCommand.execute();
        }
    }

    private void clearWindowsConsole() throws Exception {
        new ProcessBuilder("cmd", "/c", "cls")
                    .inheritIO()
                    .start()
                    .waitFor();
    }
}
