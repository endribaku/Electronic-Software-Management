package stubs;

import Interfaces.DAO.IItemFileHandler;
import Models.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ItemFileHandlerStub implements IItemFileHandler {

    private final ObservableList<Item> items = FXCollections.observableArrayList();
    public boolean getAllItemsCalled = false;
    public boolean insertItemCalled = false;
    public boolean deleteItemCalled = false;
    public boolean updateAllCalled = false;

    @Override
    public ObservableList<Item> getAllItems() {
        getAllItemsCalled = true;
        return items;
    }

    @Override
    public void insertItem(Item item) {
        insertItemCalled = true;
        items.add(item);
    }

    @Override
    public void deleteItem(Item item) {
        deleteItemCalled = true;
        items.remove(item);
    }

    @Override
    public void deleteAll(List<Item> itemsToRemove) {
        items.removeAll(itemsToRemove);
    }

    @Override
    public boolean updateAll() {
        updateAllCalled = true;
        return true;
    }

    @Override
    public Item selectItem(String itemName) {
        return items.stream()
                .filter(i -> i.getName().equals(itemName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void selectAllItems() {
        // No-op for stub
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void reset() {
        getAllItemsCalled = false;
        insertItemCalled = false;
        deleteItemCalled = false;
        updateAllCalled = false;
        items.clear();
    }
}
