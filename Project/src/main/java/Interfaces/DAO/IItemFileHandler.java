package Interfaces.DAO;

import Models.Item;
import javafx.collections.ObservableList;

import java.util.List;

public interface IItemFileHandler {

    ObservableList<Item> getAllItems();

    void insertItem(Item item);

    void deleteItem(Item item);

    void deleteAll(List<Item> itemsToRemove);

    boolean updateAll();

    Item selectItem(String itemName);

    void selectAllItems();
}
