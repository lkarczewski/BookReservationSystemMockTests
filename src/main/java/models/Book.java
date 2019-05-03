package models;

public class Book {

    private int id;
    private String title;
    private String author;
    private String genre;
    private String description;

    public Book() {}

    public Book(String title, String author, String genre, String description) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "----------\n" + "'" + title + "'\n" + "'" + author + "'\n" + "'" + genre + "'\n" +
                "'" + description + "'\n" + "----------\n";
    }
}
