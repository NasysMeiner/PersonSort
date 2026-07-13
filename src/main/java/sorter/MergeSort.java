package sorter;

import model.Person;

import java.util.Comparator;

public class MergeSort implements UserSorter {

    private final Comparator<Person> comparator;

    public MergeSort(Comparator<Person> comparator) {
        this.comparator = comparator;
    }

    @Override
    public Person[] sort(Person[] people) {
        if (people == null) {
            return null;
        }

        if (people.length < 2) {
            return people;
        }

        Person[] buffer = new Person[people.length];

        mergeSort(
                people,
                buffer,
                0,
                people.length - 1
        );

        return people;
    }

    private void mergeSort(
            Person[] people,
            Person[] buffer,
            int left,
            int right
    ) {
        if (left >= right) {
            return;
        }

        int middle = left + (right - left) / 2;

        mergeSort(people, buffer, left, middle);
        mergeSort(people, buffer, middle + 1, right);

        merge(people, buffer, left, middle, right);
    }

    private void merge(
            Person[] people,
            Person[] buffer,
            int left,
            int middle,
            int right
    ) {
        int leftIndex = left;
        int rightIndex = middle + 1;
        int bufferIndex = left;

        while (leftIndex <= middle && rightIndex <= right) {
            if (comparator.compare(
                    people[leftIndex],
                    people[rightIndex]
            ) <= 0) {
                buffer[bufferIndex] = people[leftIndex];
                leftIndex++;
            } else {
                buffer[bufferIndex] = people[rightIndex];
                rightIndex++;
            }

            bufferIndex++;
        }

        while (leftIndex <= middle) {
            buffer[bufferIndex] = people[leftIndex];
            leftIndex++;
            bufferIndex++;
        }

        while (rightIndex <= right) {
            buffer[bufferIndex] = people[rightIndex];
            rightIndex++;
            bufferIndex++;
        }

        for (int i = left; i <= right; i++) {
            people[i] = buffer[i];
        }
    }
}