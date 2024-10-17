module gui.librarymanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens gui.librarymanager to javafx.fxml;
    exports gui.librarymanager;
}