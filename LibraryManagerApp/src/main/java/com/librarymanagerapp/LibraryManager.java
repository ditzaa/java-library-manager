package com.librarymanagerapp;

import com.librarymanagerapp.model.Book;
import com.librarymanagerapp.model.Category;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        FXMLLoader fxmlLoader = new FXMLLoader(LibraryManager.class.getResource("main-menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("css/index.css").toExternalForm());
        mainStage.setScene(scene);
        stage.setTitle("BookFlow");
        stage.setResizable(false);
        stage.show();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            SaveFilesManager.saveLibraryBooks();
        }));
    }

    public static void main(String[] args) {
        launch();
    }

    public static Scene switchScene(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibraryManager.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load());
        //scene.getStylesheets().add(getClass().getResource("/css/index.css").toExternalForm());
        mainStage.setScene(scene);
        return scene;
    }

    //not implemented yet
    public void setStylesheet(Scene scene) {
        scene.getStylesheets().add(getClass().getResource("index.css").toExternalForm());
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
        synchronized (library) {
            if (library.getBooks() != null) {
                library.getBooks().clear();
            }
            if (library.getAuthorsMap() != null) {
                library.getAuthorsMap().clear();
            }
            if (library.getCategories() != null) {
                library.getCategories().clear();
            }
        }

        // Define sample books and authors
        List<Book> sampleBooks = Arrays.asList(
                new Book("Java Fundamentals", Arrays.asList("John Doe", "Jane Smith"), "Programming", LocalDate.of(2020, 5, 1)),
                new Book("Advanced Java", Arrays.asList("John Doe"), "Programming", LocalDate.of(2021, 8, 15)),
                new Book("Data Structures", Arrays.asList("Emily Clark"), "Computer Science", LocalDate.of(2019, 10, 10)),
                new Book("Algorithms", Arrays.asList("Emily Clark", "James Lee"), "Computer Science", LocalDate.of(2018, 12, 25)),
                new Book("Machine Learning", Arrays.asList("Andrew Ng", "Jane Smith"), "AI", LocalDate.of(2022, 3, 18)),
                new Book("Deep Learning", Arrays.asList("Ian Goodfellow"), "AI", LocalDate.of(2018, 7, 22)),
                new Book("Artificial Intelligence", Arrays.asList("Russell Stuart", "Jane Smith"), "AI", LocalDate.of(2020, 11, 30)),
                new Book("Clean Code", Arrays.asList("Robert Martin"), "Programming", LocalDate.of(2008, 6, 17)),
                new Book("The Pragmatic Programmer", Arrays.asList("Andy Hunt", "Dave Thomas"), "Programming", LocalDate.of(1999, 10, 30)),
                new Book("Design Patterns", Arrays.asList("Erich Gamma", "Richard Helm", "Ralph Johnson"), "Software Design", LocalDate.of(1994, 10, 21)),
                new Book("Effective Java", Arrays.asList("Joshua Bloch"), "Programming", LocalDate.of(2018, 1, 6)),
                new Book("Refactoring", Arrays.asList("Martin Fowler"), "Programming", LocalDate.of(1999, 6, 28)),
                new Book("Java Concurrency", Arrays.asList("Brian Goetz"), "Programming", LocalDate.of(2006, 5, 19)),
                new Book("Introduction to Algorithms", Arrays.asList("Thomas Cormen"), "Algorithms", LocalDate.of(2009, 7, 25)),
                new Book("Programming Pearls", Arrays.asList("Jon Bentley"), "Algorithms", LocalDate.of(1986, 10, 8)),
                new Book("Computer Networks", Arrays.asList("Andrew S. Tanenbaum"), "Networks", LocalDate.of(2010, 11, 1)),
                new Book("Operating System Concepts", Arrays.asList("Abraham Silberschatz"), "Operating Systems", LocalDate.of(2013, 2, 18)),
                new Book("Computer Architecture", Arrays.asList("John Hennessy", "David Patterson"), "Hardware", LocalDate.of(2017, 9, 16)),
                new Book("Python Crash Course", Arrays.asList("Eric Matthes"), "Programming", LocalDate.of(2016, 5, 3)),
                new Book("JavaScript: The Good Parts", Arrays.asList("Douglas Crockford"), "Programming", LocalDate.of(2008, 5, 1))
        );

        // Track unique categories
        Set<String> uniqueCategories = new HashSet<>();

        // Add sample books to the library and manage categories
        for (Book book : sampleBooks) {
            library.addBook(book);
            library.addAuthor(book.getAuthors(), book);

            // Add book to category
            String genre = book.getGenre();
            if (!uniqueCategories.contains(genre)) {
                Category newCategory = new Category(genre);
                newCategory.addBook(book);
                library.getCategories().add(newCategory);
                uniqueCategories.add(genre);
            } else {
                // Find existing category and add book
                for (Category category : library.getCategories()) {
                    if (category.getName().equals(genre)) {
                        category.addBook(book);
                        break;
                    }
                }
            }
        }
    }
}