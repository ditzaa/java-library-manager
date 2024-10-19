module com.librarymanagerapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.librarymanagerapp to javafx.fxml;
    exports com.librarymanagerapp;

    opens com.librarymanagerapp.model to javafx.base;
}