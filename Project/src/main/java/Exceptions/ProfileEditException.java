package Exceptions;

import javafx.scene.control.Alert;

public class ProfileEditException extends RuntimeException{
    public ProfileEditException(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.show();
    }
}
