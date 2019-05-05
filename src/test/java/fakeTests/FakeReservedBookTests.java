package fakeTests;

import models.ReservedBook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.*;
import services.LibraryService;

import java.util.List;

import static junit.framework.Assert.assertFalse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

    @Test
    void borrowBookBookDoesNotExistsThrowsSecurityException() {
        service.addUser("login", "password");

        assertThrows(SecurityException.class, () -> {
            service.borrowBook(0,0, "01.01.2019");service.borrowBook(0,0, "01.01.2019");
        });
    }

    @Test
    void borrowBookWithInvalidReservationDataReturnsFalse() {
        service.addUser("login", "password");
        service.addBook("Title", "Author", "Genre", "Desc");

        boolean result = service.borrowBook(0,0, "aaaaaaaa");
        assertFalse(result);
    }

    @Test
    void borrowBookSameBookWasAlreadyBorrowed() {
        service.addUser("login", "password");
        service.addBook("Title", "Author", "Genre", "Desc");

        service.borrowBook(0,0, "01.01.2019");
        boolean result = service.borrowBook(0,0, "01.01.2019");
        assertFalse(result);
    }

    @Test
    void deleteExistingReservedBookReturnsTrue() {
        service.addUser("login", "password");
        service.addBook("Title", "Author", "Genre", "Desc");

        service.borrowBook(0,0, "01.01.2019");
        boolean result = service.deleteReservation(0);
        assertTrue(result);
    }

    @Test
    void deleteNonExistingReservedBookThrowsIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> { service.deleteReservation(0);});
    }

    @Test
    void getReservedBooksReturnsAllBooksFromTheList(){
        service.addUser("login1", "password1");
        service.addUser("login2", "password2");
        service.addBook("Title1", "Author1", "Genre1", "Desc1");
        service.addBook("Title2", "Author2", "Genre2", "Desc2");
        service.addBook("Title3", "Author3", "Genre3", "Desc3");
        service.addBook("Title4", "Author4", "Genre4", "Desc4");
        service.borrowBook(0, 0, "01.01.2019");
        service.borrowBook(0, 1, "02.01.2019");
        service.borrowBook(1, 2, "03.01.2019");
        service.borrowBook(1, 3, "04.01.2019");

        List<ReservedBook> result = service.getReservations();
        assertThat(result.size()).isEqualTo(4);
    }

    @Test
    public void getReservationsByUserIdReturnsFilteredListWithGivenUserId(){
        service.addUser("login1", "password1");
        service.addUser("login2", "password2");
        service.addBook("Title1", "Author1", "Genre1", "Desc1");
        service.addBook("Title2", "Author2", "Genre2", "Desc2");
        service.addBook("Title3", "Author3", "Genre3", "Desc3");
        service.borrowBook(0, 0, "01.01.2019");
        service.borrowBook(0, 1, "02.01.2019");
        service.borrowBook(1, 2, "03.01.2019");

        List<ReservedBook> result = service.getReservationsByUser(0);
        assertThat(result.size()).isPositive().isGreaterThan(1).isLessThan(4);
    }

    @Test
    public void getReservationsToStringReturnsStringWithAllReservations(){
        service.addUser("login1", "password1");
        service.addUser("login2", "password2");
        service.addBook("Title1", "Author1", "Genre1", "Desc1");
        service.addBook("Title2", "Author2", "Genre2", "Desc2");
        service.addBook("Title3", "Author3", "Genre3", "Desc3");
        service.borrowBook(0, 0, "01.01.2019");
        service.borrowBook(0, 1, "02.01.2019");
        service.borrowBook(1, 2, "03.01.2019");

        String result = service.reservationsToString();
        assertThat(result).hasLineCount(3);
    }

    @AfterEach
    void tearDown() {
        userRepository = null;
        bookRepository = null;
        reservedBookRepository = null;
        service = null;
    }
}
