package model;

import java.util.stream.Stream;

public class DataBaseService {
    private final DataBase dataBase;

    public DataBaseService(DataBase dataBase){
        this.dataBase = dataBase;
    }
    
    public int getSize(){
        return dataBase.getSize();
    }

    // Create
    public Person addPerson(String name, String email, String password) {
        Person newPerson = new Person.Builder()
                .setName(name)
                .setMail(email)
                .setPassword(password)
                .build();

        this.dataBase.add(newPerson);

        return newPerson;
    }

    public Person[] AddAllPerson(Person[] persons) {
        Stream.of(persons)
            .forEach(this::addPerson);

        return persons;
    }

    public Person addPerson(Person person) {
        this.dataBase.add(person);

        return person;
    }

    // Read
    public Person[] getAll(){
        return dataBase.getAll();
    }
}
