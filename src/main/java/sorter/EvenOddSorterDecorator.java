package sorter;

import model.Person;

public class EvenOddSorterDecorator implements UserSorter {

    private final UserSorter sorter;

    public EvenOddSorterDecorator(UserSorter sorter) {
        this.sorter = sorter;
    }

    @Override
    public Person[] sort(Person[] people) {
        if (people == null || people.length < 2) {
            return people;
        }

        Person[] evenPeople = new Person[(people.length + 1) / 2];
        Person[] oddPeople = new Person[people.length / 2];

        int evenIndex = 0;
        int oddIndex = 0;

        for (int i = 0; i < people.length; i++) {
            if (i % 2 == 0) {
                evenPeople[evenIndex++] = people[i];
            } else {
                oddPeople[oddIndex++] = people[i];
            }
        }

        evenPeople = sorter.sort(evenPeople);
        oddPeople = sorter.sort(oddPeople);

        evenIndex = 0;
        oddIndex = 0;

        for (int i = 0; i < people.length; i++) {
            if (i % 2 == 0) {
                people[i] = evenPeople[evenIndex++];
            } else {
                people[i] = oddPeople[oddIndex++];
            }
        }

        return people;
    }
}