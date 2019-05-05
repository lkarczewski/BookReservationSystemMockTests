package FakeTests;

import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.*;
import services.LibraryService;

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

    //USER
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

    @Test
    void updateExistingUserWithValidData() {
        service.addUser("login", "password");
        service.updateUser(0, "login2", "password");

        assertThat(service.getUsers().get(0).getLogin()).matches("login2");
    }

    @Test
    void updateExistingUserWithInvalidDataThrowsIllegalArgumentException() {
        service.addUser("login", "password");

        assertThrows(IllegalArgumentException.class, () -> {
            service.updateUser(0, "", null);
        });
    }

    @Test
    void updateNonExistingUserThrowsIllegalArgumentException() {
        service.addUser("login", "password");

        assertThrows(IllegalArgumentException.class, () -> {
            service.updateUser(1, "login1", "password");
        });
    }

    @Test
    void deleteExistingUserListIsEmpty() {
        service.addUser("login", "password");
        service.deleteUser(0);

        assertThat(service.getUsers().isEmpty()).isTrue();
    }

    @Test
    void deleteNonExistingUserThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.deleteUser(1);
        });
    }

    @Test
    void logInExistingUserReturnsThisUser() {
        service.addUser("login", "password");
        User loggedUser = service.logIn("login", "password");

        assertThat(loggedUser).isNotNull();
    }

    @Test
    void logInNonExistingUserReturnsNull() {
        User loggedUser = service.logIn("login", "password");

        assertThat(loggedUser).isNull();
    }

    @Test
    void usersToStringReturnsStringWithAllUsers(){
        service.addUser("login1", "password1");
        service.addUser("login2", "password2");
        service.addUser("login3", "password3");

        String result = service.usersToString();
        assertThat(result).hasLineCount(3);
    }

    //BOOK

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

    }

    //RESERVED BOOK

    @AfterEach
    void tearDown() {
        userRepository = null;
        bookRepository = null;
        reservedBookRepository = null;
        service = null;
    }
}