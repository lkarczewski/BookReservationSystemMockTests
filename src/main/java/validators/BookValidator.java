package validators;

import models.Book;

public class BookValidator {

    public static boolean validate(Book book) {
        if(book.getTitle() == null || book.getTitle().isEmpty() || book.getTitle().length() > 50 ||
                book.getAuthor() == null || book.getAuthor().isEmpty() || book.getAuthor().length() > 30 ||
                book.getGenre() == null || book.getGenre().isEmpty() || book.getGenre().length() > 20 ||
                book.getDescription() == null || book.getDescription().isEmpty() || book.getDescription().length() > 100) {
            return false;
        }
        return true;
    }
}
