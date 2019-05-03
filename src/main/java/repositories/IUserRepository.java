package repositories;

import models.User;
import java.util.List;

public interface IUserRepository {

    List getUsers();
    User getUser(int id);
    User getUser(String login);
    User getUser(String login, String password);
    void addUser(User user);
    void deleteUser(int id);
    void updateUser(int id, User user);
    boolean validateUser(User user);
    boolean userExists(int id);
    boolean userExists(String login);
}
