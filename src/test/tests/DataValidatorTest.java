package test.tests;

import model.DataValidator;
import test.Test;

public class DataValidatorTest extends Test {
    @Override
    public void run() {
        // Запускаем все тестовые методы по очереди
        testValidateNameEmpty();
        testValidateNameLatin();
        testValidateNameCyrillic();
        testValidateNameWithDigit();
        testValidateNameMixedScript();
        testValidateEmailValid();
        testValidateEmailInvalidNoAt();
        testValidatePasswordValid();
        testValidatePasswordTooShort();
        testValidatePasswordNoDigit();
    }

    private void testValidateNameEmpty() {
        try {
            boolean result = DataValidator.validateName("");
            if (!result) {
                setSuccess();
                setMessage("Пустое имя корректно отвергнуто (ожидалось false)");
            } else {
                setFail();
                setMessage("Ошибка: пустое имя должно быть invalid, но получено true");
            }
        } catch (Exception e) {
            setFail();
            setMessage("Исключение при проверке пустого имени: " + e.toString());
        }
    }

    private void testValidateNameLatin() {
        try {
            boolean result = DataValidator.validateName("Alice");
            if (result) {
                setSuccess();
                setMessage("Латинское имя Alice корректно принято");
            } else {
                setFail();
                setMessage("Ошибка: латинское имя Alice должно быть valid, но получено false");
            }
        } catch (Exception e) {
            setFail();
            setMessage("Исключение при проверке латинского имени: " + e.toString());
        }
    }

    private void testValidateNameCyrillic() {
        try {
            boolean result = DataValidator.validateName("Алиса");
            if (result) {
                setSuccess();
                setMessage("Кириллическое имя Алиса корректно принято");
            } else {
                setFail();
                setMessage("Ошибка: кириллическое имя Алиса должно быть valid, но получено false");
            }
        } catch (Exception e) {
            setFail();
            setMessage("Исключение при проверке кириллического имени: " + e.toString());
        }
    }

    private void testValidateNameWithDigit() {
        try {
            boolean result = DataValidator.validateName("Alice1");
            if (!result) {
                setSuccess();
                setMessage("Имя с цифрой (Alice1) корректно отвергнуто");
            } else {
                setFail();
                setMessage("Ошибка: имя с цифрой должно быть invalid, но получено true");
            }
        } catch (Exception e) {
            setFail();
            setMessage("Исключение при проверке имени с цифрой: " + e.toString());
        }
    }

    private void testValidateNameMixedScript() {
        try {
            boolean result = DataValidator.validateName("Алисe"); // смешанные латиница и кириллица
            if (!result) {
                setSuccess();
                setMessage("Смешанная запись имени корректно отвергнута");
            } else {
                setFail();
                setMessage("Ошибка: смешанная запись имени должна быть invalid, но получено true");
            }
        } catch (Exception e) {
            setFail();
            setMessage("Исключение при проверке смешанной записи имени: " + e.toString());
        }
    }

    private void testValidateEmailValid() {
        try {
            boolean result = DataValidator.validateEmail("user@example.com");
            if (result) {
                setSuccess();
                setMessage("Корректный email user@example.com принят");
            } else {
                setFail();
                setMessage("Ошибка: корректный email должен быть valid, но получен false");
            }
        } catch (Exception e) {
            setFail();
            setMessage("Исключение при проверке валидного email: " + e.toString());
        }
    }

    private void testValidateEmailInvalidNoAt() {
        try {
            boolean result = DataValidator.validateEmail("example.com");
            if (!result) {
                setSuccess();
                setMessage("Email без @ корректно отвергнут");
            } else {
                setFail();
                setMessage("Ошибка: email без @ должен быть invalid, но получен true");
            }
        } catch (Exception e) {
            setFail();
            setMessage("Исключение при проверке email без @: " + e.toString());
        }
    }

    private void testValidatePasswordValid() {
        try {
            boolean result = DataValidator.validatePassword("Abc12345"); // есть цифра, строчная, заглавная, длина >= 8
            if (result) {
                setSuccess();
                setMessage("Валидный пароль корректно принят");
            } else {
                setFail();
                setMessage("Ошибка: валидный пароль должен быть valid, но получен false");
            }
        } catch (Exception e) {
            setFail();
            setMessage("Исключение при проверке валидного пароля: " + e.toString());
        }
    }

    private void testValidatePasswordTooShort() {
        try {
            boolean result = DataValidator.validatePassword("Abc1234"); // длина 7
            if (!result) {
                setSuccess();
                setMessage("Слишком короткий пароль корректно отвергнут");
            } else {
                setFail();
                setMessage("Ошибка: слишком короткий пароль должен быть invalid, но получен true");
            }
        } catch (Exception e) {
            setFail();
            setMessage("Исключение при проверке слишком короткого пароля: " + e.toString());
        }
    }

    private void testValidatePasswordNoDigit() {
        try {
            boolean result = DataValidator.validatePassword("Abcdefgh"); // нет цифры
            if (!result) {
                setSuccess();
                setMessage("Пароль без цифры корректно отвергнут");
            } else {
                setFail();
                setMessage("Ошибка: пароль без цифры должен быть invalid, но получен true");
            }
        } catch (Exception e) {
            setFail();
            setMessage("Исключение при проверке пароля без цифры: " + e.toString());
        }
    }
}
