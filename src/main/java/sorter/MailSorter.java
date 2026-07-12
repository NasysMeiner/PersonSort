package sorter;

import model.Person;

import java.util.Comparator;

public class MailSorter implements UserSorter {

    private final MergeSort mergeSort = new MergeSort();

    @Override
    public void sort(Person[] people) {
        mergeSort.sort(
                people,
                Comparator.comparing(Person::getMail)
        );
    }
}