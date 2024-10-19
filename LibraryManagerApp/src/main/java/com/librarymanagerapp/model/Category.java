package com.librarymanagerapp.model;

import java.util.List;

public class Category {
    private List<Book> books;
    private String name;
    private int numberOfBooks;

    public Category(List<Book> books, String name) {
        this.books = books;
        this.name = name;
        this.numberOfBooks = 0;
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
