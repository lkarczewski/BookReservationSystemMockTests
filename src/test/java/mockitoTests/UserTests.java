package mockitoTests;

import models.ReservedBook;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repositories.IBookRepository;
import repositories.IReservedBookRepository;
import repositories.IUserRepository;
import services.LibraryService;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

public class UserTests {

    private IUserRepository userRepository;
    private IBookRepository bookRepository;
    private IReservedBookRepository reservedBookRepository;
    private LibraryService service;
    private User user;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(IUserRepository.class);
        bookRepository = Mockito.mock(IBookRepository.class);
        reservedBookRepository = Mockito.mock(IReservedBookRepository.class);
        service = new LibraryService(userRepository, bookRepository, reservedBookRepository);
        user = new User();
    }

    @Test
    void logInExistingUserReturnsUser() {
        doReturn(user).when(userRepository).getUser(user.getLogin(), user.getPassword());

        User result = service.logIn(user.getLogin(), user.getPassword());
        assertEquals(user, result);
    }

    @Test
    void logInNonExistingUserReturnsNull() {
        doReturn(null).when(userRepository).getUser("login", "password");

        User result = service.logIn("login", "password");
        assertNull(result);
    }

    @Test
    void addValidUserReturnsTrue() {
        doReturn(false).when(userRepository).userExists(user.getLogin());
        doReturn(true).when(userRepository).validateUser(any(User.class));

        boolean result = service.addUser(user.getLogin(), user.getPassword());
        assertThat(result).isTrue();
    }

    @Test
    void addInvalidUserThrowsIllegalArgumentException() {
        doReturn(false).when(userRepository).userExists(user.getLogin());
        doReturn(false).when(userRepository).validateUser(any(User.class));

        assertThrows(IllegalArgumentException.class, () -> {
            service.addUser(user.getLogin(), user.getPassword());
        });
    }

    @Test
    void addValidUserLoginAlreadyExistsReturnsFalse() {
        doReturn(true).when(userRepository).userExists(user.getLogin());

        boolean result = service.addUser(user.getLogin(), user.getPassword());
        assertThat(result).isFalse();
    }

    @Test
    void deleteExistingUserReturnsTrue() {
        doReturn(true).when(userRepository).userExists(1);

        boolean result = service.deleteUser(1);
        assertThat(result).isTrue();
    }

    @Test
    void deleteNonExistingUserThrowsIllegalArgumentException() {
        doReturn(false).when(userRepository).userExists(1);

        assertThrows(IllegalArgumentException.class, () -> {
            service.deleteUser(1);
        });
    }

    @Test
    void updateExistingUserWithValidDataReturnsTrue() {
        doReturn(true).when(userRepository).userExists(1);
        doReturn(true).when(userRepository).validateUser(any(User.class));
        doReturn(user).when(userRepository).getUser(1);
        doReturn(user).when(userRepository).getUser(user.getLogin());

        boolean result = service.updateUser(1, "login", "password");
        assertThat(result).isTrue();
    }

    @Test
    void updateNonExistingUserThrowsIllegalArgumentException() {
        doReturn(false).when(userRepository).userExists(1);

        assertThrows(IllegalArgumentException.class, () -> {
            service.updateUser(1, "login", "password");
        });
    }

    @Test
    void updateExistingUserWithNonValidDataThrowsIllegalArgumentException() {
        doReturn(true).when(userRepository).userExists(1);
        doReturn(false).when(userRepository).validateUser(any(User.class));

        assertThatThrownBy( () -> {
           service.updateUser(1, null, null);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void updateUserWithExistingDataThrowsIllegalArgumentException() {
        User user2 = new User();

        doReturn(true).when(userRepository).userExists(1);
        doReturn(true).when(userRepository).validateUser(any(User.class));
        doReturn(user).when(userRepository).getUser(1);
        doReturn(user2).when(userRepository).getUser("login");

        assertThrows(IllegalArgumentException.class, () -> {
            service.updateUser(1, "login", "password");
        });
    }

    @Test
    void getReservedBooksByUserWithValidArgumentsReturnsCorrectNumberOfElements() {
        ReservedBook rb1 = new ReservedBook();
        ReservedBook rb2 = new ReservedBook();

        doReturn(Arrays.asList(rb1, rb2)).when(reservedBookRepository).getReservedBooksByUser(user.getId());
        int result = service.getReservationsByUser(user.getId()).size();

        assertEquals(result, 2);
    }

    @Test
    void getReservedBooksByUserWithNoReservationsReturnsEmptyList() {
        doReturn(Arrays.asList()).when(reservedBookRepository).getReservedBooksByUser(user.getId());
        int result = service.getReservationsByUser(user.getId()).size();

        assertEquals(result, 0);
    }

    @AfterEach
    void tearDown() {
        userRepository = null;
        bookRepository = null;
        reservedBookRepository = null;
        service = null;
        user = null;
    }
}
