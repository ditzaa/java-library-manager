package com.librarymanagerapp;

import com.librarymanagerapp.model.Book;
import com.librarymanagerapp.model.Library;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BorrowDateReportController {

    @FXML
    private ComboBox<String> comboBoxMonth;
    @FXML
    private TextField textFieldYear;
    @FXML
    private ComboBox<Integer> comboBoxYear;

    Library library = LibraryManager.getLibrary();
    Map<YearMonth, List<Book>> monthsYearsMap = library.getMonthsYearsMap();
    ObservableList<String> months = FXCollections.observableArrayList(
            "Ianuarie", "Februarie", "Martie", "Aprilie", "Mai", "Iunie",
            "Iulie", "August", "Septembrie", "Octombrie", "Noiembrie", "Decembrie"
    );
    ObservableList<Integer> years = FXCollections.observableArrayList(
            IntStream.rangeClosed(Year.now().getValue() - 29, Year.now().getValue())
                    .boxed()
                    .sorted((a, b) -> b.compareTo(a))
                    .collect(Collectors.toList())
    );

    public void initialize() {
        comboBoxMonth.setItems(months);
        comboBoxYear.setItems(years);
    }

    @FXML
    void onGenerateDateReport(ActionEvent event) {
        String selectedMonth = comboBoxMonth.getValue();
        Integer selectedYear = comboBoxYear.getValue();

        if (selectedMonth == null || selectedYear == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atenție");
            alert.setHeaderText("Informații lipsă");
            alert.setContentText("Selectați luna și anul pentru generarea raportului.");
            alert.showAndWait();
            return;
        }

        int monthNumber;
        switch (selectedMonth) {
            case "Ianuarie": monthNumber = 1; break;
            case "Februarie": monthNumber = 2; break;
            case "Martie": monthNumber = 3; break;
            case "Aprilie": monthNumber = 4; break;
            case "Mai": monthNumber = 5; break;
            case "Iunie": monthNumber = 6; break;
            case "Iulie": monthNumber = 7; break;
            case "August": monthNumber = 8; break;
            case "Septembrie": monthNumber = 9; break;
            case "Octombrie": monthNumber = 10; break;
            case "Noiembrie": monthNumber = 11; break;
            case "Decembrie": monthNumber = 12; break;
            default: monthNumber = 0; break;
        }
        String fileName = selectedMonth + " " + selectedYear + " Borrowing Report.txt";
        YearMonth yearMonthBorrowedDate = YearMonth.of(selectedYear, monthNumber);
        List<Book> booksListOfSelectedPeriod = monthsYearsMap.get(yearMonthBorrowedDate);

        if (booksListOfSelectedPeriod == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenție");
            alert.setHeaderText("Eroare generare raport");
            alert.setContentText("Nu există nicio carte împrumutată în perioada selectată!");
            alert.showAndWait();
            return;
        }
        int numberOfBooks = booksListOfSelectedPeriod.size();

        FileChooser.ExtensionFilter extFilter1 = new FileChooser.ExtensionFilter("Text files",
                "*.txt");
        FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("All files",
                "*.*");

        Stage stageFileSave = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Alege locul de salvare al fișierului");
        fileChooser.setInitialFileName(fileName);
        fileChooser.getExtensionFilters().addAll(extFilter1, extFilter2);
        fileChooser.setInitialDirectory(new File("./Reports"));
        stageFileSave.setResizable(true);
        File fileReport = fileChooser.showSaveDialog(stageFileSave);

        if (fileReport != null) {
            new Thread(() -> {
                try (FileWriter fileWriter = new FileWriter(fileReport)) {
                    fileWriter.write(selectedMonth + " " + selectedYear + " - Raport al Cărților Împrumutate");
                    String delimitatorLine = "\n----------------------------------------------------------------------------------------------------------------------------";
                    fileWriter.write(delimitatorLine);
                    fileWriter.write("\nNumărul de cărți împrumutate: " + numberOfBooks + "\n");
                    fileWriter.write("\nNr.   Titlu                                    Autor                            Gen                              Cititor");
                    fileWriter.write(delimitatorLine);

                    int indexBooks = 1;
                    for (Book book : booksListOfSelectedPeriod) {
                        fileWriter.write(String.format("\n%-5d %-40s %-32s %-32s %-25s",
                                indexBooks,
                                book.getTitle(),
                                book.getAuthors().get(0),
                                book.getGenre(),
                                book.getCurrentReader()));
                        indexBooks++;
                    }

                    fileWriter.write(delimitatorLine);

                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Creare raport");
                        alert.setHeaderText("Raport creat cu succes!");
                        alert.showAndWait();
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    @FXML
    void switchToReportsMenu(ActionEvent event) {
        try {
            Scene scene = LibraryManager.switchScene("generate-reports-view.fxml");
            scene.getStylesheets().add(getClass().getResource("css/index.css").toExternalForm());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onReportsMenu(ActionEvent event) {
        try {
            Scene scene = LibraryManager.switchScene("generate-reports-view.fxml");
            scene.getStylesheets().add(getClass().getResource("css/index.css").toExternalForm());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToAddBookScene(ActionEvent event) {
        try {
            Scene scene = LibraryManager.switchScene("add-book-view.fxml");
            scene.getStylesheets().add(getClass().getResource("css/index.css").toExternalForm());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToRemoveBookScene(ActionEvent event) {
        try {
            Scene scene = LibraryManager.switchScene("remove-book-view.fxml");
            scene.getStylesheets().add(getClass().getResource("css/index.css").toExternalForm());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onBookSearchSwitch(ActionEvent event) {
        try {
            Scene scene = LibraryManager.switchScene("search-book-view.fxml");
            scene.getStylesheets().add(getClass().getResource("css/index.css").toExternalForm());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
