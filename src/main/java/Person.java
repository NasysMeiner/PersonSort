import java.util.concurrent.atomic.AtomicLong;

public class Person {
//    private static final AtomicLong idCreator = new AtomicLong(0);
//    private Long id;
    private final String name;
    private final String email;
    private final String password;

    public Person(PersonBuilder builder) {
//        this.id = idCreator.addAndGet(1L);
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
    }

//    public Long getId() {
//        return id;
//    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return email;
    }

    String getPassword() {
        return password;
    }
    public static class PersonBuilder{
        private String name;
        private String password;
        private String email;

        public PersonBuilder name(String name){
            this.name = name;
            return this;
        }

        public PersonBuilder email (String email){
            this.email = email;
            return this;
        }

        public PersonBuilder password(String password){
            this.password = password;
            return this;
        }

        public Person build(){
            if (name == null || email == null || password == null){
                throw new IllegalArgumentException("Все поля должны быть заполнены!");
            }
            return new Person(this);
        }

    }
}
