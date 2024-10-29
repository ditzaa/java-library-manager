package com.librarymanagerapp;

import com.librarymanagerapp.model.Book;
import com.librarymanagerapp.model.Library;
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
        selectedBook =  tableViewBooks.getSelectionModel().getSelectedItem();
    }

    //pt a cauta o carte dupa titlu - OK
    @FXML
    void onBookSearch(ActionEvent event) {
        if ("".equals(textFieldSearchBook.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenție");
            alert.setHeaderText("Informații invalide");
            alert.setContentText("Completează câmpul cu titlul cărții corespunzător înainte de a căuta o carte.");
            alert.showAndWait();
        } else {
            booksToSearch = FXCollections.observableArrayList();
            String title = textFieldSearchBook.getText();
            Library library = LibraryManager.getLibrary();
            List<Book> libraryBooks = library.getBooks();
            for(Book book : libraryBooks) {
                if(title.equals(book.getTitle())) {
                    booksToSearch.add(book);
                }
            }
            if (booksToSearch.isEmpty()) {
                tableViewBooks.setPlaceholder(new Label("Nici o carte cu acest titlu"));
            } else {
                tableViewBooks.setItems(booksToSearch);
            }
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
    void onSelectBookToDisplayDetails(ActionEvent event) {
        LibraryManager.setCurrentSelectedBook(selectedBook);
        try {
            LibraryManager.switchScene("details-book-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
