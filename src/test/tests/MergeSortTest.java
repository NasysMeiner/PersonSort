package test.tests;

import model.Person;
import sorter.MergeSort;
import test.Test;

import java.util.Comparator;

public class MergeSortTest extends Test {

    @Override
    public void run() {
        try {
            testSortByName();
            testSortByMail();
            testSortByPassword();
            testSingleElementArray();
            testEmptyArray();
            testNullArray();

            setSuccess();
            setMessage("All MergeSort tests passed");
        } catch (AssertionError error) {
            setFail();
            setMessage(error.getMessage());
        } catch (Exception error) {
            setFail();
            setMessage("Unexpected error: " + error.getMessage());
        }
    }

    private void testSortByName() {
        Person[] people = createPeople();

        MergeSort sorter = new MergeSort(
                Comparator.comparing(Person::getName)
        );

        sorter.sort(people);

        assertEquals(
                "Anna",
                people[0].getName(),
                "Sort by name: first element"
        );

        assertEquals(
                "Ivan",
                people[1].getName(),
                "Sort by name: second element"
        );

        assertEquals(
                "Petr",
                people[2].getName(),
                "Sort by name: third element"
        );
    }

    private void testSortByMail() {
        Person[] people = createPeople();

        MergeSort sorter = new MergeSort(
                Comparator.comparing(Person::getMail)
        );

        sorter.sort(people);

        assertEquals(
                "anna@mail.com",
                people[0].getMail(),
                "Sort by mail: first element"
        );

        assertEquals(
                "ivan@mail.com",
                people[1].getMail(),
                "Sort by mail: second element"
        );

        assertEquals(
                "petr@mail.com",
                people[2].getMail(),
                "Sort by mail: third element"
        );
    }

    private void testSortByPassword() {
        Person[] people = createPeople();

        MergeSort sorter = new MergeSort(
                Comparator.comparing(Person::getPassword)
        );

        sorter.sort(people);

        assertEquals(
                "abc123",
                people[0].getPassword(),
                "Sort by password: first element"
        );

        assertEquals(
                "qwerty",
                people[1].getPassword(),
                "Sort by password: second element"
        );

        assertEquals(
                "zxc999",
                people[2].getPassword(),
                "Sort by password: third element"
        );
    }

    private void testSingleElementArray() {
        Person person = createPerson(
                "Anna",
                "anna@mail.com",
                "abc123"
        );

        Person[] people = {person};

        MergeSort sorter = new MergeSort(
                Comparator.comparing(Person::getName)
        );

        Person[] result = sorter.sort(people);

        assertSame(
                people,
                result,
                "Single-element array must be returned"
        );

        assertSame(
                person,
                result[0],
                "Single element must not change"
        );
    }

    private void testEmptyArray() {
        Person[] people = new Person[0];

        MergeSort sorter = new MergeSort(
                Comparator.comparing(Person::getName)
        );

        Person[] result = sorter.sort(people);

        assertSame(
                people,
                result,
                "Empty array must be returned"
        );
    }

    private void testNullArray() {
        MergeSort sorter = new MergeSort(
                Comparator.comparing(Person::getName)
        );

        Person[] result = sorter.sort(null);

        if (result != null) {
            throw new AssertionError(
                    "Null array: expected null"
            );
        }
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

    private void assertSame(
            Object expected,
            Object actual,
            String testName
    ) {
        if (expected != actual) {
            throw new AssertionError(
                    testName
                            + ". Expected the same object"
            );
        }
    }
}