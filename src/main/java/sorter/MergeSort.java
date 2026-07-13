package sorter;

import java.util.Comparator;
import model.Person;

public class MergeSort implements UserSorter {
    private final Comparator<Person> comparator;

    public MergeSort(Comparator<Person> comparator) {
        this.comparator = comparator;
    }

    @Override
    public Person[] sort(Person[] people) {
        if (people == null || people.length < 2) {
            return null;
        }

        Person[] buffer = new Person[people.length];
        mergeSort(people, buffer, 0, people.length - 1, comparator);

        return people;
    }

    private void mergeSort(
            Person[] people,
            Person[] buffer,
            int left,
            int right,
            Comparator<Person> comparator
    ) {
        if (left >= right) {
            return;
        }

        int middle = left + (right - left) / 2;

        mergeSort(people, buffer, left, middle, comparator);
        mergeSort(people, buffer, middle + 1, right, comparator);

        merge(people, buffer, left, middle, right, comparator);
    }

    private void merge(
            Person[] people,
            Person[] buffer,
            int left,
            int middle,
            int right,
            Comparator<Person> comparator
    ) {
        int leftIndex = left;
        int rightIndex = middle + 1;
        int bufferIndex = left;

        while (leftIndex <= middle && rightIndex <= right) {
            if (comparator.compare(
                    people[leftIndex],
                    people[rightIndex]
            ) <= 0) {
                buffer[bufferIndex++] = people[leftIndex++];
            } else {
                buffer[bufferIndex++] = people[rightIndex++];
            }
        }

        while (leftIndex <= middle) {
            buffer[bufferIndex++] = people[leftIndex++];
        }

        while (rightIndex <= right) {
            buffer[bufferIndex++] = people[rightIndex++];
        }

        for (int i = left; i <= right; i++) {
            people[i] = buffer[i];
        }
    }
}