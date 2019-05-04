package helpers;

import models.User;

public class UserValidator {

    public static boolean validate(User user) {
        if(user.getLogin() == null || user.getLogin().isEmpty() || user.getLogin().length() > 20 ||
                user.getPassword() == null || user.getPassword().isEmpty() || user.getPassword().length() < 4) {
            return false;
        }
        return true;
    }
}