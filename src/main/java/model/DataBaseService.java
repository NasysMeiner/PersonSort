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
    public boolean saveToFile(){
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("persons.dat"))) {
            outputStream.writeObject(this.dataBase);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public DataBase readFromFile(){
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("persons.dat"))) {
            this.dataBase = (DataBase) inputStream.readObject();
            return this.dataBase;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Create
    public Person createPerson(String name, String email, String password){
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
}
