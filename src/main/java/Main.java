import bootstrap.MainInitializer;
import model.DataBase;
import model.Person;
import runner.MainRunner;

public class Main {
    public static void main(String[] args) {

        Person igor = new Person.Builder()
                .name("Igor")
                .mail("tututu@mail.ru")
                .password("parolanet")
                .build();
        Person slava = new Person.Builder()
                .name("Slava")
                .mail("figa@mail.ru")
                .password("parolanet2")
                .build();
        
        DataBase database = new DataBase();
        database.add(igor);
        database.add(slava);
        for (Person person: database){
            System.out.println(person);
        }
        
        MainInitializer initializer = new MainInitializer();

        initializer.initialize();

        MainRunner runner = new MainRunner();

        runner.run();
    }
}