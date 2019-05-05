package repositories;

import helpers.BookValidatorHelper;
import models.Book;

import java.util.ArrayList;
import java.util.List;

public class FakeBookRepository implements IBookRepository {
    private List<Book> books = new ArrayList<>();
    private int id = 0;

    @Override
    public List getBooks() {
        return books;
    }

    @Override
    public Book getBook(int id) {
        return books.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Book getBook(String title) {
        return books.stream().filter(x -> x.getTitle().equals(title)).findFirst().orElse(null);
    }

    @Override
    public void addBook(Book book) {
        books.add(new Book(id, book));
        id++;
    }

    @Override
    public void deleteBook(int id) {
        books.removeIf(x -> x.getId() == id);
    }

    @Override
    public void updateBook(int id, Book book) {
        books.stream().filter(x -> x.getId() == id).findFirst().ifPresent(
                b -> books.set(books.indexOf(b), new Book(id, book)));
    }

    @Override
    public boolean validateBook(Book book) {
        return BookValidatorHelper.validate(book);
    }

    @Override
    public boolean bookExists(int id) {
        return books.stream().anyMatch(x -> x.getId() == id);
    }

    @Override
    public boolean bookExists(String title) {
        return books.stream().anyMatch(x -> x.getTitle().equals(title));
    }
}
