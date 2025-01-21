package Exceptions;

import javafx.scene.control.Alert;

public class BillCreationException extends RuntimeException {
    public BillCreationException(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.show();
    }
}
