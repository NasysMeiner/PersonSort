package test.tests;

import model.Person;
import sorter.EvenOddSorterDecorator;
import sorter.MergeSort;
import sorter.UserSorter;
import test.Test;

import java.util.Comparator;

public class EvenOddSorterDecoratorTest extends Test {

    @Override
    public void run() {
        try {
            testSortsOnlyEvenLengthNames();
            testOddLengthNamesStayInPlace();
            testLessThanTwoMatchingElements();
            testSingleElementArray();
            testEmptyArray();
            testNullArray();

            setSuccess();
            setMessage("All EvenOddSorterDecorator tests passed");
        } catch (AssertionError error) {
            setFail();
            setMessage(error.getMessage());
        } catch (Exception error) {
            setFail();
            setMessage("Unexpected error: " + error.getMessage());
        }
    }

    private void testSortsOnlyEvenLengthNames() {
        Person[] people = createMixedPeople();

        UserSorter sorter = createEvenNameLengthSorter();
        sorter.sort(people);

        assertEquals(
                "Anna",
                people[0].getName(),
                "First even-length name"
        );

        assertEquals(
                "Ivan",
                people[2].getName(),
                "Second even-length name"
        );

        assertEquals(
                "Zoya",
                people[4].getName(),
                "Third even-length name"
        );
    }

    private void testOddLengthNamesStayInPlace() {
        Person[] people = createMixedPeople();

        Person bob = people[1];
        Person tom = people[3];

        UserSorter sorter = createEvenNameLengthSorter();
        sorter.sort(people);

        assertSame(
                bob,
                people[1],
                "Odd-length name at index 1 must stay in place"
        );

        assertSame(
                tom,
                people[3],
                "Odd-length name at index 3 must stay in place"
        );
    }

    private void testLessThanTwoMatchingElements() {
        Person first = createPerson(
                "Bob",
                "bob@mail.com",
                "pass001"
        );

        Person second = createPerson(
                "Anna",
                "anna@mail.com",
                "pass002"
        );

        Person third = createPerson(
                "Tom",
                "tom@mail.com",
                "pass003"
        );

        Person[] people = {
                first,
                second,
                third
        };

        UserSorter sorter = createEvenNameLengthSorter();
        Person[] result = sorter.sort(people);

        assertSame(
                people,
                result,
                "Array reference must stay the same"
        );

        assertSame(
                first,
                result[0],
                "First element must stay in place"
        );

        assertSame(
                second,
                result[1],
                "Only matching element must stay in place"
        );

        assertSame(
                third,
                result[2],
                "Third element must stay in place"
        );
    }

    private void testSingleElementArray() {
        Person person = createPerson(
                "Anna",
                "anna@mail.com",
                "pass001"
        );

        Person[] people = {person};

        UserSorter sorter = createEvenNameLengthSorter();
        Person[] result = sorter.sort(people);

        assertSame(
                people,
                result,
                "Single-element array must be returned"
        );

        assertSame(
                person,
                result[0],
                "Single element must stay unchanged"
        );
    }

    private void testEmptyArray() {
        Person[] people = new Person[0];

        UserSorter sorter = createEvenNameLengthSorter();
        Person[] result = sorter.sort(people);

        assertSame(
                people,
                result,
                "Empty array must be returned"
        );
    }

    private void testNullArray() {
        UserSorter sorter = createEvenNameLengthSorter();
        Person[] result = sorter.sort(null);

        if (result != null) {
            throw new AssertionError(
                    "Null array: expected null"
            );
        }
    }

    private UserSorter createEvenNameLengthSorter() {
        return new EvenOddSorterDecorator(
                new MergeSort(
                        Comparator.comparing(Person::getName)
                ),
                person -> person.getName() != null
                        && person.getName().length() % 2 == 0
        );
    }

    private Person[] createMixedPeople() {
        return new Person[]{
                createPerson(
                        "Zoya",
                        "zoya@mail.com",
                        "pass001"
                ),
                createPerson(
                        "Bob",
                        "bob@mail.com",
                        "pass002"
                ),
                createPerson(
                        "Anna",
                        "anna@mail.com",
                        "pass003"
                ),
                createPerson(
                        "Tom",
                        "tom@mail.com",
                        "pass004"
                ),
                createPerson(
                        "Ivan",
                        "ivan@mail.com",
                        "pass005"
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