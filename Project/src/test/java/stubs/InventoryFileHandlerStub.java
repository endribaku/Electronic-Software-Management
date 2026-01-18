package stubs;

import Interfaces.DAO.IInventoryFileHandler;
import Models.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class InventoryFileHandlerStub implements IInventoryFileHandler {

    // ===== Flags used by Controller ↔ View IT =====
    public boolean addItemCalled = false;
    public boolean updateItemCalled = false;
    public boolean deleteItemCalled = false;


    // ===== Minimal in-memory data =====
    private final ObjectProperty<Inventory> inventory =
            new SimpleObjectProperty<>(new Inventory());

    // ===== Core methods the controller triggers =====

    @Override
    public void addItem(Category targetCategory, Supplier targetSupplier, Item newItem) {
        addItemCalled = true;
    }

    @Override
    public boolean updateItem(
            String itemID,
            String itemName,
            String itemCategory,
            String itemSupplier,
            LocalDate date,
            double pPrice,
            double sPrice,
            int quantity
    ) {
        updateItemCalled = true;
        return true;
    }

    @Override
    public boolean deleteItem(Item item) {
        deleteItemCalled = true;
        return true;
    }

    // ===== Required by interface but not relevant for these tests =====

    @Override
    public ObjectProperty<Inventory> getInventory() {
        return inventory;
    }

    @Override
    public boolean updateInventory(Inventory inventory) {
        return true;
    }

    @Override
    public void updateInventoryFile() {
        // no-op
    }

    @Override
    public ObservableList<Item> getItemsList() {
        return FXCollections.observableArrayList();
    }

    @Override
    public ObservableList<Item> getItemsOfUser(User user) {
        return FXCollections.observableArrayList();
    }

    @Override
    public ObservableList<Category> getCategoriesOfUser(User user) {
        return FXCollections.observableArrayList();
    }

    @Override
    public ObservableList<Supplier> getSuppliersList() {
        return FXCollections.observableArrayList();
    }

    @Override
    public ObservableList<Item> getSuppliedItems() {
        return FXCollections.observableArrayList();
    }

    @Override
    public ObservableList<Sector> getSectorsOfUser(User user) {
        return FXCollections.observableArrayList();
    }

    @Override
    public void addCategory(String sectorName, Category newCategory) {
        // no-op
    }

    @Override
    public void addSector(Sector newSector) {
        // no-op
    }

    @Override
    public void addSupplier(Supplier supplier) {
        // no-op
    }

    @Override
    public boolean updateSupplier(String supplierID, String supplierName) {
        return true;
    }

    @Override
    public void deleteSupplier(Supplier supplier) {
        // no-op
    }

    @Override
    public boolean updateCategory(Category category, String categoryName, String sectorName) {
        return true;
    }

    @Override
    public boolean updateSector(Sector sector, String sectorName) {
        return true;
    }

    @Override
    public ObservableList<Item> checkForLowStock(ObservableList<Item> items) {
        return FXCollections.observableArrayList();
    }

    @Override
    public ObservableList<Item> getItems() {
        return null;
    }

    @Override
    public void insertItem(Item item) {

    }

    @Override
    public void deleteItem(String itemID) {

    }
}
