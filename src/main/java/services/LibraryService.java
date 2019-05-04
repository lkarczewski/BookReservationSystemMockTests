package services;

import helpers.DateParser;
import helpers.ReservationIdGenerator;
import models.Book;
import models.ReservedBook;
import models.User;
import repositories.IBookRepository;
import repositories.IReservedBookRepository;
import repositories.IUserRepository;
import sun.nio.ch.LinuxAsynchronousChannelProvider;

import javax.naming.spi.ResolveResult;
import java.util.Date;
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
            throw new IllegalArgumentException("User with given id:" + id + " does not exist!");
        }

        userRepository.deleteUser(id);
        reservedBookRepository.deleteUserBooks(id);

        return true;
    }

    public boolean updateUser(int id, String login, String password) {
        if(!userRepository.userExists(id)) {
            throw new IllegalArgumentException("User with given id:" + id + " does not exist!");
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

    //BOOK
    public boolean addBook(String title, String author, String genre, String description) {
        if(bookRepository.bookExists(title)) {
            return false;
        }

        Book book = new Book(title, author, genre, description);
        if(!bookRepository.validateBook(book)) {
            throw new IllegalArgumentException("Book parameters are not valid!");
        }

        bookRepository.addBook(book);
        return true;
    }

    public boolean deleteBook(int id) {
        if(!bookRepository.bookExists(id)) {
            throw new IllegalArgumentException("Book with given id:" + id + " does not exist!");
        }

        bookRepository.deleteBook(id);
        reservedBookRepository.deleteBookReservations(id);

        return true;
    }

    public boolean updateBook(int id, String title, String author, String genre, String description) {
        if(!bookRepository.bookExists(id)) {
            throw new IllegalArgumentException("Book with given id:" + id + " does not exists!");
        }

        Book updatedBook = new Book(title, author, genre, description);
        if(!bookRepository.validateBook(updatedBook)) {
            throw new IllegalArgumentException("Book parameters are not valid!");
        }

        Book bookToUpdateId = bookRepository.getBook(id);
        Book bookToUpdateTitle = bookRepository.getBook(title);
        if(bookToUpdateTitle == null || bookToUpdateId == bookToUpdateTitle) {
            bookRepository.updateBook(id, updatedBook);
            return true;
        }
        else {
            throw new IllegalArgumentException("Book with this title already exists!");
        }
    }

    public List<Book> getBooks() {
        return bookRepository.getBooks();
    }

    public String booksToString() {
        List<Book> books = bookRepository.getBooks();
        String bookList;
        bookList = "";

        for(int i = 0; i < books.size(); i++) {
            bookList += books.get(i).toString() + "\n";
        }

        return bookList;
    }

    //RESERVED BOOK
    public boolean borrowBook(int userId, int bookId, String dateOfReservation) {
        if(!userRepository.userExists(userId)) {
            throw new SecurityException("User with given id:" + userId + " does not exists!");
        }
        if(!bookRepository.bookExists(bookId)) {
            throw new SecurityException("Book with given id:" + bookId + " does not exists!");
        }

        String reservationId = ReservationIdGenerator.generateReservationId(userId, bookId, dateOfReservation);
        Date dateObj = DateParser.parseDate(dateOfReservation);
        ReservedBook reservedBook = new ReservedBook(userId, bookId, dateObj, reservationId);

        if(!reservedBookRepository.validateReservation(reservedBook)) {
            return false;
        }
        if(!reservedBookRepository.reservationExists(reservedBook)) {
            return false;
        }

        reservedBookRepository.addReservedBook(reservedBook);
        return true;
    }

    public boolean deleteReservation(int id) {
        if(!reservedBookRepository.reservationExists(id)) {
            throw new IllegalArgumentException("Reservation with givend id:" + id + " does not exist!");
        }

        reservedBookRepository.deleteReservation(id);
        return true;
    }

    public List<ReservedBook> getReservations() {
        return reservedBookRepository.getReservedBooks();
    }

    public String reservationsToString() {
        List<ReservedBook> reservedBooks = reservedBookRepository.getReservedBooks();
        String reservedBooksList;
        reservedBooksList = "";

        for(int i = 0; i < reservedBooks.size(); i++) {
            reservedBooksList += reservedBooks.get(i).toString() + "\n";
        }

        return reservedBooksList;
    }
}
