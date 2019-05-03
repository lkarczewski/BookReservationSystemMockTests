package models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReservedBook {

    private int id;
    private String reservationId;
    private User user;
    private Book book;
    private Date dateOfReservation;

    public ReservedBook(String reservationId, User user, Book book, Date dateOfReservation) {
        this.reservationId = reservationId;
        this.user = user;
        this.book = book;
        this.dateOfReservation = dateOfReservation;
    }

    public int getId() {
        return id;
    }

    public String getReservationId() {
        return reservationId;
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public Date getDateOfReservation() {
        return dateOfReservation;
    }

    @Override
    public String toString() {
        String dateFormat = "dd.MM.yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        return "[" + reservationId + "]" + book.getTitle() + " reserved by: " + user.getLogin() + " at " +
                sdf.format(dateOfReservation);
    }
}
