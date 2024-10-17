package gui.librarymanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToAddBookScene(ActionEvent event) {
        try {
            LibraryManager.switchScene("AddBookScene.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void displayBooks(ActionEvent event){
        LibraryManager.getLibrary().displayBooks();
    }
}