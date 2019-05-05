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

    //RESERVED BOOK

    @AfterEach
    void tearDown() {
        userRepository = null;
        bookRepository = null;
        reservedBookRepository = null;
        service = null;
    }
}