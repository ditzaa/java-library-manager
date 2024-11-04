package com.librarymanagerapp;

import com.librarymanagerapp.model.Category;
import com.librarymanagerapp.util.CategoriesComparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

public class AllCategoriesController {
    @FXML
    private ListView<String> listViewAllCategories;

    ObservableList<String> allCategories = FXCollections.observableArrayList();
    Set<Category> categories = LibraryManager.getLibrary().getCategories();

    public void initialize() {
        listViewAllCategories.setItems(allCategories);

        for (Category category : categories) {
            allCategories.add(category.getName());
        }

        CategoriesComparator categoriesComparator = new CategoriesComparator();
        Collections.sort(allCategories, categoriesComparator);

        listViewAllCategories.setItems(allCategories);
    }
}
