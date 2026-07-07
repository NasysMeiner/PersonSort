package main.java.model;

import java.io.*;

public class DataBaseService {
    public static boolean saveToFile(DataBase dataBase){
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("persons.dat"))) {
            outputStream.writeObject(dataBase);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static DataBase readFromFile(){
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("persons.dat"))) {
            DataBase restoredDataBase = (DataBase) inputStream.readObject();
            return restoredDataBase;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
