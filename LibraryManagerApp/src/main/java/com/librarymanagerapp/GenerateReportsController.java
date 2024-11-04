package com.librarymanagerapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class GenerateReportsController {
    @FXML
    private Button buttonAuthorReport;
    @FXML
    private Button buttonBorrowDateReport;
    @FXML
    private Button buttonGenreReport;

    @FXML
    void onAuthorReportClick(ActionEvent event) {
        try {
            LibraryManager.switchScene("author-report-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onBorrowDateReportClick(ActionEvent event) {

    }

    @FXML
    void onGenreReportClick(ActionEvent event) {
        try {
            LibraryManager.switchScene("categories-report-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToMainMenu(ActionEvent event) {
        try {
            LibraryManager.switchScene("main-menu-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
