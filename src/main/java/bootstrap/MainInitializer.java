package bootstrap;

import application.FileDataPersister;
import application.ManualPersonInputService;
import application.PersonInputService;
import input.ConsoleUserInput;
import input.UserInput;
import model.DataBase;
import model.DataBaseService;
import model.Person;
import persistence.FileStateLoader;
import persistence.FileStateSaver;
import runner.MainRunner;
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

          View view = new ConsoleView();
          UserInput userInput = new ConsoleUserInput();

          PersonInputService personInputService =
                  new ManualPersonInputService(userInput, view, db);

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