package FakeTests;

import models.Book;
import models.ReservedBook;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import repositories.*;
import services.LibraryService;

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

    @AfterEach
    void tearDown() {
        userRepository = null;
        bookRepository = null;
        reservedBookRepository = null;
        service = null;
    }
}
