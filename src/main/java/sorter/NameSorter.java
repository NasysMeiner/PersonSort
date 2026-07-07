package sorter;

import model.Person;

public class NameSorter implements UserSorter {

    @Override
    public void sort(Person[] people) {
        if (people == null || people.length < 2) {
            return;
        }

        for (int i = 0; i < people.length - 1; i++) {
            for (int j = 0; j < people.length - 1 - i; j++) {
                if (people[j].getName().compareToIgnoreCase(people[j + 1].getName()) > 0) {
                    Person temp = people[j];
                    people[j] = people[j + 1];
                    people[j + 1] = temp;
                }
            }
        }
    }
}