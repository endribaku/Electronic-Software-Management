module com.example.store {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.store to javafx.fxml;
    exports com.example.store;
    exports com.example.store.CashierInterface;
    opens com.example.store.CashierInterface to javafx.fxml;
}