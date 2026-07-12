package runner;

import application.DataPersister;
import input.UserInput;
import sorter.SorterSelection;
import ui.View;

public class MainRunner {
    private final DataPersister dataPersister;
    private final SorterSelection sorterSelection;
    private final View view;
    private final UserInput userInput;

    public MainRunner(DataPersister dataPersister, SorterSelection sorterSelection, View view, UserInput userInput) {
        this.dataPersister = dataPersister;
        this.sorterSelection = sorterSelection;
        this.view = view;
        this.userInput = userInput;
    }

    public void run() {
        System.out.println("Run");

        view.showMenu();

        String command = userInput.getInput();
    }
}
