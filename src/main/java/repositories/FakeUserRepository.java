package repositories;

import helpers.UserValidatorHelper;
import models.User;

import java.util.ArrayList;
import java.util.List;

public class FakeUserRepository implements IUserRepository {
    private List<User> users = new ArrayList<>();
    private int id = 0;

    @Override
    public List getUsers() {
        return users;
    }

    @Override
    public User getUser(int id) {
        return users.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    @Override
    public User getUser(String login) {
        return users.stream().filter(x -> x.getLogin().equals(login)).findFirst().orElse(null);
    }

    @Override
    public User getUser(String login, String password) {
        return users.stream().filter(x -> x.getLogin().equals(login) && x.getPassword().equals(password)).
                findFirst().orElse(null);
    }

    @Override
    public void addUser(User user) {
        users.add(new User(id, user));
        id++;
    }

    @Override
    public void deleteUser(int id) {

    }

    @Override
    public void updateUser(int id, User user) {
        users.stream().filter(x -> x.getId() == id).findFirst().ifPresent(u -> users.set(users.indexOf(u), new User(id, user)));
    }

    @Override
    public boolean validateUser(User user) {
        return UserValidatorHelper.validate(user);
    }

    @Override
    public boolean userExists(int id) {
        return users.stream().anyMatch(x -> x.getId() == id);
    }

    @Override
    public boolean userExists(String login) {
        return users.stream().anyMatch(x -> x.getLogin().equals(login));
    }
}
