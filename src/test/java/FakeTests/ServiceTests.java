package FakeTests;

import models.Book;
import models.ReservedBook;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.*;
import services.LibraryService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServiceTests {

    private IUserRepository userRepository;
    private IBookRepository bookRepository;
    private IReservedBookRepository reservedBookRepository;
    private LibraryService service;

    @BeforeEach
    void setUp() {
        userRepository = new FakeUserRepository();
        bookRepository = new FakeBookRepository();
        reservedBookRepository = new FakeReservedBookRepository();
        service = new LibraryService(userRepository, bookRepository, reservedBookRepository);
    }

    @Test
    void addValidUserToListUserIsOnTheList() {
        service.addUser("login", "password");

        assertThat(service.getUsers().get(0).getLogin()).matches("login");
    }

    @Test
    void addValidUsersGetCorrectNumberFromTheList() {
        service.addUser("login1", "password1");
        service.addUser("login2", "password2");
        service.addUser("login3", "password3");

        int result = service.getUsers().size();
        assertEquals(3, result);
    }

    @Test
    void addInvalidUserThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.addUser(null, "");
        });
    }

    @AfterEach
    void tearDown() {
        userRepository = null;
        bookRepository = null;
        reservedBookRepository = null;
        service = null;
    }
}