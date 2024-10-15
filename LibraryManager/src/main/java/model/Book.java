package model;

import java.time.LocalDate;

public class Book {
    private String title;
    private String author;
    private String genre;
    private LocalDate publicationDate;
    private LocalDate borrowedDate;
    private String currentReader;

    public Book(String title, String author, String genre, LocalDate publicationDate) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationDate = publicationDate;
        this.currentReader = "";
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

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public String getCurrentReader() {
        return currentReader;
    }

    public boolean isBorrowed() {
        return borrowedDate != null;
    }

//    public void borrowBook(LocalDate date) {
//        this.borrowedDate = date;
//    }
}
