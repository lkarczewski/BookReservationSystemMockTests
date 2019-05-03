package repositories;

import models.ReservedBook;
import java.util.List;

public interface IReservedBookRepository {

    List getReservedBooks();
    List getReservedBooksByUser(int id);
    List getReservationsByBook(int id);
    void addReservedBook(ReservedBook reservedBook);
    void deleteReservation(int id);
    boolean validateReservation(ReservedBook reservedBook);
}
