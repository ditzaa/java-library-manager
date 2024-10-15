package gui.librarymanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Library;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddBookController {

    @FXML
    private TextField authorTextField;
    @FXML
    private ListView<String> authorsListView;
    ObservableList<String> authors = FXCollections.observableArrayList();

    public void switchToMainMenu(ActionEvent event) {
        try {
            LibraryManager.switchScene("MainMenu.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onAddBook(ActionEvent event) {
        //LibraryManager.getLibrary().addBook(newBook);
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
        //System.out.println(authorsToDelete);
        authors.removeAll(authorsToDelete);
        //authorsListView.getSelectionModel().clearSelection();
    }
}
