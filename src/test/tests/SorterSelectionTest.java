package test.tests;

import model.Person;
import sorter.SorterSelection;
import sorter.UserSorter;
import test.Test;

public class SorterSelectionTest extends Test {

    @Override
    public void run() {
        try {
            testNameSorterSelection();
            testPasswordSorterSelection();
            testMailSorterSelection();

            setSuccess();
            setMessage("All SorterSelection tests passed");
        } catch (AssertionError error) {
            setFail();
            setMessage(error.getMessage());
        } catch (Exception error) {
            setFail();
            setMessage("Unexpected error: " + error.getMessage());
        }
    }

    private void testNameSorterSelection() {
        RecordingSorter nameSorter = new RecordingSorter();
        RecordingSorter passwordSorter = new RecordingSorter();
        RecordingSorter mailSorter = new RecordingSorter();

        SorterSelection selection = new SorterSelection(
                nameSorter,
                passwordSorter,
                mailSorter
        );

        Person[] people = createPeople();
        Person[] result = selection.sortCollectionToName(people);

        assertTrue(
                nameSorter.wasCalled,
                "Name sorter must be called"
        );

        assertFalse(
                passwordSorter.wasCalled,
                "Password sorter must not be called"
        );

        assertFalse(
                mailSorter.wasCalled,
                "Mail sorter must not be called"
        );

        assertSame(
                people,
                result,
                "Name sorting must return sorter result"
        );
    }

    private void testPasswordSorterSelection() {
        RecordingSorter nameSorter = new RecordingSorter();
        RecordingSorter passwordSorter = new RecordingSorter();
        RecordingSorter mailSorter = new RecordingSorter();

        SorterSelection selection = new SorterSelection(
                nameSorter,
                passwordSorter,
                mailSorter
        );

        Person[] people = createPeople();
        Person[] result = selection.sortCollectionToPassword(people);

        assertFalse(
                nameSorter.wasCalled,
                "Name sorter must not be called"
        );

        assertTrue(
                passwordSorter.wasCalled,
                "Password sorter must be called"
        );

        assertFalse(
                mailSorter.wasCalled,
                "Mail sorter must not be called"
        );

        assertSame(
                people,
                result,
                "Password sorting must return sorter result"
        );
    }

    private void testMailSorterSelection() {
        RecordingSorter nameSorter = new RecordingSorter();
        RecordingSorter passwordSorter = new RecordingSorter();
        RecordingSorter mailSorter = new RecordingSorter();

        SorterSelection selection = new SorterSelection(
                nameSorter,
                passwordSorter,
                mailSorter
        );

        Person[] people = createPeople();
        Person[] result = selection.sortCollectionToMail(people);

        assertFalse(
                nameSorter.wasCalled,
                "Name sorter must not be called"
        );

        assertFalse(
                passwordSorter.wasCalled,
                "Password sorter must not be called"
        );

        assertTrue(
                mailSorter.wasCalled,
                "Mail sorter must be called"
        );

        assertSame(
                people,
                result,
                "Mail sorting must return sorter result"
        );
    }

    private Person[] createPeople() {
        return new Person[]{
                new Person.Builder()
                        .setName("Anna")
                        .setMail("anna@mail.com")
                        .setPassword("pass001")
                        .build()
        };
    }

    private void assertTrue(
            boolean condition,
            String testName
    ) {
        if (!condition) {
            throw new AssertionError(testName);
        }
    }

    private void assertFalse(
            boolean condition,
            String testName
    ) {
        if (condition) {
            throw new AssertionError(testName);
        }
    }

    private void assertSame(
            Object expected,
            Object actual,
            String testName
    ) {
        if (expected != actual) {
            throw new AssertionError(
                    testName + ". Expected the same object"
            );
        }
    }

    private static class RecordingSorter implements UserSorter {

        private boolean wasCalled;

        @Override
        public Person[] sort(Person[] people) {
            wasCalled = true;
            return people;
        }
    }
}