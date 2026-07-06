public class Main {
    public static void main(String[] args) {

        Person igor = new Person.Builder()
                .name("Igor")
                .mail("tututu@mail.ru")
                .password("parolanet")
                .build();

        System.out.println(igor);
        DataBase database = new DataBase();
        
    }
}