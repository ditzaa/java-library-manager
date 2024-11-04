package com.librarymanagerapp.util;

import java.util.Comparator;

public class CategoriesComparator implements Comparator<String> {
    @Override
    public int compare(String category1, String category2) {
        return category1.compareTo(category2);
    }
}
