package gui.librarymanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Library;
import util.InputValidator;

import javax.xml.validation.Validator;
import java.io.IOException;

public class LibraryManager extends Application {
    private static Stage mainStage;
    private static Library library = new Library();
    private static InputValidator inputValidator = new InputValidator();

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        switchScene("MainMenu.fxml");
        stage.setTitle("BookFlow");
        stage.show();
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