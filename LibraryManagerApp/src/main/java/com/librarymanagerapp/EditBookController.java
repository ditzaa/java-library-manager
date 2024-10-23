package com.librarymanagerapp;

import com.librarymanagerapp.model.Book;
import com.librarymanagerapp.util.InputValidator;
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
import java.util.ArrayList;
import java.util.List;

public class EditBookController {

    @FXML
    private TextField authorTextField;
    @FXML
    private ListView<String> authorsListView;
    @FXML
    private Button buttonAddAuthor;
    @FXML
    private Button buttonRemoveAuthor;
    @FXML
    private Button buttonEditBook;
    @FXML
    private Button buttonBackToBookDetails;
    @FXML
    private TextField genreTextField;
    @FXML
    private DatePicker publishDateTextField;
    @FXML
    private TextField titleTextField;
    @FXML
    private Label labelEditConfirmation;

    private Book selectedBook = LibraryManager.getCurrentSelectedBook();
    private ObservableList<String> authors = FXCollections.observableArrayList();
    private List<String> authorsSelectedBook = selectedBook.getAuthors();

    public void initialize() {
        titleTextField.setText(selectedBook.getTitle());
        for (String author : authorsSelectedBook) {
            authors.add(author);
        }
        authorsListView.setItems(authors);
        genreTextField.setText(selectedBook.getGenre());
        publishDateTextField.setValue(selectedBook.getPublicationDate());
    }

    @FXML
    void onEditBook(ActionEvent event) {
        String title = titleTextField.getText();
        List<String> authorsList = new ArrayList<>();
        for (String author : authors) {
            authorsList.add(author);
        }
        String genre = genreTextField.getText();
        LocalDate publicationDate = publishDateTextField.getValue();
        if (InputValidator.validateBookAdd(title, authorsList, genre, publicationDate)) {
            List<Book> libraryBooks = LibraryManager.getLibrary().getBooks();
            for (Book book : libraryBooks) {
                if (book.getTitle().equals(selectedBook.getTitle())) {
                    book.setTitle(title);
                    book.setAuthors(authorsList);
                    book.setGenre(genre);
                    book.setPublicationDate(publicationDate);
                }
            }

            labelEditConfirmation.setText("Carte editată cu succes!");
            labelEditConfirmation.setVisible(true);

            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(3),
                    event1 -> labelEditConfirmation.setVisible(false)
            ));
            timeline.setCycleCount(1);
            timeline.play();
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenție");
            alert.setHeaderText("Informații lipsă sau invalide");
            alert.setContentText("Completează toate câmpurile corespunzător înainte de a edita detaliile unei cărți.");
            alert.showAndWait();
        }
    }

    @FXML
    void onAddNewAuthor(ActionEvent event) {
        if ("".equals(authorTextField.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenție");
            alert.setHeaderText("Informații invalide");
            alert.setContentText("Completează câmpul cu numele autorului corespunzător înainte de a-l adăuga.");
            alert.showAndWait();
        }
        String author = authorTextField.getText();
        authors.add(author);
        authorTextField.setText("");
    }

    @FXML
    void onRemoveAuthor(ActionEvent event) {
        ObservableList<String> authorsToDelete = authorsListView.getSelectionModel().getSelectedItems();
        authors.removeAll(authorsToDelete);
    }

    @FXML
    void switchToBookDetails(ActionEvent event) {
        try {
            LibraryManager.switchScene("details-book-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
