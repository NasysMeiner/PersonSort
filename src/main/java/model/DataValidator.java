package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidator {
    public static boolean validateName(String name){
        if (name.isEmpty()){
            return false;
        }

        return name.matches("^[a-zA-Z]+$") || name.matches("^[а-яА-ЯёЁ]+$");
    }
    public static boolean validateEmail(String email){
        Pattern pattern = Pattern.compile("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public static boolean validatePassword(String password){
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$");
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
}
