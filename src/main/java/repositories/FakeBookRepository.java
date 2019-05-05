package repositories;

import models.Book;

import java.util.List;

public class FakeBookRepository implements IBookRepository {
    @Override
    public List getBooks() {
        return null;
    }

    @Override
    public Book getBook(int id) {
        return null;
    }

    @Override
    public Book getBook(String title) {
        return null;
    }

    @Override
    public void addBook(Book book) {

    }

    @Override
    public void deleteBook(int id) {

    }

    @Override
    public void updateBook(int id, Book book) {

    }

    @Override
    public boolean validateBook(Book book) {
        return false;
    }

    @Override
    public boolean bookExists(int id) {
        return false;
    }

    @Override
    public boolean bookExists(String title) {
        return false;
    }
}
