package com.librarymanagerapp;

import com.librarymanagerapp.model.Book;
import com.librarymanagerapp.model.Library;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GenerateReportsController {
    @FXML
    private Button buttonAuthorReport;
    @FXML
    private Button buttonBorrowDateReport;
    @FXML
    private Button buttonGenreReport;

    Library library = LibraryManager.getLibrary();
    int[] nbTopBorrowings = library.getNbTopBorrowings();
    String[] titlesTopBorrowings = library.getTitlesTopBorrowings();
    List<Book> books = library.getBooks();

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
        try {
            LibraryManager.switchScene("publication-date-report-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    @FXML
    void onTopBorrowedBooksClick(ActionEvent event) {
        String fileName = "Top 10 Most Borrowed Books.txt";

        FileChooser.ExtensionFilter extFilter1 = new FileChooser.ExtensionFilter("Text files",
                "*.txt");
        FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("All files",
                "*.*");

        Stage stageFileSave = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Alege locul de salvare al fișierului");
        fileChooser.setInitialFileName(fileName);
        fileChooser.getExtensionFilters().addAll(extFilter1, extFilter2);
        fileChooser.setInitialDirectory(new File("./Reports"));
        stageFileSave.setResizable(true);
        File fileReport = fileChooser.showSaveDialog(stageFileSave);

        if (fileReport != null) {
            new Thread(() -> {
                try (FileWriter fileWriter = new FileWriter(fileReport)) {
                    fileWriter.write("Top 10 Cele Mai Împrumutate Cărți");
                    String delimitatorLine = "\n----------------------------------------------------------------------------------------------------------------------------";
                    fileWriter.write(delimitatorLine);
                    fileWriter.write("\nNr.   Titlu                                    Autor                     Gen                       Împrumuturi");
                    fileWriter.write(delimitatorLine);

                    for (int i = 0; i < titlesTopBorrowings.length; i++) {
                        if (titlesTopBorrowings[i] != null) {
                            String bookName = titlesTopBorrowings[i];
                            Book book = books.stream()
                                    .filter(b -> b.getTitle().equals(bookName))
                                    .findFirst()
                                    .orElse(null);

                            if (book != null) {
                                fileWriter.write(String.format("\n%-5d %-40s %-25s %-25s %-5d",
                                        (i + 1),
                                        book.getTitle(),
                                        book.getAuthors().get(0),
                                        book.getGenre(),
                                        nbTopBorrowings[i]));
                            }
                        }
                    }

                    fileWriter.write(delimitatorLine);

                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Creare raport");
                        alert.setHeaderText("Raport creat cu succes!");
                        alert.showAndWait();
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}