package test.tests;

import model.DataBase;
import model.Person;
import java.util.NoSuchElementException;

public class DataBaseTest {
    public static void main(String[] args) {
        boolean passed = true;
        DataBase db = new DataBase();

        // Добавляем больше, чем начальный размер (10), чтобы проверить resize
        for (int i = 0; i < 15; i++) {
            Person p = new Person.Builder()
                    .setName("User" + i)
                    .setMail("user" + i + "@test.com")
                    .setPassword("pass" + i)
                    .build();
            db.add(p);
        }

        if (db.getSize() != 15) {
            System.out.println("FAIL: размер должен быть 15, а равен " + db.getSize());
            passed = false;
        } else {
            System.out.println("OK: размер равен 15");
        }

        // Проверяем, что элементы на своих местах
        Person p0 = db.get(0);
        if (!p0.getName().equals("User0")) {
            System.out.println("FAIL: первый элемент должен быть User0");
            passed = false;
        } else {
            System.out.println("OK: первый элемент User0");
        }

        Person p14 = db.get(14);
        if (!p14.getName().equals("User14")) {
            System.out.println("FAIL: последний элемент должен быть User14");
            passed = false;
        } else {
            System.out.println("OK: последний элемент User14");
        }

        // Проверка IndexOutOfBounds
        try {
            db.get(20);
            System.out.println("FAIL: должно было выбросить IndexOutOfBoundsException");
            passed = false;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("OK: правильно выброшено IndexOutOfBoundsException при get(20)");
        }

        System.out.println(passed ? "DataBaseAddGrowTest: PASS" : "DataBaseAddGrowTest: FAIL");


        passed = true;


        // Удаляем элемент посередине (индекс 2)
        Person removed = db.delete(2);
        if (!removed.getName().equals("U2")) {
            System.out.println("FAIL: удалён должен быть U2");
            passed = false;
        } else {
            System.out.println("OK: удалён U2");
        }

        if (db.getSize() != 4) {
            System.out.println("FAIL: после удаления размер должен быть 4, а равен " + db.getSize());
            passed = false;
        } else {
            System.out.println("OK: размер после удаления равен 4");
        }

        // Проверяем сдвиг: на индексе 2 теперь должен быть U3
        Person nowAt2 = db.get(2);
        if (!nowAt2.getName().equals("U3")) {
            System.out.println("FAIL: на индексе 2 должен быть U3, а равен " + nowAt2.getName());
            passed = false;
        } else {
            System.out.println("OK: сдвиг работает, на индексе 2 стоит U3");
        }

        // Удаление последнего элемента
        Person last = db.delete(3);
        if (!last.getName().equals("U4")) {
            System.out.println("FAIL: должен быть удалён последний U4");
            passed = false;
        } else {
            System.out.println("OK: удалён последний U4");
        }

        System.out.println(passed ? "DataBaseDeleteTest: PASS" : "DataBaseDeleteTest: FAIL");


        passed = true;


        Person p1 = new Person.Builder().setName("Alice").setMail("alice@t.com").setPassword("123").build();
        Person p2 = new Person.Builder().setName("Bob").setMail("bob@t.com").setPassword("456").build();
        db.add(p1);
        db.add(p2);

        // getByName
        Person foundByName = db.getByName("alice"); // ignoreCase
        if (!foundByName.getName().equals("Alice")) {
            System.out.println("FAIL: getByName не нашёл Alice");
            passed = false;
        } else {
            System.out.println("OK: getByName нашёл Alice");
        }

        // getByEmail
        Person foundByEmail = db.getByEmail("BOB@T.COM"); // ignoreCase
        if (!foundByEmail.getName().equals("Bob")) {
            System.out.println("FAIL: getByEmail не нашёл Bob");
            passed = false;
        } else {
            System.out.println("OK: getByEmail нашёл Bob");
        }

        // getByID
        Person foundById = db.getByID(p1.getId());
        if (!foundById.getName().equals("Alice")) {
            System.out.println("FAIL: getByID не нашёл Alice по ID");
            passed = false;
        } else {
            System.out.println("OK: getByID нашёл Alice по ID");
        }

        // Ошибка поиска
        try {
            db.getByName("NonExisting");
            System.out.println("FAIL: должно было выбросить NoSuchElementException");
            passed = false;
        } catch (NoSuchElementException e) {
            System.out.println("OK: правильно выброшен NoSuchElementException при поиске несуществующего");
        }

        System.out.println(passed ? "DataBaseSearchTest: PASS" : "DataBaseSearchTest: FAIL");
    }
}
