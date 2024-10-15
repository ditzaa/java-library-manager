package gui.librarymanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class AddBookController {

    @FXML

    public void switchToMainMenu(ActionEvent event) {
        try {
            LibraryManager.switchScene("MainMenu.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
