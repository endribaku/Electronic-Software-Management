package Exceptions;

import javafx.scene.control.Alert;

public class ItemCreationException extends RuntimeException {

    public ItemCreationException(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.show();
    }

}
