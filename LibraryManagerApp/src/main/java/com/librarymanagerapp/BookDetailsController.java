package com.librarymanagerapp;

import com.librarymanagerapp.model.Book;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class BookDetailsController {

    @FXML
    private Label labelBookTitle;
    @FXML
    private Button buttonBack;
    @FXML
    private Button buttonBorrowReturnBook;
    @FXML
    private Button buttonEditBookInfo;
    @FXML
    private Label labelBorrowDate;
    @FXML
    private Label labelGenre;
    @FXML
    private Label labelPublicationDate;
    @FXML
    private Label labelReturnDate;
    @FXML
    private Label labelStatus;
    @FXML
    private ListView<String> listViewAuthors;
    @FXML
    private TextField textFieldCurrentReader;

    private Book selectedBook = LibraryManager.getCurrentSelectedBook();

    public void initialize() {
        labelBookTitle.setText(selectedBook.getTitle());
    }

}
