package Interfaces.DAO;

import Models.*;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public interface IInventoryFileHandler {

    // Inventory object + save
    ObjectProperty<Inventory> getInventory();
    boolean updateInventory(Inventory inventory);
    void updateInventoryFile();

    // Lists for UI
    ObservableList<Item> getItemsList();
    ObservableList<Item> getItemsOfUser(User user);

    ObservableList<Category> getCategoriesOfUser(User user);

    ObservableList<Supplier> getSuppliersList();
    ObservableList<Item> getSuppliedItems();

    ObservableList<Sector> getSectorsOfUser(User user);

    // CRUD operations used by controller
    void addItem(Category targetCategory, Supplier targetSupplier, Item newItem);
    void addCategory(String sectorName, Category newCategory);
    void addSector(Sector newSector);

    // Supplier CRUD (inventory-level)
    void addSupplier(Supplier supplier);
    boolean updateSupplier(String supplierID, String supplierName);
    void deleteSupplier(Supplier supplier);

    boolean updateItem(
            String itemID,
            String itemName,
            String itemCategory,
            String itemSupplier,
            LocalDate date,
            double pPrice,
            double sPrice,
            int quantity
    );

    boolean updateCategory(Category category, String categoryName, String sectorName);
    boolean updateSector(Sector sector, String sectorName);

    boolean deleteItem(Item item);

    // Low stock
    ObservableList<Item> checkForLowStock(ObservableList<Item> items);
}
