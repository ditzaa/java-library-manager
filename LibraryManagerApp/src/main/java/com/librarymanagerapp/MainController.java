package com.librarymanagerapp;

import com.librarymanagerapp.model.Book;
import com.librarymanagerapp.model.Category;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainController {
    @FXML

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToAddBookScene(ActionEvent event) {
        try {
            LibraryManager.switchScene("add-book-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToRemoveBookScene(ActionEvent event) {
        try {
            LibraryManager.switchScene("remove-book-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void displayBooks(ActionEvent event){
        System.out.println();
        LibraryManager.getLibrary().displayBooks();
    }

    @FXML
    void onBookSearch(ActionEvent event) {
        try {
            LibraryManager.switchScene("search-book-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onReportsMenu(ActionEvent event) {
        try {
            LibraryManager.switchScene("generate-reports-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void displayAuthors(ActionEvent event) {
        System.out.println();
        LibraryManager.getLibrary().displayAuthors();
    }

    @FXML
    void onResetLibraryDataClicked(ActionEvent event) {
        LibraryManager.resetLibraryData();
    }

    @FXML
    void displayCategories(ActionEvent event) {
        Set<Category> categories = LibraryManager.getLibrary().getCategories();
        System.out.println();
        for(Category category : categories) {
            System.out.println(category.toString());
        }
    }

    @FXML
    void printMonthsYearsMap(ActionEvent event) {
        Map<YearMonth, List<Book>> yearsMonthsMap = LibraryManager.getLibrary().getMonthsYearsMap();
        for (YearMonth yearMonth : yearsMonthsMap.keySet()) {
            System.out.println("Key: " + yearMonth);
            List<Book> bookList = yearsMonthsMap.get(yearMonth);
            for(Book book : bookList) {
                System.out.println(book);
            }
        }
    }
}