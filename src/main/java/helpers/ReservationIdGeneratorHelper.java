package helpers;

public class ReservationIdGeneratorHelper {
    public static String generateReservationId(int userId, int bookId, String dateOfReservation) {
        String date = dateOfReservation.replaceAll("[^0-9]+", "");
        return "" + userId + bookId + date;
    }
}
