package model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;

public class DataBase implements Iterable<Person>{
    private Person[] persons;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public DataBase(){
        persons = new Person[10];
    }

    public void add(Person person){
        if (size == persons.length){
            persons = Arrays.copyOf(persons, persons.length * 2);
        }
        persons[size++] = person;
    }

    public Person get(int index){
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Индекс "+ index
                    + " за пределами действительного интервала от 0 до " + size );
        }
        return persons[index];
    }

    public Person delete(int index){
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Индекс "+ index
                    + " за пределами действительного интервала от 0 до " + size );
        }
        Person removedItem = get(index);
        if (size - index - 1 > 0){
            System.arraycopy(persons, index + 1, persons, index, size - index - 1);
        }
        persons[--size] = null;

        return removedItem;
    }
    public int getSize(){
        return size;
    }
    public Person getByName(String name){
        return Arrays.stream(persons)
                .filter(person -> person.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Человек с таким именем не найден"));
    }
    public Person getByEmail(String email){
        return Arrays.stream(persons)
                .filter(person -> person.getMail().equals(email))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Человек с такой почтой не найден"));
    }

    @Override
    public Iterator<Person> iterator() {
        return new PersonIterator();
    }

    @Override
    public void forEach(Consumer<? super Person> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<Person> spliterator() {
        return Iterable.super.spliterator();
    }

    private class PersonIterator implements Iterator<Person>{
        private int cursor = 0;
        private int previus = -1;

        @Override
        public boolean hasNext() {
            return cursor<size;
        }

        @Override
        public Person next() {
            if (cursor >= size){
                throw new NoSuchElementException("Вышли за пределы коллекции");
            }
            previus = cursor;
            return persons[cursor++];
        }

        @Override
        public void remove() {
            Iterator.super.remove();
        }
    }
}
