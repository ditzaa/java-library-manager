package com.librarymanagerapp;

import com.librarymanagerapp.model.Book;
import com.librarymanagerapp.model.Library;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RemoveBookController {
    @FXML
    TextField textFieldBookToDelete;
    @FXML
    TableView<Book> tableViewBooksToDelete = new TableView<Book>();
    @FXML
    private TableColumn<Book, Integer> columnBookId;
    @FXML
    private TableColumn<Book, String> columnTitle;

    ObservableList<Book> booksToRemove = FXCollections.observableArrayList();

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

        List<String> nisteAutori = new ArrayList<>();
        LocalDate data = LocalDate.ofEpochDay(2024-10-15);
        nisteAutori.add("Un autor");
        booksToRemove = FXCollections.observableArrayList(
                new Book("title", nisteAutori, "genre", data)
        );
        tableViewBooksToDelete.setItems(booksToRemove);
    }

    public void searchBookToRemove() {
        //booksToRemove = FXCollections.observableArrayList();
        String title = textFieldBookToDelete.getText();
        Library library = LibraryManager.getLibrary();
        List<Book> libraryBooks = library.getBooks();
        for(Book book : libraryBooks) {
            if(title.equals(book.getTitle())) {
                booksToRemove.add(book);
                System.out.println(booksToRemove);
                //tableViewBooksToDelete.setItems(booksToRemove);
            }
        }
    }
}
