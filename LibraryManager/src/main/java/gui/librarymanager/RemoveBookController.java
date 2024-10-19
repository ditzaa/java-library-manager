package gui.librarymanager;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Book;
import model.BookProperty;
import model.Library;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

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
            LibraryManager.switchScene("MainMenu.fxml");
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
