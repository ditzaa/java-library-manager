package com.librarymanagerapp;

import com.librarymanagerapp.model.Library;
import com.librarymanagerapp.services.SaveFilesManager;
import com.librarymanagerapp.util.InputValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class LibraryManager extends Application {
    private static Stage mainStage;

    private static Library library = SaveFilesManager.reloadLibraryBooks();
    private static InputValidator inputValidator = new InputValidator();

    public LibraryManager() throws FileNotFoundException {
    }

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        switchScene("main-menu-view.fxml");
        stage.setTitle("BookFlow");
        stage.show();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            SaveFilesManager.saveLibraryBooks();
        }));
    }

    public static void main(String[] args) {
        launch();
    }

    public static void switchScene(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibraryManager.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load());
        mainStage.setScene(scene);
    }

    public static Library getLibrary() {
        return library;
    }
}