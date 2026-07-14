package command;

import input.UserInput;
import router.MenuType;
import ui.View;

public abstract class Command {
    protected final View view;
    protected final UserInput userInput;
    
    public Command(View view, UserInput userInput) {
        this.view = view;
        this.userInput = userInput;
    }

    protected void waitForEnter() {
        view.showMessage("Press Enter to continue...");
        userInput.getInput();
    }

    public abstract MenuType execute();
}
