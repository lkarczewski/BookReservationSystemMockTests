package repositories;

import models.Book;
import java.util.List;

public interface IBookRepository {

    List getBooks();
    Book getBook(int id);
    Book getBook(String title);
    void addBook(Book book);
    void deleteBook(int id);
    void updateBook(int id, Book book);
    boolean validateBook(Book book);
}
