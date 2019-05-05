package helpers;

import models.Book;
import models.ReservedBook;

import java.util.Date;

public class ReservedBookValidatorHelper {

    public static boolean validate(Book book, Date dateOfReservation, String reservationId) {
        if(book == null || dateOfReservation == null || reservationId == null) {
            return false;
        }
        return true;
    }
}
