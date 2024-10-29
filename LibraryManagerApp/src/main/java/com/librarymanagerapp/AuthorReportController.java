package com.librarymanagerapp;

import com.librarymanagerapp.model.Book;
import com.librarymanagerapp.model.Library;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;

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

    @FXML
    void onAuthorReportGenerate(ActionEvent event) {

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
//            authors = FXCollections.observableArrayList();
//            String title = textFieldSearchBook.getText();
//            Library library = LibraryManager.getLibrary();
//            List<Book> libraryBooks = library.getBooks();
//            for(Book book : libraryBooks) {
//                if(title.equals(book.getTitle())) {
//                    booksToSearch.add(book);
//                }
//            }
//            if (booksToSearch.isEmpty()) {
//                tableViewBooks.setPlaceholder(new Label("Nici o carte cu acest titlu"));
//            } else {
//                tableViewBooks.setItems(booksToSearch);
//            }
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
        //selectedBook =  tableViewBooks.getSelectionModel().getSelectedItem();
    }
}
