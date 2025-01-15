module com.example.store {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports Views;
    opens Views to javafx.fxml;
}