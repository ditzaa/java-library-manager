module gui.librarymanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens gui.librarymanager to javafx.fxml;
    exports gui.librarymanager;
}