package repositories;

import models.ReservedBook;
import java.util.List;

public interface IReservedBookRepository {

    List getReservedBooks();
    List getReservedBooksByUser(int id);
    void addReservedBook(ReservedBook reservedBook);
    void deleteReservation(int id);
    void deleteUserBooks(int id);
    void deleteBookReservations(int bookId);
    boolean validateReservation(ReservedBook reservedBook);
    boolean reservationExists(int id);
    boolean reservationExists(String reservationId);
}
