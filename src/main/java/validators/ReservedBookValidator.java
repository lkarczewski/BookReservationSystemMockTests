package validators;

import models.ReservedBook;

public class ReservedBookValidator {

    public boolean validate(ReservedBook reservedBook) {
        if(reservedBook.getReservationId().isEmpty() || reservedBook.getUser() == null ||
                reservedBook.getBook() == null || reservedBook.getDateOfReservation() == null) {
            return false;
        }
        return true;
    }
}
