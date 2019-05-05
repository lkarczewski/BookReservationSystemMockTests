package EasyMockTests;

import models.Book;
import org.easymock.EasyMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import repositories.IBookRepository;
import repositories.IReservedBookRepository;
import repositories.IUserRepository;
import services.LibraryService;

public class BookTests {

    private IUserRepository userRepository;
    private IBookRepository bookRepository;
    private IReservedBookRepository reservedBookRepository;
    private LibraryService service;
    private Book book;

    @BeforeEach
    void setUp() {
        userRepository = EasyMock.createNiceMock(IUserRepository.class);
        bookRepository = EasyMock.createNiceMock(IBookRepository.class);
        reservedBookRepository = EasyMock.createNiceMock(IReservedBookRepository.class);
        service = new LibraryService(userRepository, bookRepository, reservedBookRepository);
        book = new Book();
    }

    @AfterEach
    void tearDown() {
        userRepository = null;
        bookRepository = null;
        reservedBookRepository = null;
        service = null;
        book = null;
    }
}
