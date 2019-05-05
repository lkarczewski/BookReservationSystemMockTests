package FakeTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.*;
import services.LibraryService;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FakeReservedBookTests {

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
    void borrowBookUserAndBookExistsAllDataIsValidReturnsTrue() {
        service.addUser("login", "password");
        service.addBook("Title", "Author", "Genre", "Desc");

        boolean result = service.borrowBook(0,0, "01.01.2019");
        assertTrue(result);
    }

    @Test
    void borrowBookUserDoesNotExistsThrowsSecurityException() {
        service.addBook("Title", "Author", "Genre", "Desc");

        assertThrows(SecurityException.class, () -> {
            service.borrowBook(0,0, "01.01.2019");service.borrowBook(0,0, "01.01.2019");
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
