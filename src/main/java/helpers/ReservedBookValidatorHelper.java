package helpers;

import models.ReservedBook;

public class ReservedBookValidatorHelper {

    public static boolean validate(ReservedBook reservedBook) {
        if(reservedBook.getReservationId() == null||reservedBook.getReservationId().isEmpty()) {
            return false;
        }
        return true;
    }
}
