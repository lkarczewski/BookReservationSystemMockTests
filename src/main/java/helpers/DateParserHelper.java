package helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
    private static String dateFormat = "dd.MM.yyyy";

    public static Date parseDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date dateObj;

        try {
            dateObj = sdf.parse(date);
        } catch (ParseException e) {
            dateObj = null;
        }

        return dateObj;
    }

    public static String getDateFormat() {
        return dateFormat;
    }
}
