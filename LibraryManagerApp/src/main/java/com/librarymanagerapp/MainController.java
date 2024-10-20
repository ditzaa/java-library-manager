package com.librarymanagerapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

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
        LibraryManager.getLibrary().displayBooks();
    }
}