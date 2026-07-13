package test.tests;

import model.Person;
import sorter.MergeSort;
import test.Test;

import java.util.Comparator;

public class SorterApplicationTest extends Test {

    @Override
    public void run() {
        try {
            testNameSorter();
            testPasswordSorter();
            testMailSorter();

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

    private Person[] createPeople() {
        return new Person[]{
                new Person.Builder()
                        .setName("Ivan")
                        .setMail("ivan@mail.com")
                        .setPassword("qwerty")
                        .build(),

                new Person.Builder()
                        .setName("Petr")
                        .setMail("petr@mail.com")
                        .setPassword("zxc999")
                        .build(),

                new Person.Builder()
                        .setName("Anna")
                        .setMail("anna@mail.com")
                        .setPassword("abc123")
                        .build()
        };
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