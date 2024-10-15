package gui.librarymanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Book;
import model.Library;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddBookController {

    @FXML
    private TextField authorTextField;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField genreTextField;
    @FXML
    private  ListView<String> authorsListView;
    @FXML
    private DatePicker publishDateTextField;
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
        System.out.println(authorsList);
        String genre = genreTextField.getText();
        LocalDate publicationDate = publishDateTextField.getValue();
        Book newBook = new Book(title, authorsList, genre, publicationDate);
        System.out.println(newBook.toString());
        LibraryManager.getLibrary().addBook(newBook);
        System.out.println(LibraryManager.getLibrary());
    }

    public void initialize() {
        authorsListView.setItems(authors);
    }

    @FXML
    public void onAddNewAuthor(ActionEvent event) {
        String author = authorTextField.getText();
        authors.add(author);
        System.out.println(authors);
        authorTextField.setText("");
    }

    @FXML
    public void onRemoveAuthor(ActionEvent event) {
        ObservableList<String> authorsToDelete = authorsListView.getSelectionModel().getSelectedItems();
        authors.removeAll(authorsToDelete);
    }
}
