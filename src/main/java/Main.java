import bootstrap.MainInitializer;
import model.DataBase;
import model.DataBaseService;
import model.Person;
import runner.MainRunner;

public class Main {
    public static void main(String[] args) {

        DataBaseService service = new DataBaseService();

        Person igor = new Person.Builder()
                .name("Igor")
                .mail("tututu@mail.ru")
                .password("paroLanet$1")
                .build();
        Person slava = new Person.Builder()
                .name("Slava")
                .mail("figa@mail.ru")
                .password("paroLanet$2")
                .build();

        service.addPerson(igor.getName(), igor.getMail(), "paroLanet$1");
        service.addPerson(slava.getName(), slava.getMail(), "paroLanet$2");
        for (Person person: service.getAll()){
            System.out.println(person);
        }

        service.printData();
        
        MainInitializer initializer = new MainInitializer();

        initializer.initialize();

        MainRunner runner = new MainRunner();

        runner.run();
    }
}