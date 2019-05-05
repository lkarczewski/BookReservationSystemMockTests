package fakes;

import models.ReservedBook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.*;
import services.LibraryService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class FakeBookTests {

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
    void addValidBookToListBookIsOnTheList() {
        service.addBook("Title", "Author", "Genre", "Desc");

        assertThat(service.getBooks().get(0).getTitle()).matches("Title");
    }

    @Test
    void addValidBooksGetCorrectNumberFromTheList() {
        service.addBook("Title1", "Author1", "Genre1", "Desc1");
        service.addBook("Title2", "Author2", "Genre2", "Desc2");
        service.addBook("Title3", "Author3", "Genre3", "Desc3");
        service.addBook("Title4", "Author4", "Genre4", "Desc4");

        int result = service.getBooks().size();
        assertEquals(4, result);
    }

    @Test
    void addInvalidBookThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.addBook(null, null, null, null);
        });
    }

    @Test
    void addSameBookTwiceThereIsOnlyOneOnTheList() {
        service.addBook("Title", "Author", "Genre", "Desc");
        service.addBook("Title", "Author", "Genre", "Desc");

        int result = service.getBooks().size();
        assertEquals(1, result);
    }

    @Test
    void updateExistingBookWithValidData() {
        service.addBook("Title", "Author", "Genre", "Desc");
        service.updateBook(0, "Title2", "Author2", "Genre2", "Desc2");

        assertThat(service.getBooks().get(0).getTitle()).matches("Title2");
    }

    @Test
    void updateExistingBookWithInvalidDataThrowsIllegalArgumentException() {
        service.addBook("Title", "Author", "Genre", "Desc");

        assertThrows(IllegalArgumentException.class, () -> {
            service.updateBook(0, null, "", null, "");
        });
    }

    @Test
    void updateNonExistingBookWithInvalidDataThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.deleteBook(1);
        });
    }

    @Test
    void deleteExistingBookListIsEmpty() {
        service.addBook("Title", "Author", "Genre", "Desc");
        service.deleteBook(0);

        assertThat(service.getBooks().isEmpty()).isTrue();
    }

    @Test
    void deleteNonExistingBookThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.deleteBook(1);
        });
    }

    @Test
    void deleteBookThatHasBeenBorrowedReservationsAreDeleted() {
        service.addUser("login", "password");
        service.addBook("Title", "Author", "Genre", "Desc");
        service.borrowBook(0,0, "01.01.2019");
        service.deleteBook(0);

        List<ReservedBook> result = service.getReservations();
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    void booksToStringReturnsStringWithAllBooks() {
        service.addBook("Title1", "Author1", "Genre1", "Desc1");
        service.addBook("Title2", "Author2", "Genre2", "Desc2");
        service.addBook("Title3", "Author3", "Genre3", "Desc3");
        service.addBook("Title4", "Author4", "Genre4", "Desc4");

        String result = service.booksToString();
        assertThat(result).hasLineCount(28); //one book has 7 lines in toString method
    }

    @AfterEach
    void tearDown() {
        userRepository = null;
        bookRepository = null;
        reservedBookRepository = null;
        service = null;
    }
}
