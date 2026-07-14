package test.tests;

import model.Person;
import test.Test;

public class PersonBuilderTest extends Test {
    private int passed = 0;
    private int failed = 0;
    private StringBuilder result = new StringBuilder();

    @Override
    public void run() {
        try {
            testValidPerson();
            testNameValidation();
            testEmailValidation();
            testPasswordValidation();

            if (failed == 0) {
                setSuccess();
                setMessage("Все " + (passed + failed) + " тестов пройдены!\n" + result);
            } else {
                setFail();
                setMessage("Провалено " + failed + " из " + (passed + failed) + " тестов\n" + result);
            }
        } catch (Exception e) {
            setFail();
            setMessage("Ошибка: " + e.getMessage());
        }
    }

    private void assertTrue(boolean condition, String msg) {
        if (condition) {
            passed++;
            result.append("  ✅ ").append(msg).append("\n");
        } else {
            failed++;
            result.append("  ❌ ").append(msg).append("\n");
        }
    }

    private void assertEquals(Object expected, Object actual, String msg) {
        assertTrue((expected == null && actual == null) || 
                   (expected != null && expected.equals(actual)), 
                   msg + " (expected=" + expected + ", actual=" + actual + ")");
    }

    private void assertThrows(Runnable action, String msg) {
        try {
            action.run();
            failed++;
            result.append("  ❌ ").append(msg).append(" - исключение НЕ выброшено\n");
        } catch (Exception e) {
            passed++;
            result.append("  ✅ ").append(msg).append(" - ").append(e.getClass().getSimpleName()).append("\n");
        }
    }

    private void testValidPerson() {
        result.append("\n▶ testValidPerson:\n");
        Person p = new Person.Builder()
                .setName("Иван Петров")
                .setMail("ivan@example.com")
                .setPassword("password123")
                .build();
        assertNotNull(p, "Person создан");
        assertEquals("Иван Петров", p.getName(), "Имя");
        assertEquals("ivan@example.com", p.getMail(), "Email");
        assertEquals("password123", p.getPassword(), "Пароль");
    }

    private void assertNotNull(Object obj, String msg) {
        assertTrue(obj != null, msg);
    }

    private void testNameValidation() {
        result.append("\n▶ testNameValidation:\n");
        assertThrows(() -> new Person.Builder()
                .setName("").setMail("test@example.com").setPassword("pass123").build(),
                "Пустое имя");
        assertThrows(() -> new Person.Builder()
                .setName(null).setMail("test@example.com").setPassword("pass123").build(),
                "null имя");
        assertThrows(() -> new Person.Builder()
                .setName("John123").setMail("test@example.com").setPassword("pass123").build(),
                "Имя с цифрами");
        assertThrows(() -> new Person.Builder()
                .setName("John@Doe").setMail("test@example.com").setPassword("pass123").build(),
                "Имя со спецсимволами");
        
        // Валидные имена
        try {
            new Person.Builder().setName("Иван").setMail("test@example.com").setPassword("pass123").build();
            assertTrue(true, "Имя 'Иван' валидно");
            new Person.Builder().setName("Anna-Maria").setMail("test@example.com").setPassword("pass123").build();
            assertTrue(true, "Имя 'Anna-Maria' валидно");
        } catch (Exception e) {
            assertTrue(false, "Валидное имя не должно падать: " + e.getMessage());
        }
    }

    private void testEmailValidation() {
        result.append("\n▶ testEmailValidation:\n");
        assertThrows(() -> new Person.Builder()
                .setName("John").setMail("").setPassword("pass123").build(),
                "Пустой email");
        assertThrows(() -> new Person.Builder()
                .setName("John").setMail(null).setPassword("pass123").build(),
                "null email");
        assertThrows(() -> new Person.Builder()
                .setName("John").setMail("testexample.com").setPassword("pass123").build(),
                "Email без @");
        assertThrows(() -> new Person.Builder()
                .setName("John").setMail("test@").setPassword("pass123").build(),
                "Email без домена");
        assertThrows(() -> new Person.Builder()
                .setName("John").setMail("@example.com").setPassword("pass123").build(),
                "Email без имени");
        
        // Валидные email
        try {
            new Person.Builder().setName("John").setMail("john@example.com").setPassword("pass123").build();
            assertTrue(true, "Email 'john@example.com' валиден");
            new Person.Builder().setName("John").setMail("john.doe@example.co.uk").setPassword("pass123").build();
            assertTrue(true, "Email 'john.doe@example.co.uk' валиден");
        } catch (Exception e) {
            assertTrue(false, "Валидный email не должен падать: " + e.getMessage());
        }
    }

    private void testPasswordValidation() {
        result.append("\n▶ testPasswordValidation:\n");
        assertThrows(() -> new Person.Builder()
                .setName("John").setMail("test@example.com").setPassword("12345").build(),
                "Пароль < 6 символов");
        assertThrows(() -> new Person.Builder()
                .setName("John").setMail("test@example.com").setPassword("").build(),
                "Пустой пароль");
        assertThrows(() -> new Person.Builder()
                .setName("John").setMail("test@example.com").setPassword(null).build(),
                "null пароль");
        
        // Валидные пароли
        try {
            new Person.Builder().setName("John").setMail("test@example.com").setPassword("123456").build();
            assertTrue(true, "Пароль '123456' валиден");
            new Person.Builder().setName("John").setMail("test@example.com").setPassword("SuperSecret123").build();
            assertTrue(true, "Длинный пароль валиден");
        } catch (Exception e) {
            assertTrue(false, "Валидный пароль не должен падать: " + e.getMessage());
        }
    }
}