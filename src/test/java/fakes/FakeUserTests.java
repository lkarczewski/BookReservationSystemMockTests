package fakes;

import models.ReservedBook;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import repositories.FakeUserRepository;
import repositories.FakeBookRepository;
import repositories.FakeReservedBookRepository;
import repositories.IBookRepository;
import repositories.IReservedBookRepository;
import repositories.IUserRepository;
import services.LibraryService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FakeUserTests {

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
    void addSameUserTwiceThereIsOnlyOneOnTheList() {
        service.addUser("login", "password");
        service.addUser("login", "password");

        int result = service.getUsers().size();
        assertEquals(1, result);
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
    void deleteUserWithBorrowedBooksReservationsAreDeleted() {
        service.addUser("login", "password");
        service.addBook("Title", "Author", "Genre", "Desc");
        service.borrowBook(0,0, "01.01.2019");
        service.deleteUser(0);

        List<ReservedBook> result = service.getReservations();
        assertThat(result.size()).isEqualTo(0);
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

    @AfterEach
    void tearDown() {
        userRepository = null;
        bookRepository = null;
        reservedBookRepository = null;
        service = null;
    }
}