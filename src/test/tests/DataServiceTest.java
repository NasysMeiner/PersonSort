package test.tests;

import model.DataBase;
import model.DataBaseService;
import model.Person;
import test.Test;

public class DataServiceTest extends Test {
    private int subTestsPassed = 0;
    private int subTestsFailed = 0;
    private StringBuilder results = new StringBuilder();

    @Override
    public void run() {
        try {
            testGetSizeEmpty();
            testAddPerson();
            testAddAllPerson();
            testGetAll();
            testAddPersonOverload();

            if (subTestsFailed == 0) {
                setSuccess();
                setMessage("Все " + (subTestsPassed + subTestsFailed) + " подтестов пройдены успешно!\n" + results.toString());
            } else {
                setFail();
                setMessage("Провалено " + subTestsFailed + " из " + (subTestsPassed + subTestsFailed) + " подтестов\n" + results.toString());
            }

        } catch (Exception e) {
            setFail();
            setMessage("Неожиданное исключение: " + e.getMessage());
        }
    }

    private void assertTrue(boolean condition, String message) {
        if (condition) {
            subTestsPassed++;
            results.append("  ✅ ").append(message).append("\n");
        } else {
            subTestsFailed++;
            results.append("  ❌ ").append(message).append("\n");
        }
    }

    private void assertEquals(Object expected, Object actual, String message) {
        boolean ok = (expected == null && actual == null) || 
                     (expected != null && expected.equals(actual));
        if (ok) {
            subTestsPassed++;
            results.append("  ✅ ").append(message)
                   .append(" (expected=").append(expected)
                   .append(", actual=").append(actual).append(")\n");
        } else {
            subTestsFailed++;
            results.append("  ❌ ").append(message)
                   .append(" (expected=").append(expected)
                   .append(", actual=").append(actual).append(")\n");
        }
    }

    private void assertNotNull(Object obj, String message) {
        assertTrue(obj != null, message);
    }

    // === Тесты ===

    private void testGetSizeEmpty() {
        results.append("\n▶ testGetSizeEmpty:\n");
        DataBase db = new DataBase();
        DataBaseService service = new DataBaseService(db);
        assertEquals(0, service.getSize(), "getSize на пустой базе должен вернуть 0");
    }

    private void testAddPerson() {
        results.append("\n▶ testAddPerson:\n");
        DataBase db = new DataBase();
        DataBaseService service = new DataBaseService(db);

        Person added = service.addPerson("Carol", "carol@example.com", "secret3DS");
        assertNotNull(added, "addPerson должен вернуть не-null объект");
        assertEquals("Carol", added.getName(), "Имя добавленного человека должно быть Carol");
        assertEquals(1, service.getSize(), "После добавления одного человека размер должен быть 1");

        Person[] all = service.getAll();
        assertEquals(1, all.length, "getAll должен вернуть массив длины 1");
        assertEquals("Carol", all[0].getName(), "Единственный элемент должен быть Carol");
    }

    private void testAddAllPerson() {
        results.append("\n▶ testAddAllPerson:\n");
        DataBase db = new DataBase();
        DataBaseService service = new DataBaseService(db);

        Person p1 = new Person.Builder()
                .setName("Alice")
                .setMail("a@x.com")
                .setPassword("pass125DAS478")
                .build();
        Person p2 = new Person.Builder()
                .setName("Bob")
                .setMail("b@x.com")
                .setPassword("pass125DAS478")
                .build();
        Person[] input = {p1, p2};

        Person[] result = service.AddAllPerson(input);
        assertEquals(input, result, "AddAllPerson должен вернуть тот же массив, что и передан");
        assertEquals(2, service.getSize(), "Размер должен стать 2 после добавления двух человек");

        Person[] all = service.getAll();
        assertEquals(2, all.length, "getAll должен вернуть 2 элемента");
        assertEquals("Alice", all[0].getName(), "Первый элемент должен быть Alice");
        assertEquals("Bob", all[1].getName(), "Второй элемент должен быть Bob");
    }

    private void testGetAll() {
        results.append("\n▶ testGetAll:\n");
        DataBase db = new DataBase();
        DataBaseService service = new DataBaseService(db);

        Person p1 = new Person.Builder()
                .setName("Alice")
                .setMail("a@x.com")
                .setPassword("pass125DAS478")
                .build();
        Person p2 = new Person.Builder()
                .setName("Bob")
                .setMail("b@x.com")
                .setPassword("pass125DAS478")
                .build();
        service.addPerson(p1);
        service.addPerson(p2);

        Person[] all = service.getAll();
        assertEquals(2, all.length, "getAll должен вернуть 2 элемента");
        assertEquals("Alice", all[0].getName(), "Первый элемент — Alice");
        assertEquals("Bob", all[1].getName(), "Второй элемент — Bob");
        
        assertNotNull(all[0], "Первый элемент не должен быть null");
        assertNotNull(all[1], "Второй элемент не должен быть null");
    }

    private void testAddPersonOverload() {
        results.append("\n▶ testAddPersonOverload:\n");
        DataBase db = new DataBase();
        DataBaseService service = new DataBaseService(db);

        Person p = new Person.Builder()
                .setName("Alice")
                .setMail("a@x.com")
                .setPassword("pass125DAS478")
                .build();
        service.addPerson(p);

        assertEquals(1, service.getSize(), "Размер должен быть 1 после addPerson(Person)");
        
        Person[] all = service.getAll();
        assertEquals(1, all.length, "getAll должен вернуть 1 элемент");
        assertEquals(p, all[0], "Элемент должен совпадать с добавленным");
        assertEquals("Alice", all[0].getName(), "Имя должно быть Alice");
        assertEquals("a@x.com", all[0].getMail(), "Email должен быть a@x.com");
        assertEquals("pass125DAS478", all[0].getPassword(), "Пароль должен совпадать");
    }
}