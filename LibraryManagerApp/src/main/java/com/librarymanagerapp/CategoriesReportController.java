package com.librarymanagerapp;

import com.librarymanagerapp.model.Book;
import com.librarymanagerapp.model.Category;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

public class CategoriesReportController {
    @FXML
    private TextField textFieldCategory;

    Set<Category> categories = LibraryManager.getLibrary().getCategories();


    @FXML
    void switchToReportsMenu(ActionEvent event) {
        try {
            LibraryManager.switchScene("categories-report-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onGenerateCategoryReport(ActionEvent event) {
        String genre = textFieldCategory.getText();
        String fileName = genre + " Category Report";
        boolean categoryExists = false;

        FileChooser.ExtensionFilter extFilter1 = new FileChooser.ExtensionFilter("Text files",
                "*.txt");
        FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("All files",
                "*.*");

        for (Category category : categories) {
            if (genre.equals(category.getName())) {
                categoryExists = true;

                Stage stageFileSave = new Stage();
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Alege locul de salvare al fișierului");
                fileChooser.setInitialFileName(fileName);
                fileChooser.getExtensionFilters().addAll(extFilter1, extFilter2);
                fileChooser.setInitialDirectory(new File("./Reports"));
                stageFileSave.setResizable(true);
                File fileReport = fileChooser.showSaveDialog(stageFileSave);

                int numberOfBooks = category.getNumberOfBooks();
                List<Book> booksListOfSelectedGenre = category.getBooks();
                if (fileReport != null) {
                        try (FileWriter fileWriter = new FileWriter(fileReport)) {
                            fileWriter.write("Raport Categorie " + genre + "\n");
                            String delimitatorLine = "\n" + String.format("%-110s", "")
                                    .replace(' ', '-') + "\n";
                            fileWriter.write(delimitatorLine);

                            fileWriter.write(String.format("Numărul de cărți: %d\n", numberOfBooks));

                            String headerFormat = "%-5s %-50s %-40s %s\n";
                            fileWriter.write(delimitatorLine);
                            fileWriter.write(String.format(headerFormat,
                                    "Nr.",
                                    "Titlu",
                                    "Autori",
                                    "Data Publicării"));
                            fileWriter.write(delimitatorLine);

                            int indexBooks = 1;
                            String rowFormat = "%-5d %-50s %-40s %s\n";
                            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

                            for (Book book : booksListOfSelectedGenre) {
                                List<String> authors = book.getAuthors();
                                String authorsString = String.join(", ", authors);

                                String formattedDate = book.getPublicationDate().format(dateFormatter);

                                fileWriter.write(String.format(rowFormat,
                                        indexBooks,
                                        truncateString(book.getTitle(), 50),
                                        truncateString(authorsString, 40),
                                        formattedDate));

                                indexBooks++;
                            }

                            fileWriter.write(delimitatorLine);

                            Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Creare raport");
                                alert.setHeaderText("Raport creat cu succes!");
                                alert.showAndWait();
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            }
        }

        if (!categoryExists) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Creare raport");
            alert.setHeaderText("Nu există niciun raport cu acest titlu!");
            alert.showAndWait();
        }
    }

    @FXML
    void onGenerateAllCategoriesReport(ActionEvent event) {
        String fileName = "All Categories Report";
        FileChooser.ExtensionFilter extFilter1 = new FileChooser.ExtensionFilter("Text files", "*.txt");
        FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("All files", "*.*");

        Stage stageFileSave = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Alege locul de salvare al fișierului");
        fileChooser.setInitialFileName(fileName);
        fileChooser.getExtensionFilters().addAll(extFilter1, extFilter2);
        fileChooser.setInitialDirectory(new File("./Reports"));
        File fileReport = fileChooser.showSaveDialog(stageFileSave);

        if (fileReport != null) {
            try (FileWriter fileWriter = new FileWriter(fileReport)) {
                fileWriter.write(centerText("RAPORT GENERAL - TOATE CATEGORIILE", 100) + "\n\n");

                String separator = createSeparator(100);

                for (Category category : categories) {
                    fileWriter.write(separator);
                    fileWriter.write(String.format("\n%-20s%s", "CATEGORIA:",
                            category.getName().toUpperCase()) + "\n");
                    fileWriter.write(String.format("%-20s%d\n", "Cărți în catalog:", category.getNumberOfBooks()));
                    fileWriter.write("\n");

                    String headerFormat = "%-5s %-45s %-35s %s\n";
                    fileWriter.write(String.format(headerFormat,
                            "Nr.",
                            "Titlu",
                            "Autori",
                            "Data Publicării"));
                    fileWriter.write(createSeparator(100));

                    int indexBooks = 1;
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    String rowFormat = "%-5d %-45s %-35s %s\n";

                    for (Book book : category.getBooks()) {
                        String authorsString = String.join(", ", book.getAuthors());
                        String formattedDate = book.getPublicationDate().format(dateFormatter);

                        fileWriter.write(String.format(rowFormat,
                                indexBooks,
                                truncateString(book.getTitle(), 45),
                                truncateString(authorsString, 35),
                                formattedDate));
                        indexBooks++;
                    }

                    fileWriter.write(separator + "\n\n");
                }

                fileWriter.write(centerText("SFÂRȘIT RAPORT", 100) + "\n");
                fileWriter.write(centerText("Generat la: " +
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), 100));

                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Creare raport");
                    alert.setHeaderText("Raport creat cu succes!");
                    alert.showAndWait();
                });
            } catch (IOException e) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eroare");
                    alert.setHeaderText("Eroare la crearea raportului!");
                    alert.setContentText("A apărut o eroare la scrierea în fișier: " + e.getMessage());
                    alert.showAndWait();
                });
            }
        }
    }

    @FXML
    void onGenerateCategoriesList(ActionEvent event) {
        Stage categoriesStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(LibraryManager.class.getResource("all-categories-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        categoriesStage.setScene(scene);
        categoriesStage.show();
    }

    private String createSeparator(int length) {
        return "\n" + String.format("%" + length + "s", "").replace(' ', '-') + "\n";
    }

    private String centerText(String text, int width) {
        return String.format("%" + ((width + text.length()) / 2) + "s", text);
    }

    private String truncateString(String input, int maxLength) {
        if (input == null) return "";
        return input.length() > maxLength ?
                input.substring(0, maxLength - 3) + "..." :
                input;
    }
}

