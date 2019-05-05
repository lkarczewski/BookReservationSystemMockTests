package repositories;

import models.User;

import java.util.List;

public class FakeUserRepository implements IUserRepository {
    @Override
    public List getUsers() {
        return null;
    }

    @Override
    public User getUser(int id) {
        return null;
    }

    @Override
    public User getUser(String login) {
        return null;
    }

    @Override
    public User getUser(String login, String password) {
        return null;
    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public void deleteUser(int id) {

    }

    @Override
    public void updateUser(int id, User user) {

    }

    @Override
    public boolean validateUser(User user) {
        return false;
    }

    @Override
    public boolean userExists(int id) {
        return false;
    }

    @Override
    public boolean userExists(String login) {
        return false;
    }
}
