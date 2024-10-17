package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Book {
    private String title;
    private List<String> authors = new ArrayList<>();
    private String genre;
    private LocalDate publicationDate;
    private LocalDate borrowedDate;
    private String currentReader;

    public Book(String title, List<String> authors, String genre, LocalDate publicationDate) {
        this.title = title;
        for(String author : authors) {
            this.authors.add(author);
        }
        this.genre = genre;
        this.publicationDate = publicationDate;
        this.currentReader = "";
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

    public boolean isBorrowed() {
        return borrowedDate != null;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", authors=" + authors +
                ", genre='" + genre + '\'' +
                ", publicationDate=" + publicationDate +
                ", borrowedDate=" + borrowedDate +
                ", currentReader='" + currentReader + '\'' +
                '}';
    }
}
