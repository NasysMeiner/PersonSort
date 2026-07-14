package sorter;

import model.Person;

import java.util.function.Predicate;

public class EvenOddSorterDecorator implements UserSorter {

    private final UserSorter sorter;
    private final Predicate<Person> condition;

    public EvenOddSorterDecorator(
            UserSorter sorter,
            Predicate<Person> condition
    ) {
        this.sorter = sorter;
        this.condition = condition;
    }

    @Override
    public Person[] sort(Person[] people) {
        if (people == null || people.length < 2) {
            return people;
        }

        int selectedCount = 0;

        for (Person person : people) {
            if (person != null && condition.test(person)) {
                selectedCount++;
            }
        }

        if (selectedCount < 2) {
            return people;
        }

        Person[] selectedPeople = new Person[selectedCount];
        int[] selectedIndexes = new int[selectedCount];

        int selectedIndex = 0;

        for (int i = 0; i < people.length; i++) {
            Person person = people[i];

            if (person != null && condition.test(person)) {
                selectedPeople[selectedIndex] = person;
                selectedIndexes[selectedIndex] = i;
                selectedIndex++;
            }
        }

        sorter.sort(selectedPeople);

        for (int i = 0; i < selectedCount; i++) {
            people[selectedIndexes[i]] = selectedPeople[i];
        }

        return people;
    }
}