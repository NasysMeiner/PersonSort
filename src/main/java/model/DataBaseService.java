package model;

/*
C - create
R - read
U - update
D - delete
 */

import java.io.*;

public class DataBaseService {
    DataBase dataBase;
    public DataBaseService(){
        dataBase = new DataBase();
    }

    // Create
    public Person addPerson(String name, String email, String password){
        Person newPerson = new Person.Builder()
                .name(name)
                .mail(email)
                .password(password)
                .build();
        this.dataBase.add(newPerson);

        return newPerson;

    }

    // Read
    public Person[] getAll(){
        return dataBase.getAll();
    }

    public Person getById(Long id){
        return dataBase.get(dataBase.getIndexById(id));
    }

    // Update
    public Person update(Long id, Person update){
        Person personToUpdate = dataBase.get(dataBase.getIndexById(id));
        personToUpdate.setName(update.getName());
        personToUpdate.setMail(update.getMail());
        personToUpdate.setPassword(update.getPassword());
        return personToUpdate;
    }

    // Delete
    public Person deletePersonById(Long id){
        int indexToDelete = dataBase.getIndexById(id);
        return dataBase.delete(indexToDelete);
    }

    public int getIndexByName(String name){
         return dataBase.getIndexByName(name);
    }

    public void printData(){
        StringBuilder sb = new StringBuilder();
        for (Person person : dataBase){
            sb.append(person).append("\n");
        }
        System.out.println(sb);
    }
}
