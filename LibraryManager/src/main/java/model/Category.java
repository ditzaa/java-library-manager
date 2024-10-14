package model;

import java.util.List;

public class Category {
    private List<Book> books;
    private String name;
    private int numberOfBooks;

    public Category(List<Book> books, String name, int numberOfBooks) {
        this.books = books;
        this.name = name;
        this.numberOfBooks = numberOfBooks;
    }

    public List<Book> getBooks() {
        return books;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfBooks() {
        return numberOfBooks;
    }
}
