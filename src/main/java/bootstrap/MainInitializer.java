package bootstrap;

import application.FileDataPersister;
import application.ManualPersonInputService;
import application.PersonInputService;
import command.Command;
import command.DataMenuCommand;
import command.MainMenuCommand;
import command.SearchDataMenu;
import command.ShowDataMenuCommand;
import command.SortMenuCommand;
import command.StartLoadCommand;
import data.RandomDataHolder;
import input.ConsoleUserInput;
import input.UserInput;
import model.DataBase;
import model.DataBaseService;
import persistence.FileStateLoader;
import persistence.FileStateSaver;
import registry.MenuRegistry;
import router.MenuType;
import router.Router;
import runner.MainRunner;
import service.SearchService;
import sorter.SorterSelection;
import ui.ConsoleView;
import ui.View;

public class MainInitializer {

        public MainRunner initialize() {
                FileStateSaver stateSaver = new FileStateSaver();
                FileStateLoader stateLoader = new FileStateLoader();
                DataBase db = new DataBase();

                FileDataPersister dataPersister =
                        new FileDataPersister(stateSaver, stateLoader, db);

                DataBaseService dataBaseService = new DataBaseService(db);

                SorterSelection sorterSelection = new SorterSelection();

                SearchService searchService = new SearchService();

                View view = new ConsoleView();
                UserInput userInput = new ConsoleUserInput();

                PersonInputService personInputService = new ManualPersonInputService(userInput, view, db);

                RandomDataHolder randomDataHolder = new RandomDataHolder();

                Command startLoadCommand = new StartLoadCommand(view, userInput, dataPersister);
                Command menuCommand = new MainMenuCommand(view, userInput);
                Command dataMenuCommand = new DataMenuCommand(view, userInput, dataBaseService, randomDataHolder, personInputService);
                Command showDataMenuCommand = new ShowDataMenuCommand(view, userInput, dataBaseService);
                Command sortMenuCommand = new SortMenuCommand(view, userInput, dataBaseService, sorterSelection, dataPersister);
                Command searchDataMenu = new SearchDataMenu(view, userInput, searchService, dataBaseService);

                MenuRegistry menuRegistry = new MenuRegistry();
                menuRegistry.registry(MenuType.START_LOAD_MENU, startLoadCommand);
                menuRegistry.registry(MenuType.MAIN_MENU, menuCommand);
                menuRegistry.registry(MenuType.FILL_MENU, dataMenuCommand);
                menuRegistry.registry(MenuType.SHOW_DATA_MENU, showDataMenuCommand);
                menuRegistry.registry(MenuType.SORT_MENU, sortMenuCommand);
                menuRegistry.registry(MenuType.SEARCH_DATA_MENU, searchDataMenu);

                Router router = new Router(menuRegistry);

                return new MainRunner(dataPersister, dataBaseService, view, router);
        }
}