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
import model.Person;
import persistence.FileStateLoader;
import persistence.FileStateSaver;
import registry.MenuRegistry;
import router.MenuType;
import router.Router;
import runner.MainRunner;
import service.SearchService;
import sorter.EvenOddSorterDecorator;
import sorter.MergeSort;
import sorter.SorterSelection;
import sorter.UserSorter;
import ui.ConsoleView;
import ui.View;

import java.util.Comparator;

public class MainInitializer {

     public MainRunner initialize() {
          FileStateSaver stateSaver = new FileStateSaver();
          FileStateLoader stateLoader = new FileStateLoader();
          DataBase db = new DataBase();

          FileDataPersister dataPersister =
                  new FileDataPersister(stateSaver, stateLoader, db);

          DataBaseService dataBaseService = new DataBaseService(db);

          UserSorter nameSorter = new EvenOddSorterDecorator(
                  new MergeSort(Comparator.comparing(Person::getName)),
                  person -> person.getName() != null
                          && person.getName().length() % 2 == 0
          );

          UserSorter passwordSorter = new EvenOddSorterDecorator(
                  new MergeSort(Comparator.comparing(Person::getPassword)),
                  person -> person.getPassword() != null
                          && person.getPassword().length() % 2 == 0
          );

          UserSorter mailSorter = new EvenOddSorterDecorator(
                  new MergeSort(Comparator.comparing(Person::getMail)),
                  person -> person.getMail() != null
                          && person.getMail().length() % 2 == 0
          );

          SorterSelection sorterSelection =
                  new SorterSelection(
                          nameSorter,
                          passwordSorter,
                          mailSorter
                  );

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

          MainRunner mainRunner = new MainRunner(dataPersister, dataBaseService, view, router);

          return new MainRunner(
                  dataPersister,
                  dataBaseService,
                  sorterSelection,
                  view,
                  userInput,
                  personInputService
          );
     }
}