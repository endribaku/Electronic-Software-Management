package Exceptions;

import javafx.scene.control.Alert;

public class ItemStockException extends RuntimeException {
    public ItemStockException(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Item Stock is Low!");
        alert.setHeaderText(message);
        alert.show();
    }
}
