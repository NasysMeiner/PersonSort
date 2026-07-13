package bootstrap;

import application.FileDataPersister;
import application.ManualPersonInputService;
import application.PersonInputService;
import input.ConsoleUserInput;
import input.UserInput;
import java.util.Comparator;
import model.DataBase;
import model.DataBaseService;
import model.Person;
import persistence.FileStateLoader;
import persistence.FileStateSaver;
import runner.MainRunner;
import sorter.MergeSort;
import sorter.SorterSelection;
import sorter.UserSorter;
import ui.ConsoleView;
import ui.View;


public class MainInitializer {
     public MainRunner initialize() {
          FileStateSaver stateSaver = new FileStateSaver();
          FileStateLoader stateLoader = new FileStateLoader();
          DataBase db = new DataBase();
          FileDataPersister dataPersister = new FileDataPersister(stateSaver, stateLoader, db);

          DataBaseService dataBaseService = new DataBaseService();

          UserSorter nameSorter = new MergeSort(Comparator.comparing(Person::getName));
          UserSorter passwordSorter = new MergeSort(Comparator.comparing(Person::getPassword));
          UserSorter mailSorter = new MergeSort(Comparator.comparing(Person::getMail));
          SorterSelection sorterSelection = new SorterSelection(nameSorter, passwordSorter, mailSorter);

          View view = new ConsoleView();
          UserInput userInput = new ConsoleUserInput();

          PersonInputService personInputService = new ManualPersonInputService(userInput, view, db);

          MainRunner mainRunner = new MainRunner(dataPersister, dataBaseService, sorterSelection, view, userInput, personInputService);

          return mainRunner;
     }
}