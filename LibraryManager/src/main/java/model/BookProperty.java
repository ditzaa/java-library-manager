package model;

import javafx.beans.property.*;

public class BookProperty {
    private IntegerProperty idBook;
    private StringProperty title;

    public BookProperty() {
    }

    public void setIdBook(int value) {
        idBookProperty().set(value);
    }

    public int getIdBook() {
        return idBookProperty().get();
    }

    public void setTitle(String value) {
        titleProperty().set(value);
    }

    public String getTitle() {
        return titleProperty().get();
    }

    public IntegerProperty idBookProperty() {
        if (idBook == null) idBook = new SimpleIntegerProperty(this, "idBook");
        return idBook;
    }

    public StringProperty titleProperty() {
        if (title == null) title = new SimpleStringProperty(this, "title");
        return title;
    }
}
