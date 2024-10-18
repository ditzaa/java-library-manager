package gui.librarymanager;

import javafx.event.ActionEvent;

import java.io.IOException;

public class RemoveBookController {
    public void switchToMainMenu(ActionEvent event) {
        try {
            LibraryManager.switchScene("MainMenu.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
