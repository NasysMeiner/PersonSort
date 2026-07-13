package model;

/*
C - create
R - read
U - update
D - delete
 */

public class DataBaseService {
    private DataBase dataBase;

    public DataBaseService(){
        dataBase = new DataBase();
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

    public void addPerson(Person person) {
        this.dataBase.add(person);
    }

    public Person[] AddAllPerson(Person[] persons) {
        for(Person person : persons)
            addPerson(person);

        return persons;
    }

    // Read
    public Person[] getAll(){
        return dataBase.getAll();
    }

    public Person getById(Long id){
        return dataBase.get(dataBase.getIndexById(id));
    }

    public int getSize() {
        return dataBase.getSize();
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

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        for (Person person : dataBase)
            sb.append(person).append("\n");

        return sb.toString();
    }
}
