package Exceptions;

import javafx.scene.control.Alert;

public class EmployeeCreationException extends RuntimeException {
    public EmployeeCreationException(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.show();
    }
}
