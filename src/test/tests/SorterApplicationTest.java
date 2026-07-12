package test.tests;

import model.Person;
import sorter.MailSorter;
import sorter.NameSorter;
import sorter.PasswordSorter;
import test.Test;

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

        new NameSorter().sort(people);

        assertEquals("Anna", people[0].getName(), "NameSorter: first person");
        assertEquals("Ivan", people[1].getName(), "NameSorter: second person");
        assertEquals("Petr", people[2].getName(), "NameSorter: third person");
    }

    private void testPasswordSorter() {
        Person[] people = createPeople();

        new PasswordSorter().sort(people);

        assertEquals("abc123", people[0].getPassword(),
                "PasswordSorter: first password");
        assertEquals("qwerty", people[1].getPassword(),
                "PasswordSorter: second password");
        assertEquals("zxc999", people[2].getPassword(),
                "PasswordSorter: third password");
    }

    private void testMailSorter() {
        Person[] people = createPeople();

        new MailSorter().sort(people);

        assertEquals("anna@mail.com", people[0].getMail(),
                "MailSorter: first mail");
        assertEquals("ivan@mail.com", people[1].getMail(),
                "MailSorter: second mail");
        assertEquals("petr@mail.com", people[2].getMail(),
                "MailSorter: third mail");
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