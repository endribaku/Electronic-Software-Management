package Exceptions;

import javafx.scene.control.Alert;

public class CategoryCreationException extends RuntimeException {
    public CategoryCreationException(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.show();
    }
}
