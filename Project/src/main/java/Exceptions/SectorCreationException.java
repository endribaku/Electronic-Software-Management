package Exceptions;

import javafx.scene.control.Alert;

public class SectorCreationException extends Exception{
    public SectorCreationException(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.show();
    }
}
