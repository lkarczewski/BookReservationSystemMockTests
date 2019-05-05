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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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

    @Test
    void addBookAlreadyExistsReturnsFalse() {
        expect(bookRepository.bookExists(book.getTitle())).andReturn(true);
        replay(bookRepository);

        boolean result = service.addBook(book.getTitle(), book.getTitle(), book.getGenre(), book.getDescription());
        assertThat(result).isFalse();
    }

    @Test
    void deleteExistingBookReturnsTrue() {
        expect(bookRepository.bookExists(1)).andReturn(true);
        replay(bookRepository);

        boolean result = service.deleteBook(1);
        assertThat(result).isTrue();
    }

    @Test
    void deleteNonExistingBookThrowsIllegalArgumentException() {
        expect(bookRepository.bookExists(1)).andReturn(false);
        replay(bookRepository);

        assertThatThrownBy( () -> {
            service.deleteBook(1);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void updateExistingBookWithValidDataReturnsTrue() {
        expect(bookRepository.bookExists(1)).andReturn(true);
        expect(bookRepository.validateBook(anyObject(Book.class))).andReturn(true);
        expect(bookRepository.getBook(1)).andReturn(book);
        expect(bookRepository.getBook(book.getTitle())).andReturn(book);
        replay(bookRepository);

        boolean result = service.updateBook(1, "newTitle", "newAuthor", "newGenre", "newDesc");
        assertThat(result).isTrue();
    }

    @Test
    void updateNonExistingBookThrowsIllegalArgumentException() {
        expect(bookRepository.bookExists(1)).andReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            service.updateBook(1, "title", "author", "genre", "desc");
        });
    }

    @Test
    void updateExistingBookWithInvalidDataThrowsIllegalArgumentException() {
        expect(bookRepository.bookExists(1)).andReturn(true);
        expect(bookRepository.validateBook(anyObject(Book.class))).andReturn(false);
        replay(bookRepository);

        assertThrows(IllegalArgumentException.class, () -> {
            service.updateBook(1, "title", "author", "genre", "desc");
        });
    }

    @Test
    void updateBookWithExistingDataThrowsIllegalArgumentException() {
        Book book2 = new Book();

        expect(bookRepository.bookExists(1)).andReturn(true);
        expect(bookRepository.validateBook(anyObject(Book.class))).andReturn(true);
        expect(bookRepository.getBook(1)).andReturn(book);
        expect(bookRepository.getBook("Title")).andReturn(book2);
        replay(bookRepository);

        assertThrows(IllegalArgumentException.class, () -> {
            service.updateBook(1, "Title", "Author", "Genre", "Desc");
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
