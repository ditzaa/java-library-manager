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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RemoveBookController {
    @FXML
    TextField textFieldBookToDelete;
    @FXML
    TableView<Book> tableViewBooksToDelete = new TableView<Book>();
    @FXML
    private TableColumn<Book, Integer> columnBookId;
    @FXML
    private TableColumn<Book, String> columnTitle;
    @FXML
    private Button buttonDeleteBook;

    ObservableList<Book> booksToRemove = FXCollections.observableArrayList();
    Book bookToRemove;

    public void switchToMainMenu(ActionEvent event) {
        try {
            LibraryManager.switchScene("main-menu-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {
        columnBookId.setCellValueFactory(new PropertyValueFactory<Book, Integer>("idBook"));
        columnTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));

        tableViewBooksToDelete.setPlaceholder(new Label(""));
        tableViewBooksToDelete.setItems(booksToRemove);
    }

    public void searchBookToRemove() {
        if ("".equals(textFieldBookToDelete.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenție");
            alert.setHeaderText("Informații invalide");
            alert.setContentText("Completează câmpul corespunzător titlului înainte de a căuta o carte.");
            alert.showAndWait();
        } else {
            booksToRemove = FXCollections.observableArrayList();
            String title = textFieldBookToDelete.getText();
            Library library = LibraryManager.getLibrary();
            List<Book> libraryBooks = library.getBooks();
            for(Book book : libraryBooks) {
                if(title.equals(book.getTitle())) {
                    booksToRemove.add(book);
                }
            }

            if (booksToRemove.isEmpty()) {
                tableViewBooksToDelete.setPlaceholder(new Label("Nici o carte cu acest titlu"));
            } else {
                tableViewBooksToDelete.setItems(booksToRemove);
            }
        }
    }

    @FXML
    void onDeleteBook(ActionEvent event) {
        LibraryManager.getLibrary().removeBook(bookToRemove);

        textFieldBookToDelete.setText("");
        booksToRemove = FXCollections.observableArrayList();
        tableViewBooksToDelete.setItems(booksToRemove);

        //delete book from authors map (author - list<Book>)
        //1. Get the book to remove + its authors
        List<String> authorsOfRemovedBook = bookToRemove.getAuthors();
        Library library = LibraryManager.getLibrary();
        Map<String, List<Book>> authorsMap = library.getAuthorsMap();
        //2. for each author remove the book from its books list
        for(String author : authorsOfRemovedBook) {
            List<Book> listOfBooks = authorsMap.get(author);
            listOfBooks.remove(bookToRemove);
        }
    }

    @FXML
    void onSelectBookToRemove(MouseEvent event) {
        bookToRemove = tableViewBooksToDelete.getSelectionModel().getSelectedItem();
    }
}
