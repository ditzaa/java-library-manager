package com.librarymanagerapp;

import com.librarymanagerapp.model.Book;
import com.librarymanagerapp.model.Category;
import com.librarymanagerapp.util.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;

public class AddBookController {

    @FXML
    private TextField titleTextField;
    @FXML
    private TextField authorTextField;
    @FXML
    private ListView<String> authorsListView;
    @FXML
    private TextField genreTextField;
    @FXML
    private DatePicker publishDateTextField;
    @FXML
    private Label labelAddConfirmation;
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

    ObservableList<String> authors = FXCollections.observableArrayList();
    boolean developerButtonsVisible = false;

    public void switchToMainMenu(ActionEvent event) {
        try {
            Scene scene = LibraryManager.switchScene("main-menu-view.fxml");
            scene.getStylesheets().add(getClass().getResource("css/index.css").toExternalForm());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onAddBook(ActionEvent event) {
        String title = titleTextField.getText();
        List<String> authorsList = authors;
        String genre = genreTextField.getText();
        LocalDate publicationDate = publishDateTextField.getValue();

        try {
            if (title.isEmpty() || authorsList.isEmpty() || genre.isEmpty() || publicationDate == null) {
                throw new InvalidBookDataException("Completează toate câmpurile corespunzător înainte de a adăuga o carte.");
            }

            if (!genre.matches("[a-zA-Z ]+")) {
                throw new InvalidGenreException("Genul cărții trebuie să conțină doar litere și spații.");
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            try {
                publicationDate.format(formatter);
            } catch (DateTimeParseException e) {
                throw new InvalidBookDataException("Data publicării trebuie să fie în formatul dd/MM/yyyy.");
            }

            Book newBook = new Book(title, authorsList, genre, publicationDate);
            LibraryManager.getLibrary().addBook(newBook);
            LibraryManager.getLibrary().addAuthor(authors, newBook);

            titleTextField.setText("");
            genreTextField.setText("");
            publishDateTextField.setValue(null);
            authorsListView.getItems().clear();

            labelAddConfirmation.setText("Carte adaugată cu succes!");
            labelAddConfirmation.setVisible(true);

            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(3),
                    event1 -> labelAddConfirmation.setVisible(false)
            ));
            timeline.setCycleCount(1);
            timeline.play();

        } catch (InvalidBookDataException | InvalidGenreException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenție");
            alert.setHeaderText("Informații lipsă sau invalide");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void initialize() {
        authorsListView.setItems(authors);
    }

    @FXML
    public void onAddNewAuthor(ActionEvent event) {
        String author = authorTextField.getText();
        try {
            if (author.isEmpty()) {
                throw new InvalidAuthorException("Completează câmpul cu numele autorului.");
            }
            if (!author.matches("[a-zA-Z ]+")) {
                throw new InvalidAuthorException("Numele autorului trebuie să conțină doar litere și spații.");
            }

            authors.add(author);
            authorTextField.setText("");
        } catch (InvalidAuthorException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenție");
            alert.setHeaderText("Informații invalide");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void onRemoveAuthor(ActionEvent event) {
        ObservableList<String> authorsToDelete = authorsListView.getSelectionModel().getSelectedItems();
        authors.removeAll(authorsToDelete);
    }

    @FXML
    public void displayBooks(ActionEvent event){
        System.out.println();
        LibraryManager.getLibrary().displayBooks();
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

    public void switchToRemoveBookScene(ActionEvent event) {
        try {
            Scene scene = LibraryManager.switchScene("remove-book-view.fxml");
            scene.getStylesheets().add(getClass().getResource("css/index.css").toExternalForm());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToAddBookScene(ActionEvent event) {
        try {
            Scene scene = LibraryManager.switchScene("add-book-view.fxml");
            scene.getStylesheets().add(getClass().getResource("css/index.css").toExternalForm());
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    @FXML
    void onBookSearchSwitch(ActionEvent event) {
        try {
            Scene scene = LibraryManager.switchScene("search-book-view.fxml");
            scene.getStylesheets().add(getClass().getResource("css/index.css").toExternalForm());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
