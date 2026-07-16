package sorter;

import model.Person;

public class SorterSelection{
    private UserSorter sorter;

    public SorterSelection() {}

    public void setSorter(UserSorter sorter) {
        this.sorter = sorter;
    }

    public Person[] sort(Person[] collection) {
        return sorter.sort(collection);
    }
}