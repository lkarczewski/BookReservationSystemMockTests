import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repositories.IBookRepository;
import repositories.IReservedBookRepository;
import repositories.IUserRepository;
import services.LibraryService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

public class MockitoTest {

    private IUserRepository userRepository;
    private IBookRepository bookRepository;
    private IReservedBookRepository reservedBookRepository;
    private LibraryService service;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(IUserRepository.class);
        bookRepository = Mockito.mock(IBookRepository.class);
        reservedBookRepository = Mockito.mock(IReservedBookRepository.class);
        service = new LibraryService(userRepository, bookRepository, reservedBookRepository);
    }

    @Test
    void addValidUserReturnsTrue() {
        User user = new User();

        doReturn(false).when(userRepository).userExists(user.getLogin());
        doReturn(true).when(userRepository).validateUser(any(User.class));

        boolean result = service.addUser(user.getLogin(), user.getPassword());

        assertThat(result).isTrue();
    }

    @Test
    void addInvalidUserThrowsException() {
        User user = new User();

        doReturn(false).when(userRepository).userExists(user.getLogin());
        doReturn(false).when(userRepository).validateUser(any(User.class));

        assertThrows(IllegalArgumentException.class, () -> {
            service.addUser(user.getLogin(), user.getPassword());
        });
    }

    @AfterEach
    void tearDown() {
        userRepository = null;
        bookRepository = null;
        reservedBookRepository = null;
        service = null;
    }
}
