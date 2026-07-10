package data;

import model.Person;

import java.util.Random;
import java.util.stream.IntStream;

public class RandomDataHolder implements DataHolder {
    private static final String[] NAMES = {
            "Игорь", "Анна", "Пётр", "Елена", "Максим", "Ольга", "Роман", "Светлана"
    };
    private static final String CHARS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 8;

    private final Random random = new Random();

    @Override
    public Person[] getData(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException(
                    "Размер должен быть положительным: " + count);
        }

        return IntStream.range(0, count)
                .mapToObj(i -> buildRandomPerson())
                .toArray(Person[]::new);
    }

    private Person buildRandomPerson() {
        String name = NAMES[random.nextInt(NAMES.length)];
        return new Person.Builder()
                .setName(name)
                .setMail(name.toLowerCase() + random.nextInt(1000) + "@mail.ru")
                .setPassword(generatePassword())
                .build();
    }

    private String generatePassword() {
        return random.ints(PASSWORD_LENGTH, 0, CHARS.length())
                .mapToObj(CHARS::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}