package bootstrap;

import application.FileDataPersister;
import input.ConsoleUserInput;
import input.UserInput;
import model.DataBase;
import persistence.FileStateLoader;
import persistence.FileStateSaver;
import runner.MainRunner;
import sorter.MailSorter;
import sorter.NameSorter;
import sorter.PasswordSorter;
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

          UserSorter nameSorter = new NameSorter();
          UserSorter passwordSorter = new PasswordSorter();
          UserSorter mailSorter = new MailSorter();
          SorterSelection sorterSelection = new SorterSelection(nameSorter, passwordSorter, mailSorter);

          View view = new ConsoleView();
          UserInput userInput = new ConsoleUserInput();

          MainRunner mainRunner = new MainRunner(dataPersister, sorterSelection, view, userInput);

          return mainRunner;
     }
}