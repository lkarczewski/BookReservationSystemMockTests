package models;

public class User {

    private int id;
    private String login;
    private String password;

    public User() {}

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(int id, User user) {
        this.id = id;
        this.login = user.getLogin();
        this.password = user.getPassword();
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "'" + login + "'";
    }
}
