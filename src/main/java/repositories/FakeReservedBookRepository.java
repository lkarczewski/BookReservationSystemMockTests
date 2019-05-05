package repositories;

import helpers.ReservedBookValidatorHelper;
import models.Book;
import models.ReservedBook;

import java.util.ArrayList;
import java.util.Date;
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
    public void deleteUserBooks(int userId) {
        reservedBooks.removeIf(x -> x.getUserId() == userId);
    }

    @Override
    public void deleteBookReservations(int bookId) {
        reservedBooks.removeIf(x -> x.getBookId() == bookId);
    }

    @Override
    public boolean validateReservation(Book book, Date dateOfReservation, String reservationId) {
        return ReservedBookValidatorHelper.validate(book, dateOfReservation, reservationId);
    }

    @Override
    public boolean reservationExists(int id) {
        return reservedBooks.stream().anyMatch(x -> x.getId() == id);
    }

    @Override
    public boolean reservationExists(ReservedBook reservedBook) {
        return reservedBooks.stream().anyMatch(x -> x.getReservationId().contains(reservedBook.getReservationId()));
    }
}
