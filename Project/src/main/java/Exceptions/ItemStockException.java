package Exceptions;


public class ItemStockException extends RuntimeException {
    public ItemStockException(String message) {
        super(message);
    }
}
