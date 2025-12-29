package Exceptions;

import javafx.scene.control.Alert;

public class InvalidCredentialsException extends RuntimeException{
    public static final String MESSAGE = "Invalid username or password.";

    public InvalidCredentialsException() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(MESSAGE);
        alert.show();
    }

    public InvalidCredentialsException(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.show();
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
