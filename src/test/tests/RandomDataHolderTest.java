package test.tests;

import data.RandomDataHolder;
import model.Person;
import test.Test;

public class RandomDataHolderTest extends Test {

    @Override
    public void run() {
        try {
            RandomDataHolder holder = new RandomDataHolder();
            Person[] result = holder.getData(5);

            if (result.length != 5) {
                setFail();
                setMessage("Ожидалось 5 элементов, получено " + result.length);
                return;
            }

            for (int i = 0; i < result.length; i++) {
                if (result[i] == null) {
                    setFail();
                    setMessage("Элемент " + i + " равен null");
                    return;
                }
                if (result[i].getName() == null || result[i].getName().isBlank()) {
                    setFail();
                    setMessage("Имя элемента " + i + " пустое");
                    return;
                }
            }

            setSuccess();
            setMessage("Сгенерировано 5 валидных Person");
        } catch (Exception e) {
            setFail();
            setMessage("Исключение: " + e.getMessage());
        }
    }
}