package com.librarymanagerapp;

import com.librarymanagerapp.model.Book;
import com.librarymanagerapp.model.Library;
import com.librarymanagerapp.util.EmptySearchFieldException;
import com.librarymanagerapp.util.NoBookSelectedException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;

public class SearchBookController {
    @FXML
    private Button buttonBack;
    @FXML
    private Button buttonSearchBook;
    @FXML
    private Button buttonSelectBook;
    @FXML
    private TableView<Book> tableViewBooks;
    @FXML
    private TextField textFieldSearchBook;
    @FXML
    private TableColumn<Book, Integer> columnBookId;
    @FXML
    private TableColumn<Book, String> columnTitle;

    ObservableList<Book> booksToSearch = FXCollections.observableArrayList();
    Book selectedBook;

    public void initialize() {
        columnBookId.setCellValueFactory(new PropertyValueFactory<Book, Integer>("idBook"));
        columnTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));

        tableViewBooks.setPlaceholder(new Label(""));
        tableViewBooks.setItems(booksToSearch);
    }

    @FXML
    void onSelectBook(MouseEvent event) {
        selectedBook = tableViewBooks.getSelectionModel().getSelectedItem();
    }

    @FXML
    void onBookSearch(ActionEvent event) {
        try {
            if (textFieldSearchBook.getText().trim().isEmpty()) {
                throw new EmptySearchFieldException("Completează câmpul cu titlul cărții înainte de a căuta.");
            }

            booksToSearch.clear();
            String title = textFieldSearchBook.getText();
            Library library = LibraryManager.getLibrary();
            List<Book> libraryBooks = library.getBooks();

            for (Book book : libraryBooks) {
                if (title.equals(book.getTitle())) {
                    booksToSearch.add(book);
                }
            }

            if (booksToSearch.isEmpty()) {
                tableViewBooks.setPlaceholder(new Label("Nicio carte cu acest titlu"));
            }
            tableViewBooks.setItems(booksToSearch);

        } catch (EmptySearchFieldException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenție");
            alert.setHeaderText("Informații invalide");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void onSelectBookToDisplayDetails(ActionEvent event) {
        try {
            if (selectedBook == null) {
                throw new NoBookSelectedException("Selectează o carte înainte de a afișa detaliile.");
            }

            LibraryManager.setCurrentSelectedBook(selectedBook);
            LibraryManager.switchScene("details-book-view.fxml");

        } catch (NoBookSelectedException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Eroare de selecție");
            alert.setHeaderText("Nicio carte selectată");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare de încărcare");
            alert.setHeaderText("Nu s-a putut încărca pagina detaliilor cărții.");
            alert.showAndWait();
        }
    }

    @FXML
    void switchToMainMenu(ActionEvent event) {
        try {
            LibraryManager.switchScene("main-menu-view.fxml");
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Eroare de încărcare");
            alert.setHeaderText("Nu s-a putut încărca meniul principal.");
            alert.setContentText("Verifică fișierul de resurse și încearcă din nou.");
            alert.showAndWait();
        }
    }
}