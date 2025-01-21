package Exceptions;

import javafx.scene.control.Alert;

public class InvalidCredentialsException extends RuntimeException{
    String message = "Invalid username or password.";

    public InvalidCredentialsException() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.show();
    }

    public InvalidCredentialsException(String message) {
        this.message = message;
        System.out.println(this.getMessage());
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
