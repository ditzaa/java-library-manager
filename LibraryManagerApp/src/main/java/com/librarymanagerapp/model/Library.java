package com.librarymanagerapp.model;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

public class Library implements Serializable {
    private List<Book> books;
    private Set<Category> categories = new HashSet<>();
    private Map<String, List<Book>> authorsMap = new HashMap<>();
    private Map<YearMonth, List<Book>> monthsYearsMap = new HashMap<>();
    private int[] nbTopBorrowings = new int[10];
    private String[] titlesTopBorrowings = new String[10];

    public Library() {
        books = new ArrayList<>();
    }

    public List<Book> getBooks() {
        return books;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Map<String, List<Book>> getAuthorsMap() {
        return authorsMap;
    }

    public List<Book> getBooksByGenre(String genre) {
        return books.stream()
                .filter(book -> book.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }

    public Map<YearMonth, List<Book>> getMonthsYearsMap() {
        return monthsYearsMap;
    }

    public Book getBookByIndex(int index) {
        return books.get(index);
    }

    public int[] getNbTopBorrowings() {
        return nbTopBorrowings;
    }

    public String[] getTitlesTopBorrowings() {
        return titlesTopBorrowings;
    }

    public void displayBooks(){
        for(Book book : books){
            System.out.println(book);
        }
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public void addAuthor(List<String> authorsList, Book newBook) {
        for (String author : authorsList) {
            if (authorsMap.containsKey(author)) {
                List<Book> booksList  = authorsMap.get(author);
                boolean isBookInList = false;
                for (Book book : booksList) {
                    if (newBook.getTitle().equals(book.getTitle())) {
                        isBookInList = true;
                    }
                }
                if (!isBookInList) {
                    booksList.add(newBook);
                }
                authorsMap.put(author, booksList);
            } else {
                List<Book> bookList = new ArrayList<>();
                bookList.add(newBook);
                authorsMap.put(author, bookList);
            }
        }
    }

    public void displayAuthors(){
        for(String author : authorsMap.keySet()){
            System.out.println(author);
            List<Book> booksList  = authorsMap.get(author);
            for(Book book : booksList) {
                System.out.println("- " + book.toString());
            }
        }
    }

    public void addBorrowedDate(YearMonth yearMonth, Book book) {
        if (monthsYearsMap.containsKey(yearMonth)) {
            monthsYearsMap.get(yearMonth).add(book);
        } else {
            List<Book> booksList = new ArrayList<>();
            booksList.add(book);
            monthsYearsMap.put(yearMonth, booksList);
        }
    }

    public void checkIfTopBorrowing(Book book) {
        int positionToReplace = -1;
        boolean alreadyPlaced = false;
        for (int i = 0; i < nbTopBorrowings.length; i++) {
            //cartea este deja in top, actualizeaza nr de imprumuturi
            if ( titlesTopBorrowings[i] != null && titlesTopBorrowings[i].equals(book.getTitle())){
                nbTopBorrowings[i] = book.getNumberOfBorrowings();
                alreadyPlaced = true;
                sortTopBorrowings();
                break;
            } else if (nbTopBorrowings[i] == 0) { //cartea nu este in top
                //exista loc liber in top
                nbTopBorrowings[i] = book.getNumberOfBorrowings();
                titlesTopBorrowings[i] = book.getTitle();
                alreadyPlaced = true;
                break;
            } else {
                if (nbTopBorrowings[i] <= book.getNumberOfBorrowings()) {
                    positionToReplace = i;
                }
            }
        }

        if (!alreadyPlaced && positionToReplace != -1) {
            for (int j = nbTopBorrowings.length - 1; j > positionToReplace; j--) {
                nbTopBorrowings[j] = nbTopBorrowings[j-1];
                titlesTopBorrowings[j] = titlesTopBorrowings[j-1];
            }
            nbTopBorrowings[positionToReplace] = book.getNumberOfBorrowings();
            titlesTopBorrowings[positionToReplace] = book.getTitle();
        }

        //check
        System.out.println();
        for (int i = 0; i < titlesTopBorrowings.length; i++) {
            System.out.println(i + " " + titlesTopBorrowings[i] + " - " + nbTopBorrowings[i]);
        }
    }

    public void sortTopBorrowings() {
        for (int i = 0; i < nbTopBorrowings.length; i++) {
            for (int j = i; j < nbTopBorrowings.length; j++) {
                if (nbTopBorrowings[i] < nbTopBorrowings[j]) {
                    int aux = nbTopBorrowings[j];
                    nbTopBorrowings[j] = nbTopBorrowings[i];
                    nbTopBorrowings[i] = aux;

                    String titleAux = titlesTopBorrowings[j];
                    titlesTopBorrowings[j] = titlesTopBorrowings[i];
                    titlesTopBorrowings[i] = titleAux;
                }
            }
        }
    }
}
