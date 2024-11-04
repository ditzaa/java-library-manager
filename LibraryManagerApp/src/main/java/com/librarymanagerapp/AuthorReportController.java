package com.librarymanagerapp;

import com.librarymanagerapp.model.Book;
import com.librarymanagerapp.model.Library;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AuthorReportController {

    @FXML
    private Button buttonGenerateAuthorReport;
    @FXML
    private Button buttonSearchAuthor;
    @FXML
    private ListView<String> listViewAuthors;
    @FXML
    private TextField textFieldAuthorName;

    ObservableList<String> authors = FXCollections.observableArrayList();
    String selectedAuthor;
    Map<String, List<Book>> authorsMap;

    @FXML
    void onAuthorReportGenerate(ActionEvent event) {
        String fileName = selectedAuthor + " Author Report.txt";
        List<Book> booksListOfSelectedAuthor = authorsMap.get(selectedAuthor);
        int numberOfBooks = booksListOfSelectedAuthor.size();

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
                    fileWriter.write(selectedAuthor + " - Raport de Autor");
                    String delimitatorLine = "\n--------------------------------------------" +
                            "----------------------------------------------";
                    fileWriter.write(delimitatorLine);
                    fileWriter.write("\nNumărul de cărți scrise de autor în bibliotecă: " + numberOfBooks + "\n");
                    fileWriter.write("\nNr.   Titlu                                    Gen" +
                            "                       Data Publicării");
                    fileWriter.write(delimitatorLine);

                    int indexBooks = 1;
                    for (Book book : booksListOfSelectedAuthor) {
                        fileWriter.write(String.format("\n%-5d %-40s %-25s %s",
                                indexBooks,
                                book.getTitle(),
                                book.getGenre(),
                                book.getPublicationDate()));
                        indexBooks++;
                    }
                    fileWriter.write(delimitatorLine);

                    // Afișare alert pe JavaFX Application Thread
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
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

    @FXML
    void onAuthorSearch(ActionEvent event) {
        if ("".equals(textFieldAuthorName.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenție");
            alert.setHeaderText("Informații invalide");
            alert.setContentText("Completează câmpul corespunzător înainte de a căuta un autor.");
            alert.showAndWait();
        } else {
            authors = FXCollections.observableArrayList();
            Library library = LibraryManager.getLibrary();
            authorsMap = library.getAuthorsMap();
            String authorName = textFieldAuthorName.getText();

            if (authorsMap.containsKey(authorName)) {
                authors.add(authorName);
            }

            if (authors.isEmpty()) {
                listViewAuthors.setItems(null);
                listViewAuthors.setPlaceholder(new Label("Nici un autor cu acest nume"));
            } else {
                listViewAuthors.setItems(authors);
            }
        }
    }

    public void initialize() {
        listViewAuthors.setItems(authors);
    }

    @FXML
    void switchReportsMenu(ActionEvent event) {
        try {
            LibraryManager.switchScene("generate-reports-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onAuhorSelect(MouseEvent event) {
        selectedAuthor =  listViewAuthors.getSelectionModel().getSelectedItem();
    }
}