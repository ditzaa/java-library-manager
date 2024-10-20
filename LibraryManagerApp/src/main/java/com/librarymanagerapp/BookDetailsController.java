package com.librarymanagerapp;

import com.librarymanagerapp.model.Book;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BookDetailsController {
    @FXML
    private Label labelBookTitle;

    private Book selectedBook = LibraryManager.getCurrentSelectedBook();

    public void initialize() {
        labelBookTitle.setText(selectedBook.getTitle());
        System.out.println(selectedBook.getTitle());
    }
}
