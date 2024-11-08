package com.librarymanagerapp;

import com.librarymanagerapp.model.Book;
import com.librarymanagerapp.model.Library;
import com.librarymanagerapp.util.InvalidReaderException;
import com.librarymanagerapp.util.LibraryMapUpdateException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

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

    Library library = LibraryManager.getLibrary();
    private Book selectedBook = LibraryManager.getCurrentSelectedBook();
    private ObservableList<String> authors = FXCollections.observableArrayList();
    private List<String> authorsSelectedBook = selectedBook.getAuthors();
    Map<YearMonth, List<Book>> monthsYearsMap = LibraryManager.getLibrary().getMonthsYearsMap();

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
        } else {
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
            Scene scene = LibraryManager.switchScene("main-menu-view.fxml");
            scene.getStylesheets().add(getClass().getResource("css/index.css").toExternalForm());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onBookStatusChange(ActionEvent event) {
        try {
            String readerName = textFieldCurrentReader.getText();

            if (!readerName.matches("[a-zA-Z ]+")) {
                throw new InvalidReaderException("Numele cititorului trebuie să conțină doar litere.");
            }

            if ("".equals(selectedBook.getCurrentReader())) {
                // Împrumută cartea
                System.out.println("Împrumută cartea");
                labelStatus.setText("Împrumutată");
                selectedBook.setCurrentReader(readerName);
                selectedBook.setBorrowedDate(LocalDate.now());
                LocalDate borrowedDate = selectedBook.getBorrowedDate();
                labelBorrowDate.setText(borrowedDate.toString());
                labelReturnDate.setText(borrowedDate.plusDays(21).toString());
                textFieldCurrentReader.setEditable(false);
                buttonBorrowReturnBook.setText("Returnează");

                try {
                    YearMonth yearMonthBorrowedDate = YearMonth.of(borrowedDate.getYear(), borrowedDate.getMonth());
                    library.addBorrowedDate(yearMonthBorrowedDate, selectedBook);
                } catch (Exception e) {
                    throw new LibraryMapUpdateException("A apărut o eroare la actualizarea mapei cu datele de împrumut.");
                }

                selectedBook.setNumberOfBorrowings(selectedBook.getNumberOfBorrowings() + 1);
                library.checkIfTopBorrowing(selectedBook);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
        } catch (InvalidReaderException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare");
            alert.setHeaderText("Numele cititorului este invalid");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (LibraryMapUpdateException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare");
            alert.setHeaderText("Eroare la actualizarea mapei");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    void onBookEditOption(ActionEvent event) {
        try {
            Scene scene = LibraryManager.switchScene("edit-book-view.fxml");
            scene.getStylesheets().add(getClass().getResource("css/index.css").toExternalForm());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void switchToBookSearch(ActionEvent event) {
        try {
            Scene scene = LibraryManager.switchScene("search-book-view.fxml");
            scene.getStylesheets().add(getClass().getResource("css/index.css").toExternalForm());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}