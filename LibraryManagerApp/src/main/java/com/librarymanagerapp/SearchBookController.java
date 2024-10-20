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
        System.out.println(selectedBook);
    }

    //pt a cauta o carte dupa titlu - OK
    @FXML
    void onBookSearch(ActionEvent event) {
        booksToSearch = FXCollections.observableArrayList();
        String title = textFieldSearchBook.getText();
        Library library = LibraryManager.getLibrary();
        List<Book> libraryBooks = library.getBooks();
        for(Book book : libraryBooks) {
            if(title.equals(book.getTitle())) {
                booksToSearch.add(book);
            }
        }
        tableViewBooks.setItems(booksToSearch);
    }

    @FXML
    void switchToMainMenu(ActionEvent event) {
        try {
            LibraryManager.switchScene("main-menu-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //pt a deschide un nou view cu cartea respectiva
//    @FXML
//    void onSelectBookToDisplayDetails(MouseEvent event) {
//
//    }

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
