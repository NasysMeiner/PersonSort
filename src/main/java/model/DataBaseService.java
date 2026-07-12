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
        if (dataBase.getSize() > 0){
            throw new IllegalStateException("База данных имеет записи");
        }
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("persons.dat"))) {
            this.dataBase = (DataBase) inputStream.readObject();
            return this.dataBase;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Create
    public Person addPerson(String name, String email, String password){
        Person newPerson = new Person.Builder()
                .setName(name)
                .setMail(email)
                .setPassword(password)
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

    public void addFromFile(){
        try (BufferedReader reader = new BufferedReader(new FileReader("persons.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                String[] personToSave = line.split(";", 3);
                Person newPerson = addPerson(personToSave[0], personToSave[1], personToSave[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void printData(){
        StringBuilder sb = new StringBuilder();
        for (Person person : dataBase){
            sb.append(person).append("\n");
        }
        System.out.println(sb.toString());
    }
}
