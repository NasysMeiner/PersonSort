package registry;

import command.Command;
import java.util.HashMap;
import java.util.Map;
import router.MenuType;

public class MenuRegistry {
    private final Map<MenuType, Command> commands = new HashMap<>();

    public void registry(MenuType menuType, Command command) {
        commands.put(menuType, command);
    }

    public Command getCommand(MenuType menuType) {
        return commands.get(menuType);
    }
}
