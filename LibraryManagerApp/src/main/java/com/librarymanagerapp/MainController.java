package com.librarymanagerapp;

import com.librarymanagerapp.model.Book;
import com.librarymanagerapp.model.Category;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainController {

    @FXML
    private Button buttonDisplayMonthsYearMap;
    @FXML
    private Button buttonPrintAllBooks;
    @FXML
    private Button buttonRemoveBook;
    @FXML
    private Button buttonResetLibrary;
    @FXML
    private Button buttonSearchBook;
    @FXML
    private Button buttonViewReports;
    @FXML
    private Button buttonDisplayCategories;
    @FXML
    private Button buttonDisplayAuthors;

    private Stage stage;
    private Scene scene;
    private Parent root;
    boolean developerButtonsVisible = false;

    public void initialize() {
        buttonPrintAllBooks.setVisible(false);
        buttonDisplayAuthors.setVisible(false);
        buttonResetLibrary.setVisible(false);
        buttonDisplayCategories.setVisible(false);
        buttonDisplayMonthsYearMap.setVisible(false);
    }

    public void switchToAddBookScene(ActionEvent event) {
        try {
            Scene scene = LibraryManager.switchScene("add-book-view.fxml");
            scene.getStylesheets().add(getClass().getResource("css/index.css").toExternalForm());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToRemoveBookScene(ActionEvent event) {
        try {
            Scene scene = LibraryManager.switchScene("remove-book-view.fxml");
            scene.getStylesheets().add(getClass().getResource("css/index.css").toExternalForm());
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
    void onBookSearchSwitch(ActionEvent event) {
        try {
            Scene scene = LibraryManager.switchScene("search-book-view.fxml");
            scene.getStylesheets().add(getClass().getResource("css/index.css").toExternalForm());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onReportsMenu(ActionEvent event) {
        try {
            Scene scene = LibraryManager.switchScene("generate-reports-view.fxml");
            scene.getStylesheets().add(getClass().getResource("css/index.css").toExternalForm());
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

    @FXML
    void showDeveloperFunctions(ActionEvent event) {
        if (!developerButtonsVisible) {
            buttonPrintAllBooks.setVisible(true);
            buttonDisplayAuthors.setVisible(true);
            buttonResetLibrary.setVisible(true);
            buttonDisplayCategories.setVisible(true);
            buttonDisplayMonthsYearMap.setVisible(true);

            developerButtonsVisible = true;
        } else {
            buttonPrintAllBooks.setVisible(false);
            buttonDisplayAuthors.setVisible(false);
            buttonResetLibrary.setVisible(false);
            buttonDisplayCategories.setVisible(false);
            buttonDisplayMonthsYearMap.setVisible(false);

            developerButtonsVisible = false;
        }
    }
}