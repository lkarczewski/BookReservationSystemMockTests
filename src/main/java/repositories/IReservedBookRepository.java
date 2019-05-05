package repositories;

import models.Book;
import models.ReservedBook;

import java.util.Date;
import java.util.List;

public interface IReservedBookRepository {

    List getReservedBooks();
    List getReservedBooksByUser(int id);
    void addReservedBook(ReservedBook reservedBook);
    void deleteReservation(int id);
    void deleteUserBooks(int userId);
    void deleteBookReservations(int bookId);
    boolean validateReservation(Book book, Date dateOfReservation, String reservationId);
    boolean reservationExists(int id);
    boolean reservationExists(ReservedBook reservedBook);
}
