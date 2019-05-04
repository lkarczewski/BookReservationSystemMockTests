package services;

import models.User;
import repositories.IBookRepository;
import repositories.IReservedBookRepository;
import repositories.IUserRepository;

import javax.jws.soap.SOAPBinding;
import java.util.List;

public class LibraryService {

    private IUserRepository userRepository;
    private IBookRepository bookRepository;
    private IReservedBookRepository reservedBookRepository;

    public LibraryService(IUserRepository userRepository, IBookRepository bookRepository,
                          IReservedBookRepository reservedBookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.reservedBookRepository = reservedBookRepository;
    }

    //USER
    public boolean addUser(String login, String password) {
        if(userRepository.userExists(login)) {
            return false;
        }

        User user = new User(login, password);
        if(!userRepository.validateUser(user)) {
            throw new IllegalArgumentException("User parameters are not valid!");
        }

        userRepository.addUser(user);
        return true;
    }

    public boolean deleteUser(int id) {
        if(!userRepository.userExists(id)) {
            throw new IllegalArgumentException("User with given id:" + id + "does not exist!");
        }

        userRepository.deleteUser(id);
        reservedBookRepository.deleteUserBooks(id);

        return true;
    }

    public boolean updateUser(int id, String login, String password) {
        if(!userRepository.userExists(id)) {
            throw new IllegalArgumentException("User with given id:" + id + "does not exist!");
        }

        User updatedUser = new User(login, password);
        if(!userRepository.validateUser(updatedUser)) {
            throw new IllegalArgumentException("User parameters are not valid!");
        }

        User userToUpdateId = userRepository.getUser(id);
        User userToUpdateLogin = userRepository.getUser(login);
        if(userToUpdateLogin == null || userToUpdateId == userToUpdateLogin) {
            userRepository.updateUser(id, updatedUser);
            return true;
        }
        else {
            throw new IllegalArgumentException("User with this login already exists!");
        }
    }

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    public String usersToString() {
        List<User> users = userRepository.getUsers();
        String userList;
        userList = "";

        for(int i = 0; i < users.size(); i++) {
            userList += users.get(i).toString() + "\n";
        }

        return userList;
    }
}
