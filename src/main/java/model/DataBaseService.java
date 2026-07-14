package model;

/*
C - create
R - read
U - update
D - delete
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public class DataBaseService {
    DataBase dataBase;
    public DataBaseService(DataBase dataBase){
        this.dataBase = dataBase;
    }
    public int getSize(){
        return dataBase.getSize();
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

    public void printData(){
        StringBuilder sb = new StringBuilder();
        for (Person person : dataBase){
            sb.append(person).append("\n");
        }
        System.out.println(sb);
    }

    public long countOccurrences(String targetName) throws InterruptedException, ExecutionException {
        int threads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        int n = dataBase.getSize();
        int chunk = Math.max(1, n / threads);
        var futures = new ArrayList<Future<Long>>();
        for (int t = 0; t < threads; t++) {
            int start = t * chunk;
            int end = (t == threads - 1) ? n : ( t + 1) * chunk;
            futures.add(executor.submit(() ->{
                long localCount = 0;
                for (int i = start; i < end; i++) {
                    if (dataBase.get(i).getName().equals(targetName)){
                        localCount++;
                    }
                }
                return localCount;
            }));
        }
        long total = 0;
        for (var f : futures){
            total += f.get();
        }
        executor.shutdown();
        return total;
    }

    public Person[] AddAllPerson(Person[] data) {
        Stream.of(data)
                .forEach(this::addPerson);
        return dataBase.getAll();
    }
    public void addPerson(Person person) {
        this.dataBase.add(person);
    }
}
