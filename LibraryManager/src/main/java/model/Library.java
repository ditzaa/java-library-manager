package model;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Library implements Serializable {
    private List<Book> books;
    private Set<Category> categories;

    public Library() {
        books = new ArrayList<>();
        categories = new TreeSet<Category>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public List<Book> getBooksByGenre(String genre) {
        return books.stream()
                .filter(book -> book.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }

    public void displayBooks(){
        for(Book book : books){
            System.out.println(book);
        }
    }
}
