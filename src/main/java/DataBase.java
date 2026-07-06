import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

public class DataBase<T> {
//    private AtomicLong idCreator = new AtomicLong(0);
    private T[] persons;
    private int size = 0;

    public DataBase(){
        persons = new T[10];
    }

    public void add(T person){
        if (size == persons.length){
            persons = Arrays.copyOf(persons, persons.length * 2);
        }
        persons[size++] = person;
    }

//    public T getByid
    public T get(int index){
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Индекс "+ index
                    + " за пределами действительного интервала от 0 до " + size );
        }
        return persons[index];
    }
    
}
