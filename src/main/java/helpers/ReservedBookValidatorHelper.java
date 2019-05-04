package helpers;

import models.ReservedBook;

public class ReservedBookValidator {

    public boolean validate(ReservedBook reservedBook) {
        if(reservedBook.getReservationId() == null||reservedBook.getReservationId().isEmpty()) {
            return false;
        }
        return true;
    }
}
