package sorter;

import model.Person;

import java.util.Comparator;

public class PasswordSorter implements UserSorter {

    private final MergeSort mergeSort = new MergeSort();

    @Override
    public void sort(Person[] people) {
        mergeSort.sort(
                people,
                Comparator.comparing(Person::getPassword)
        );
    }
}