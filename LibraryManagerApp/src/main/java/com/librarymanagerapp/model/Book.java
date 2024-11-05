package com.librarymanagerapp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Book implements Serializable {
    public static int BOOK_ID = 100000;

    private int idBook;
    private String title;
    private List<String> authors = new ArrayList<>();
    private String genre;
    private LocalDate publicationDate;
    private LocalDate borrowedDate;
    private String currentReader;
    private int numberOfBorrowings;

    public Book(String title, List<String> authors, String genre, LocalDate publicationDate) {
        this.idBook = BOOK_ID++;
        this.title = title;
        for(String author : authors) {
            this.authors.add(author);
        }
        this.genre = genre;
        this.publicationDate = publicationDate;
        this.currentReader = "";
        this.numberOfBorrowings = 0;
    }

    public Book() {

    }

    public int getIdBook() {
        return idBook;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public String getGenre() {
        return genre;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public String getCurrentReader() {
        return currentReader;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public boolean isBorrowed() {
        return borrowedDate != null;
    }

    public int getNumberOfBorrowings() {
        return numberOfBorrowings;
    }

    public static void setID_BOOK(int bookId) {
        BOOK_ID = bookId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setCurrentReader(String currentReader) {
        this.currentReader = currentReader;
    }

    public void setBorrowedDate(LocalDate borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public void setNumberOfBorrowings(int numberOfBorrowings) {
        this.numberOfBorrowings = numberOfBorrowings;
    }

    @Override
    public String toString() {
        return "Book{" +
                "idBook=" + idBook +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", genre='" + genre + '\'' +
                ", publicationDate=" + publicationDate +
                ", borrowedDate=" + borrowedDate +
                ", currentReader='" + currentReader + '\'' +
                '}';
    }
}
