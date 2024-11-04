package com.librarymanagerapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Category implements Serializable {
    private List<Book> books;
    private String name;
    private int numberOfBooks;

    public Category(String name) {
        this.books = new ArrayList<>();
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

    public void addBook(Book newBook){
        books.add(newBook);
        this.numberOfBooks++;
    }

    public void removeBook(Book newBook){
        books.remove(newBook);
        this.numberOfBooks--;
    }

    @Override
    public String toString() {
        return "Category{" +
                "books=" + books +
                ", name='" + name + '\'' +
                ", numberOfBooks=" + numberOfBooks +
                '}';
    }
}
