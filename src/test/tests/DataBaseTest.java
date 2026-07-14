package test.tests;

import model.DataBase;
import model.Person;
import test.Test;

public class DataBaseTest extends Test {
    private final String[] NAMES = {
            "Igor", "Anna", "Petr", "Elena", "Maxim",
            "Olga", "Roman", "Svetlana", "Artem", "Dmitriy",
            "Yuriy", "Daniil", "Pavel", "Kirill", "Alina"
    };

    @Override
    public void run() {
        try {
            DataBase db = new DataBase();
            
            for (int i = 0; i < 15; i++) {
                Person p = new Person.Builder()
                        .setName("User" + NAMES[i])
                        .setMail("user" + i + "@test.com")
                        .setPassword("pass" + i + NAMES[i] + "pass")
                        .build();
                db.add(p);
            }

            if (db.getSize() != 15) {
                setFail();
                setMessage("Размер должен быть 15, а равен " + db.getSize());
                return;
            }

            Person p0 = db.get(0);
            if (!p0.getName().equals("UserIgor")) {
                setFail();
                setMessage("Первый элемент должен быть UserIgor, а равен " + p0.getName());
                return;
            }

            Person p14 = db.get(14);
            if (!p14.getName().equals("UserAlina")) {
                setFail();
                setMessage("Последний элемент должен быть UserAlina, а равен " + p14.getName());
                return;
            }

            try {
                db.get(20);
                setFail();
                setMessage("Должно было выбросить IndexOutOfBoundsException при get(20)");
                return;
            } catch (IndexOutOfBoundsException e) {}

            setSuccess();
            setMessage("Все тесты пройдены успешно");

        } catch (Exception e) {
            setFail();
            setMessage("Неожиданное исключение: " + e.getMessage());
        }
    }
}