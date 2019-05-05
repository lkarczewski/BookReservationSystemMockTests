package repositories;

import helpers.ReservedBookValidatorHelper;
import models.ReservedBook;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FakeReservedBookRepository implements IReservedBookRepository {
    private List<ReservedBook> reservedBooks = new ArrayList<>();
    private int id;

    @Override
    public List getReservedBooks() {
        return reservedBooks;
    }

    @Override
    public List getReservedBooksByUser(int id) {
        return reservedBooks.stream().filter(x -> x.getUserId() == id).collect(Collectors.toList());
    }

    @Override
    public void addReservedBook(ReservedBook reservedBook) {
        reservedBooks.add(new ReservedBook(id, reservedBook));
        id++;
    }

    @Override
    public void deleteReservation(int id) {
        reservedBooks.removeIf(x -> x.getId() == id);
    }

    @Override
    public void deleteUserBooks(int id) {
        reservedBooks.removeIf(x -> x.getId() == id);
    }

    @Override
    public void deleteBookReservations(int bookId) {
        reservedBooks.removeIf(x -> x.getId() == id);
    }

    @Override
    public boolean validateReservation(ReservedBook reservedBook) {
        return ReservedBookValidatorHelper.validate(reservedBook);
    }

    @Override
    public boolean reservationExists(int id) {
        return reservedBooks.stream().anyMatch(x -> x.getId() == id);
    }

    @Override
    public boolean reservationExists(String reservationId) {
        return reservedBooks.stream().anyMatch(x -> x.getReservationId().contains(reservationId));
    }
}
