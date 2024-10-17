package gui.librarymanager;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import model.Book;
import model.Library;
import util.InputValidator;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddBookController {
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField authorTextField;
    @FXML
    private  ListView<String> authorsListView;
    @FXML
    private TextField genreTextField;
    @FXML
    private DatePicker publishDateTextField;
    @FXML
    private Label labelAddConfirmation;

    ObservableList<String> authors = FXCollections.observableArrayList();

    public void switchToMainMenu(ActionEvent event) {
        try {
            LibraryManager.switchScene("MainMenu.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onAddBook(ActionEvent event) {
        String title = titleTextField.getText();
        List<String> authorsList = authors;
        String genre = genreTextField.getText();
        LocalDate publicationDate = publishDateTextField.getValue();
        if (InputValidator.validateBookAdd(title, authorsList, genre, publicationDate)) {
            Book newBook = new Book(title, authorsList, genre, publicationDate);
            LibraryManager.getLibrary().addBook(newBook);

            titleTextField.setText("");
            genreTextField.setText("");
            publishDateTextField.setValue(null);
            authorsListView.getItems().clear();

            labelAddConfirmation.setText("Carte adaugata cu succes!");
            labelAddConfirmation.setVisible(true);

            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(3),
                    event1 -> labelAddConfirmation.setVisible(false)
            ));
            timeline.setCycleCount(1);
            timeline.play();
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenție");
            alert.setHeaderText("Informații lipsă sau invalide");
            alert.setContentText("Completează toate câmpurile corespunzător înainte de a adăuga o carte.");
            alert.showAndWait();
        }
    }

    public void initialize() {
        authorsListView.setItems(authors);
    }

    @FXML
    public void onAddNewAuthor(ActionEvent event) {
        String author = authorTextField.getText();
        authors.add(author);
        authorTextField.setText("");
    }

    @FXML
    public void onRemoveAuthor(ActionEvent event) {
        ObservableList<String> authorsToDelete = authorsListView.getSelectionModel().getSelectedItems();
        authors.removeAll(authorsToDelete);
    }
}
