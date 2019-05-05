package repositories;

import models.ReservedBook;

import java.util.List;

public class FakeReservedBookRepository implements IReservedBookRepository {
    @Override
    public List getReservedBooks() {
        return null;
    }

    @Override
    public List getReservedBooksByUser(int id) {
        return null;
    }

    @Override
    public void addReservedBook(ReservedBook reservedBook) {

    }

    @Override
    public void deleteReservation(int id) {

    }

    @Override
    public void deleteUserBooks(int id) {

    }

    @Override
    public void deleteBookReservations(int bookId) {

    }

    @Override
    public boolean validateReservation(ReservedBook reservedBook) {
        return false;
    }

    @Override
    public boolean reservationExists(int id) {
        return false;
    }

    @Override
    public boolean reservationExists(ReservedBook reservedBook) {
        return false;
    }
}
