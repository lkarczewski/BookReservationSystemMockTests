import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import repositories.IBookRepository;
import repositories.IReservedBookRepository;
import repositories.IUserRepository;
import services.LibraryService;

public class MockitoTest {

    IUserRepository userRepository;
    IBookRepository bookRepository;
    IReservedBookRepository reservedBookRepository;
    LibraryService service;

    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(IUserRepository.class);
        bookRepository = Mockito.mock(IBookRepository.class);
        reservedBookRepository = Mockito.mock(IReservedBookRepository.class);
        service = new LibraryService(userRepository, bookRepository, reservedBookRepository);
    }

    @AfterEach
    public void tearDown() {
        userRepository = null;
        bookRepository = null;
        reservedBookRepository = null;
        service = null;
    }
}
