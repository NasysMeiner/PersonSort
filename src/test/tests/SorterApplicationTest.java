package test.tests;

import model.Person;
import sorter.EvenOddSorterDecorator;
import sorter.MergeSort;
import sorter.UserSorter;
import test.Test;

import java.util.Comparator;

public class SorterApplicationTest extends Test {

    @Override
    public void run() {
        try {
            testNameSorter();
            testPasswordSorter();
            testMailSorter();
            testEvenLengthNameSorting();

            setSuccess();
            setMessage("All sorter tests passed");
        } catch (AssertionError error) {
            setFail();
            setMessage(error.getMessage());
        } catch (Exception error) {
            setFail();
            setMessage("Unexpected error: " + error.getMessage());
        }
    }

    private void testNameSorter() {
        Person[] people = createPeople();

        MergeSort sorter = new MergeSort(
                Comparator.comparing(Person::getName)
        );

        sorter.sort(people);

        assertEquals(
                "Anna",
                people[0].getName(),
                "MergeSort by name: first person"
        );

        assertEquals(
                "Ivan",
                people[1].getName(),
                "MergeSort by name: second person"
        );

        assertEquals(
                "Petr",
                people[2].getName(),
                "MergeSort by name: third person"
        );
    }

    private void testPasswordSorter() {
        Person[] people = createPeople();

        MergeSort sorter = new MergeSort(
                Comparator.comparing(Person::getPassword)
        );

        sorter.sort(people);

        assertEquals(
                "abc123",
                people[0].getPassword(),
                "MergeSort by password: first password"
        );

        assertEquals(
                "qwerty",
                people[1].getPassword(),
                "MergeSort by password: second password"
        );

        assertEquals(
                "zxc999",
                people[2].getPassword(),
                "MergeSort by password: third password"
        );
    }

    private void testMailSorter() {
        Person[] people = createPeople();

        MergeSort sorter = new MergeSort(
                Comparator.comparing(Person::getMail)
        );

        sorter.sort(people);

        assertEquals(
                "anna@mail.com",
                people[0].getMail(),
                "MergeSort by mail: first mail"
        );

        assertEquals(
                "ivan@mail.com",
                people[1].getMail(),
                "MergeSort by mail: second mail"
        );

        assertEquals(
                "petr@mail.com",
                people[2].getMail(),
                "MergeSort by mail: third mail"
        );
    }

    private void testEvenLengthNameSorting() {
        Person[] people = new Person[]{
                createPerson("Zoya", "zoya@mail.com", "pass001"),
                createPerson("Bob", "bob@mail.com", "pass002"),
                createPerson("Anna", "anna@mail.com", "pass003"),
                createPerson("Tom", "tom@mail.com", "pass004"),
                createPerson("Ivan", "ivan@mail.com", "pass005")
        };

        UserSorter sorter = new EvenOddSorterDecorator(
                new MergeSort(Comparator.comparing(Person::getName)),
                person -> person.getName() != null
                        && person.getName().length() % 2 == 0
        );

        sorter.sort(people);

        assertEquals(
                "Anna",
                people[0].getName(),
                "Even-length sorting: first selected position"
        );

        assertEquals(
                "Bob",
                people[1].getName(),
                "Even-length sorting: odd-length name must stay in place"
        );

        assertEquals(
                "Ivan",
                people[2].getName(),
                "Even-length sorting: second selected position"
        );

        assertEquals(
                "Tom",
                people[3].getName(),
                "Even-length sorting: odd-length name must stay in place"
        );

        assertEquals(
                "Zoya",
                people[4].getName(),
                "Even-length sorting: third selected position"
        );
    }

    private Person[] createPeople() {
        return new Person[]{
                createPerson(
                        "Ivan",
                        "ivan@mail.com",
                        "qwerty"
                ),
                createPerson(
                        "Petr",
                        "petr@mail.com",
                        "zxc999"
                ),
                createPerson(
                        "Anna",
                        "anna@mail.com",
                        "abc123"
                )
        };
    }

    private Person createPerson(
            String name,
            String mail,
            String password
    ) {
        return new Person.Builder()
                .setName(name)
                .setMail(mail)
                .setPassword(password)
                .build();
    }

    private void assertEquals(
            String expected,
            String actual,
            String testName
    ) {
        if (!expected.equals(actual)) {
            throw new AssertionError(
                    testName
                            + ". Expected: "
                            + expected
                            + ", actual: "
                            + actual
            );
        }
    }
}