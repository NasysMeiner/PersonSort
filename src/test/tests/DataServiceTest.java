package test.tests;

import model.DataBase;
import model.DataBaseService;
import model.Person;

import java.util.concurrent.ExecutionException;

public class DataServiceTest {
    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static void main(String[] args) throws Exception {
        System.out.println("=== Запуск самописных тестов DataBaseService ===\n");

        testGetSizeEmpty();
        testAddPerson();
        testAddAllPerson();
        testGetAll();
        testGetByIdFound();
        testGetByIdNotFound();
        testUpdate();
        testDeletePersonById();
        testGetIndexByNameFound();
        testGetIndexByNameNotFound();
        testCountOccurrencesSingleMatch();
        testCountOccurrencesNoMatches();
        testCountOccurrencesEmptyDatabase();
        testAddPersonOverload();

        System.out.println("\n=== Итого: " + testsPassed + " пройдено, " + testsFailed + " провалено ===");
        if (testsFailed > 0) {
            System.exit(1);
        } else {
            System.exit(0);
        }
    }

    // Вспомогательные методы для проверок
    private static void assertTrue(boolean condition, String message) {
        if (condition) {
            logPass(message);
            testsPassed++;
        } else {
            logFail(message);
            testsFailed++;
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        boolean ok = (expected == null && actual == null) || (expected != null && expected.equals(actual));
        if (ok) {
            logPass(message + " (expected=" + expected + ", actual=" + actual + ")");
            testsPassed++;
        } else {
            logFail(message + " (expected=" + expected + ", actual=" + actual + ")");
            testsFailed++;
        }
    }

    private static void logPass(String msg) { System.out.println("[PASS] " + msg); }
    private static void logFail(String msg) { System.out.println("[FAIL] " + msg); }

    // --- Тесты ---

    private static void testGetSizeEmpty() {
        DataBase db = new DataBase();
        DataBaseService service = new DataBaseService(db);
        assertEquals(0, service.getSize(), "getSize на пустой базе должен вернуть 0");
    }

    private static void testAddPerson() {
        DataBase db = new DataBase();
        DataBaseService service = new DataBaseService(db);

        Person added = service.addPerson("Carol", "carol@example.com", "secret3DS");
        assertTrue(added != null, "addPerson должен вернуть не-null объект");
        assertEquals("Carol", added.getName(), "Имя добавленного человека должно быть Carol");
        assertEquals(1, service.getSize(), "После добавления одного человека размер должен быть 1");

        Person[] all = service.getAll();
        assertEquals(1, all.length, "getAll должен вернуть массив длины 1");
        assertEquals("Carol", all[0].getName(), "Единственный элемент должен быть Carol");
    }

    private static void testAddAllPerson() {
        DataBase db = new DataBase();
        DataBaseService service = new DataBaseService(db);

        Person p1 = new Person.Builder().setName("Alice").setMail("a@x.com").setPassword("pass125DAS478").build();
        Person p2 = new Person.Builder().setName("Bob").setMail("b@x.com").setPassword("pass125DAS478").build();
        Person[] input = {p1, p2};

        Person[] result = service.AddAllPerson(input);
        assertEquals(input, result, "AddAllPerson должен вернуть тот же массив, что и передан");
        assertEquals(2, service.getSize(), "Размер должен стать 2 после добавления двух человек");

        Person[] all = service.getAll();
        assertEquals(2, all.length, "getAll должен вернуть 2 элемента");
        assertEquals("Alice", all[0].getName(), "Первый элемент должен быть Alice");
        assertEquals("Bob", all[1].getName(), "Второй элемент должен быть Bob");
    }

    private static void testGetAll() {
        DataBase db = new DataBase();
        DataBaseService service = new DataBaseService(db);

        Person p1 = new Person.Builder().setName("Alice").setMail("a@x.com").setPassword("pass125DAS478").build();
        Person p2 = new Person.Builder().setName("Bob").setMail("b@x.com").setPassword("pass125DAS478").build();
        service.addPerson(p1);
        service.addPerson(p2);

        Person[] all = service.getAll();
        assertEquals(2, all.length, "getAll должен вернуть 2 элемента");
        assertEquals("Alice", all[0].getName(), "Первый элемент — Alice");
        assertEquals("Bob", all[1].getName(), "Второй элемент — Bob");
    }

    private static void testGetByIdFound() {
        DataBase db = new DataBase();
        DataBaseService service = new DataBaseService(db);

        Person p = new Person.Builder().setName("Alice").setMail("a@x.com").setPassword("pass125DAS478").build();
        service.addPerson(p);

            Person found = service.getById(1L);
            assertTrue(found != null, "getById должен вернуть не-null при найденном ID");
            assertEquals(1L, found.getId(), "ID найденного человека должен быть 1L");
            assertEquals("Alice", found.getName(), "Имя должно быть Alice");

    }

    private static void testGetByIdNotFound() {
        DataBase db = new DataBase();
        DataBaseService service = new DataBaseService(db);

        Person p = new Person.Builder().setName("Alice").setMail("a@x.com").setPassword("pass125DAS478").build();
        service.addPerson(p);

        Person notFound = service.getById(999L);
        assertTrue(notFound == null, "При несуществующем ID должен возвращаться null");
    }

    private static void testUpdate() {
        DataBase db = new DataBase();
        DataBaseService service = new DataBaseService(db);

        Person original = new Person.Builder().setName("Alice").setMail("alice@old.com").setPassword("pass125DAS478").build();
        service.addPerson(original);

        Person updateData = new Person.Builder()
                .setName("Alice Updated")
                .setMail("alice.new@example.com")
                .setPassword("pass125DAS478")
                .build();

        Person updated = service.update(1L, updateData);
        assertTrue(updated != null, "update должен вернуть не-null");
        assertEquals("Alice Updated", updated.getName(), "Имя после update должно быть Alice Updated");

        Person fromDb = service.getById(1L);
        assertEquals("Alice Updated", fromDb.getName(), "В базе тоже должно быть обновлённое имя");
    }

    private static void testDeletePersonById() {
        DataBase db = new DataBase();
        DataBaseService service = new DataBaseService(db);

        Person p1 = new Person.Builder().setName("Alice").setMail("a@x.com").setPassword("pass125DAS478").build();
        Person p2 = new Person.Builder().setName("Bob").setMail("b@x.com").setPassword("pass125DAS478").build();
        service.addPerson(p1);
        service.addPerson(p2);

        Person deleted = service.deletePersonById(1L);
        assertTrue(deleted != null, "deletePersonById должен вернуть удалённого человека");
        assertEquals(1L, deleted.getId(), "Удалённый человек должен иметь ID 1L");

        assertEquals(1, service.getSize(), "Размер после удаления одного из двух должен стать 1");
        Person[] remaining = service.getAll();
        assertEquals(1, remaining.length, "В getAll должен остаться 1 элемент");
        assertEquals(2L, remaining[0].getId(), "Оставшийся элемент должен иметь ID 2L");
    }

    private static void testGetIndexByNameFound() {
        DataBase db = new DataBase();
        DataBaseService service = new DataBaseService(db);

        Person p1 = new Person.Builder().setName("Alice").setMail("a@x.com").setPassword("pass125DAS478").build();
        Person p2 = new Person.Builder().setName("Bob").setMail("b@x.com").setPassword("pass125DAS478").build();
        service.addPerson(p1);
        service.addPerson(p2);

        int index = service.getIndexByName("Alice");
        assertEquals(0, index, "Индекс Alice должен быть 0");
    }

    private static void testGetIndexByNameNotFound() {
        DataBase db = new DataBase();
        DataBaseService service = new DataBaseService(db);

        Person p1 = new Person.Builder().setName("Alice").setMail("a@x.com").setPassword("pass125DAS478").build();
        service.addPerson(p1);

        int index = service.getIndexByName("Charlie");
        // Подстрой под свою реализацию: -1 или другое значение при отсутствии
        assertEquals(-1, index, "При отсутствии имени индекс должен быть -1");
    }

    private static void testCountOccurrencesSingleMatch() throws InterruptedException, ExecutionException {
        DataBase db = new DataBase();
        DataBaseService service = new DataBaseService(db);

        service.addPerson(new Person.Builder().setName("Alice").setMail("a@x.com").setPassword("pass125DAS478").build());
        service.addPerson(new Person.Builder().setName("Bob").setMail("b@x.com").setPassword("pass125DAS478").build());
        service.addPerson(new Person.Builder().setName("Alice").setMail("aa@x.com").setPassword("pass125DAS478").build());

        long count = service.countOccurrences("Alice");
        assertEquals(2, count, "countOccurrences(\"Alice\") должен вернуть 2");
    }

    private static void testCountOccurrencesNoMatches() throws InterruptedException, ExecutionException {
        DataBase db = new DataBase();
        DataBaseService service = new DataBaseService(db);

        Person p1 = new Person.Builder().setName("Alice").setMail("a@x.com").setPassword("pass125DAS478").build();
        Person p2 = new Person.Builder().setName("Bob").setMail("b@x.com").setPassword("pass125DAS478").build();
        service.addPerson(p1);
        service.addPerson(p2);

        long count = service.countOccurrences("Charlie");
        assertEquals(0, count, "countOccurrences для несуществующего имени должен вернуть 0");
    }

    private static void testCountOccurrencesEmptyDatabase() throws InterruptedException, ExecutionException {
        DataBase db = new DataBase();
        DataBaseService service = new DataBaseService(db);

        long count = service.countOccurrences("Alice");
        assertEquals(0, count, "countOccurrences в пустой базе должен вернуть 0");
    }

    private static void testAddPersonOverload() {
        DataBase db = new DataBase();
        DataBaseService service = new DataBaseService(db);

        Person p = new Person.Builder().setName("Alice").setMail("a@x.com").setPassword("pass125DAS478").build();
        service.addPerson(p);

        assertEquals(1, service.getSize(), "Размер должен быть 1 после addPerson(Person)");
        Person[] all = service.getAll();
        assertEquals(1, all.length, "getAll должен вернуть 1 элемент");
        assertEquals(p, all[0], "Элемент должен совпадать с добавленным");
    }
}
