module com.example.store {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports Views.CashierInterface;
    opens Views.CashierInterface to javafx.fxml;
    exports Views;
    opens Views to javafx.fxml;
}