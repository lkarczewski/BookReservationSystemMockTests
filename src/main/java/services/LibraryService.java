package services;

import models.User;
import repositories.IBookRepository;
import repositories.IReservedBookRepository;
import repositories.IUserRepository;

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
            throw new IllegalArgumentException("User is not valid!");
        }

        userRepository.addUser(user);

        return true;
    }

    public boolean deleteUser(int id) {
        if(!userRepository.userExists(id)) {
            throw new IllegalArgumentException("User with id:" + id + "does not exist!");
        }

        userRepository.deleteUser(id);
        reservedBookRepository.deleteUserBooks(id);

        return true;
    }
}
