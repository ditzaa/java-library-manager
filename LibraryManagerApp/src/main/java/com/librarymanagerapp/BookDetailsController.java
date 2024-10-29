package com.librarymanagerapp;

import com.librarymanagerapp.model.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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
    private ObservableList<String> authors = FXCollections.observableArrayList();
    private List<String> authorsSelectedBook = selectedBook.getAuthors();

    public void initialize() {
        labelBookTitle.setText(selectedBook.getTitle());
        for (String author : authorsSelectedBook) {
            authors.add(author);
        }
        listViewAuthors.setItems(authors);
        labelGenre.setText(selectedBook.getGenre());
        labelPublicationDate.setText(selectedBook.getPublicationDate().toString());
        if ("".equals(selectedBook.getCurrentReader())) {
            labelStatus.setText("Disponibilă");
        } else {
          labelStatus.setText(selectedBook.getCurrentReader());
        }
        if ("".equals(selectedBook.getCurrentReader())) {
            textFieldCurrentReader.setText("");
        } else {
            textFieldCurrentReader.setText(selectedBook.getCurrentReader());
        }
        if (selectedBook.isBorrowed()) {
            labelBorrowDate.setText(selectedBook.getBorrowedDate().toString());
            LocalDate returnDate = selectedBook.getBorrowedDate().plusDays(21);
            labelReturnDate.setText(returnDate.toString());
        } else {
            labelBorrowDate.setText("Nu există");
            labelBorrowDate.setText("Nu există");
        }
        if ("".equals(selectedBook.getCurrentReader())) {
            //returneaza cartea
            labelStatus.setText("Disponibilă");
            labelBorrowDate.setText("Nu există");
            labelReturnDate.setText("Nu există");
            textFieldCurrentReader.setEditable(true);
            textFieldCurrentReader.setText("");
            buttonBorrowReturnBook.setText("Împrumută");
        } else /*if ( != "" "".equals(selectedBook.getCurrentReader()))*/ {
            //imprumuta cartea
            labelStatus.setText("Împrumutată");
            LocalDate borrowedDate = selectedBook.getBorrowedDate();
            labelBorrowDate.setText(borrowedDate.toString());
            labelReturnDate.setText(borrowedDate.plusDays(21).toString());
            textFieldCurrentReader.setEditable(false);
            buttonBorrowReturnBook.setText("Returnează");
        }
    }

    @FXML
    void switchToMainMenu(ActionEvent event) {
        try {
            LibraryManager.switchScene("main-menu-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onBookStatusChange(ActionEvent event) {
        if ("".equals(selectedBook.getCurrentReader())) {
            //imprumuta cartea
            System.out.println("Imprumuta cartea");
            labelStatus.setText("Împrumutată");
            selectedBook.setCurrentReader(textFieldCurrentReader.getText());
            selectedBook.setBorrowedDate(LocalDate.now());
            LocalDate borrowedDate = selectedBook.getBorrowedDate();
            labelBorrowDate.setText(borrowedDate.toString());
            labelReturnDate.setText(borrowedDate.plusDays(21).toString());
            textFieldCurrentReader.setEditable(false);
            buttonBorrowReturnBook.setText("Returnează");

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmare");
            alert.setHeaderText("Împrumutul cărții a fost realizat cu succes!");
            alert.showAndWait();
        } else {
            //returneaza cartea
            labelStatus.setText("Disponibilă");
            selectedBook.setBorrowedDate(null);
            labelBorrowDate.setText("Nu există");
            labelReturnDate.setText("Nu există");
            selectedBook.setCurrentReader("");
            textFieldCurrentReader.setEditable(true);
            textFieldCurrentReader.setText("");
            buttonBorrowReturnBook.setText("Împrumută");
        }
    }

    @FXML
    void onBookEditOption(ActionEvent event) {
        try {
            LibraryManager.switchScene("edit-book-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
