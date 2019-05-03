package models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReservedBook {

    private int id;
    private int userId;
    private int bookId;
    private Date dateOfReservation;
    private String reservationId;

    public ReservedBook() {}

    public ReservedBook(int userId, int bookId, Date dateOfReservation, String reservationId) {
        this.userId = userId;
        this.bookId = bookId;
        this.dateOfReservation = dateOfReservation;
        this.reservationId = reservationId;
    }

    public ReservedBook(int id, ReservedBook reservedBook) {
        this.id = id;
        this.userId = reservedBook.getUserId();
        this.bookId = reservedBook.getBookId();
        this.dateOfReservation = reservedBook.getDateOfReservation();
        this.reservationId = reservedBook.getReservationId();
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getBookId() {
        return bookId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public Date getDateOfReservation() {
        return dateOfReservation;
    }

    @Override
    public String toString() {
        String dateFormat = "dd.MM.yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        return "[" + reservationId + "]" + bookId + " reserved by: " + userId + " at " +
                sdf.format(dateOfReservation);
    }
}
