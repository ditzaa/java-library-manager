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
import javafx.scene.control.*;
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

    ObservableList<String> authors = FXCollections.observableArrayList();

    public void switchToMainMenu(ActionEvent event) {
        try {
            LibraryManager.switchScene("main-menu-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public void onAddBook(ActionEvent event) {
//        String title = titleTextField.getText();
//        List<String> authorsList = authors;
//        String genre = genreTextField.getText();
//        LocalDate publicationDate = publishDateTextField.getValue();
//        if (InputValidator.validateBookAdd(title, authorsList, genre, publicationDate)) {
//            Book newBook = new Book(title, authorsList, genre, publicationDate);
//            LibraryManager.getLibrary().addBook(newBook);
//            LibraryManager.getLibrary().addAuthor(authors, newBook);
//
//            titleTextField.setText("");
//            genreTextField.setText("");
//            publishDateTextField.setValue(null);
//            authorsListView.getItems().clear();
//
//            Set<Category> categories = LibraryManager.getLibrary().getCategories();
//            boolean categoryNotExisting = true;
//            for (Category category : categories) {
//                if (genre.equals(category.getName())) {
//                    categoryNotExisting = false;
//                    category.addBook(newBook);
//                }
//            }
//
//            if (categoryNotExisting) {
//                Category newCategory = new Category(genre);
//                newCategory.addBook(newBook);
//                categories.add(newCategory);
//            }
//
//            labelAddConfirmation.setText("Carte adaugată cu succes!");
//            labelAddConfirmation.setVisible(true);
//
//            Timeline timeline = new Timeline(new KeyFrame(
//                    Duration.seconds(3),
//                    event1 -> labelAddConfirmation.setVisible(false)
//            ));
//            timeline.setCycleCount(1);
//            timeline.play();
//        }else {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("Atenție");
//            alert.setHeaderText("Informații lipsă sau invalide");
//            alert.setContentText("Completează toate câmpurile corespunzător înainte de a adăuga o carte.");
//            alert.showAndWait();
//        }
//    }

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
}
