package EasyMockTests;

import models.Book;
import org.easymock.EasyMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.IBookRepository;
import repositories.IReservedBookRepository;
import repositories.IUserRepository;
import services.LibraryService;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void addNonExistingValidBookReturnsTrue() {
        expect(bookRepository.validateBook(anyObject(Book.class))).andReturn(true);
        expect(bookRepository.bookExists("title")).andReturn(false);
        replay(bookRepository);

        boolean result = service.addBook(book.getTitle(), book.getAuthor(), book.getGenre(), book.getDescription());
        assertTrue(result);
    }

    @Test
    void addInvalidBookThrowsIllegalArgumentException() {
        expect(bookRepository.validateBook(anyObject(Book.class))).andReturn(false);
        replay(bookRepository);

        assertThrows(IllegalArgumentException.class, () -> {
            service.addBook(book.getTitle(), book.getTitle(), book.getGenre(), book.getDescription());
        });
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
