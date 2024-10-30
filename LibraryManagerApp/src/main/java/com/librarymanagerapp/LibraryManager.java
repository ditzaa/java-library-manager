package com.librarymanagerapp;

import com.librarymanagerapp.model.Book;
import com.librarymanagerapp.model.Library;
import com.librarymanagerapp.services.SaveFilesManager;
import com.librarymanagerapp.util.InputValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class LibraryManager extends Application {
    private static Stage mainStage;

    private static Library library = SaveFilesManager.reloadLibraryBooks();
    private static InputValidator inputValidator = new InputValidator();
    public static Book currentSelectedBook = new Book();

    public LibraryManager() throws FileNotFoundException {
    }

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        switchScene("main-menu-view.fxml");
        stage.setTitle("BookFlow");
        stage.show();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            SaveFilesManager.saveLibraryBooks();
        }));
    }

    public static void main(String[] args) {
        launch();
    }

    public static void switchScene(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibraryManager.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load());
        mainStage.setScene(scene);
    }

    public static Library getLibrary() {
        return library;
    }

    public static Book getCurrentSelectedBook() {
        return currentSelectedBook;
    }

    public static void setCurrentSelectedBook(Book currentSelectedBook) {
        LibraryManager.currentSelectedBook = currentSelectedBook;
    }

    public static void resetLibraryData() {
        // Clear existing data
        library.getBooks().clear();
        library.getAuthorsMap().clear();

        // Define sample books and authors
        List<Book> sampleBooks = List.of(
                new Book("Java Fundamentals", List.of("John Doe", "Jane Smith"), "Programming", LocalDate.of(2020, 5, 1)),
                new Book("Advanced Java", List.of("John Doe"), "Programming", LocalDate.of(2021, 8, 15)),
                new Book("Data Structures", List.of("Emily Clark"), "Computer Science", LocalDate.of(2019, 10, 10)),
                new Book("Algorithms", List.of("Emily Clark", "James Lee"), "Computer Science", LocalDate.of(2018, 12, 25)),
                new Book("Machine Learning", List.of("Andrew Ng", "Jane Smith"), "AI", LocalDate.of(2022, 3, 18)),
                new Book("Deep Learning", List.of("Ian Goodfellow"), "AI", LocalDate.of(2018, 7, 22)),
                new Book("Artificial Intelligence", List.of("Russell Stuart", "Jane Smith"), "AI", LocalDate.of(2020, 11, 30)),
                new Book("Clean Code", List.of("Robert Martin"), "Programming", LocalDate.of(2008, 6, 17)),
                new Book("The Pragmatic Programmer", List.of("Andy Hunt", "Dave Thomas"), "Programming", LocalDate.of(1999, 10, 30)),
                new Book("Design Patterns", List.of("Erich Gamma", "Richard Helm", "Ralph Johnson"), "Software Design", LocalDate.of(1994, 10, 21)),
                // Add more books to reach 20 total
                new Book("Effective Java", List.of("Joshua Bloch"), "Programming", LocalDate.of(2018, 1, 6)),
                new Book("Refactoring", List.of("Martin Fowler"), "Programming", LocalDate.of(1999, 6, 28)),
                new Book("Java Concurrency", List.of("Brian Goetz"), "Programming", LocalDate.of(2006, 5, 19)),
                new Book("Introduction to Algorithms", List.of("Thomas Cormen"), "Algorithms", LocalDate.of(2009, 7, 25)),
                new Book("Programming Pearls", List.of("Jon Bentley"), "Algorithms", LocalDate.of(1986, 10, 8)),
                new Book("Computer Networks", List.of("Andrew S. Tanenbaum"), "Networks", LocalDate.of(2010, 11, 1)),
                new Book("Operating System Concepts", List.of("Abraham Silberschatz"), "Operating Systems", LocalDate.of(2013, 2, 18)),
                new Book("Computer Architecture", List.of("John Hennessy", "David Patterson"), "Hardware", LocalDate.of(2017, 9, 16)),
                new Book("Python Crash Course", List.of("Eric Matthes"), "Programming", LocalDate.of(2016, 5, 3)),
                new Book("JavaScript: The Good Parts", List.of("Douglas Crockford"), "Programming", LocalDate.of(2008, 5, 1))
        );

        // Add sample books to the library
        for (Book book : sampleBooks) {
            library.addBook(book);
            library.addAuthor(book.getAuthors(), book);
        }
    }

}