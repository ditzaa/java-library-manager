package com.librarymanagerapp;

import com.librarymanagerapp.model.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.Set;

public class AllCategoriesController {
    @FXML
    private ListView<String> listViewAllCategories;

    ObservableList<String> allCategories = FXCollections.observableArrayList();
    Set<Category> categories = LibraryManager.getLibrary().getCategories();

    public void initialize() {
        listViewAllCategories.setItems(allCategories);
        System.out.println("Cveva");

        for (Category category : categories) {
            System.out.println(category.getName());
            allCategories.add(category.getName());
        }

        listViewAllCategories.setItems(allCategories);
    }
}
