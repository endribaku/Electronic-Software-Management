module com.example.store {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;

    exports Views;
    opens Views to javafx.fxml;

    exports Models;
    opens Models to javafx.base;
    exports Main;
    opens Main to javafx.fxml;
}