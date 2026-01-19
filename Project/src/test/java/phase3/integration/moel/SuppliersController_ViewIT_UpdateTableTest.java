package phase3.integration.moel;

import Controllers.SuppliersController;
import Interfaces.DAO.IInventoryFileHandler;
import Interfaces.DAO.ISuppliersFileHandler;
import Interfaces.Views.ISupplierManagementView;
import Models.Item;
import Models.Supplier;
import Models.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class SuppliersController_ViewIT_UpdateTableTest {

    @BeforeAll
    static void initJavaFxToolkit() throws Exception {
        try {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(latch::countDown);
            assertTrue(latch.await(3, TimeUnit.SECONDS),
                    "JavaFX toolkit did not initialize in time");
        } catch (IllegalStateException alreadyInitialized) {
            // already initialized -> ok
        }
    }

    @Test
    void clickingUpdateTable_callsHandlerUpdateAll_andShowsSuccess() {

        // Arrange
        SupplierManagementViewStub view = new SupplierManagementViewStub();
        SuppliersFileHandlerStub handler = new SuppliersFileHandlerStub(true); // success
        InventoryStub inventory = new InventoryStub();

        User dummyUser = null;
        new SuppliersController(dummyUser, view, handler, inventory);

        assertNotNull(view.onUpdateTableAction, "Controller should wire onUpdateTable action");

        // Act
        view.onUpdateTableAction.run();

        // Assert handler called
        assertTrue(handler.updateAllCalled, "updateAll() should be called");

        // Assert view feedback
        assertEquals("Success", view.lastInfoTitle);
        assertEquals("Supplier Table Updated Successfully", view.lastInfoMessage);
        assertNull(view.lastErrorTitle, "No error expected");
    }

    @Test
    void clickingUpdateTable_whenHandlerFails_showsError() {

        // Arrange
        SupplierManagementViewStub view = new SupplierManagementViewStub();
        SuppliersFileHandlerStub handler = new SuppliersFileHandlerStub(false); // fail
        InventoryStub inventory = new InventoryStub();

        User dummyUser = null;
        new SuppliersController(dummyUser, view, handler, inventory);

        assertNotNull(view.onUpdateTableAction, "Controller should wire onUpdateTable action");

        // Act
        view.onUpdateTableAction.run();

        // Assert handler called
        assertTrue(handler.updateAllCalled, "updateAll() should be called");

        // Assert error feedback
        assertEquals("Error", view.lastErrorTitle);
        assertEquals("Supplier Table Update Error", view.lastErrorMessage);
        assertNull(view.lastInfoTitle, "No success expected");
    }

    // ========= View Stub =========
    static class SupplierManagementViewStub implements ISupplierManagementView {

        Runnable onAddSupplierAction;
        Runnable onDeleteSupplierAction;
        Runnable onEditSupplierAction;
        Runnable onCancelUpdateAction;
        Runnable onUpdateTableAction;

        String lastInfoTitle, lastInfoMessage;
        String lastErrorTitle, lastErrorMessage;

        @Override public void setSuppliersTableItems(ObservableList<Supplier> suppliers) {}
        @Override public void setAvailableItems(ObservableList<Item> items) {}

        @Override public String getSupplierNameInput() { return ""; }
        @Override public List<Item> getSelectedItemsForSupplier() { return List.of(); }
        @Override public Supplier getSelectedSupplier() { return null; }

        @Override public void setEditSupplierName(String value) {}
        @Override public String getEditSupplierName() { return ""; }

        @Override public void showAddSupplierBox() {}
        @Override public void showEditSupplierBox() {}

        @Override public void onAddSupplier(Runnable action) { onAddSupplierAction = action; }
        @Override public void onDeleteSupplier(Runnable action) { onDeleteSupplierAction = action; }
        @Override public void onEditSupplier(Runnable action) { onEditSupplierAction = action; }
        @Override public void onCancelUpdate(Runnable action) { onCancelUpdateAction = action; }
        @Override public void onUpdateTable(Runnable action) { onUpdateTableAction = action; }

        @Override
        public void showInfo(String title, String message) {
            lastInfoTitle = title;
            lastInfoMessage = message;
        }

        @Override
        public void showError(String title, String message) {
            lastErrorTitle = title;
            lastErrorMessage = message;
        }

        @Override public void clearAddSupplierInputs() {}
        @Override public HBox getSuppliersPage() { return new HBox(); }
    }

    // ========= Handler Stub =========
    static class SuppliersFileHandlerStub implements ISuppliersFileHandler {

        private final boolean shouldSucceed;
        boolean updateAllCalled = false;

        SuppliersFileHandlerStub(boolean shouldSucceed) {
            this.shouldSucceed = shouldSucceed;
        }

        @Override
        public ObservableList<Supplier> getSuppliers() {
            return null;
        }

        @Override
        public boolean updateAll() {
            updateAllCalled = true;
            return shouldSucceed;
        }

        // If your ISuppliersFileHandler has more methods, IntelliJ will mark errors:
        // Use Alt+Enter -> Implement methods, and return safe defaults (true/null/empty list).
    }

    // ========= Inventory Stub =========
    static class InventoryStub implements IInventoryFileHandler {
        @Override public ObservableList<Supplier> getSuppliersList() { return FXCollections.observableArrayList(); }
        @Override public ObservableList<Item> getSuppliedItems() { return FXCollections.observableArrayList(); }

        // Implement the rest with safe defaults (only needed because interface requires it).
        @Override public javafx.beans.property.ObjectProperty<Models.Inventory> getInventory() { return null; }
        @Override public boolean updateInventory(Models.Inventory inventory) { return true; }
        @Override public void updateInventoryFile() {}
        @Override public ObservableList<Item> getItemsList() { return FXCollections.observableArrayList(); }
        @Override public ObservableList<Item> getItemsOfUser(User user) { return FXCollections.observableArrayList(); }
        @Override public ObservableList<Models.Category> getCategoriesOfUser(User user) { return FXCollections.observableArrayList(); }
        @Override public ObservableList<Models.Sector> getSectorsOfUser(User user) { return FXCollections.observableArrayList(); }
        @Override public void addCategory(String sectorName, Models.Category newCategory) {}
        @Override public void addSector(Models.Sector newSector) {}
        @Override public void addItem(Models.Category targetCategory, Supplier targetSupplier, Item newItem) {}
        @Override public void addSupplier(Supplier supplier) {}
        @Override public boolean updateSupplier(String supplierID, String supplierName) { return true; }
        @Override public void deleteSupplier(Supplier supplier) {}
        @Override public boolean updateItem(String itemID, String itemName, String itemCategory, String itemSupplier,
                                            java.time.LocalDate date, double pPrice, double sPrice, int quantity) { return true; }
        @Override public boolean deleteItem(Item item) { return true; }
        @Override public boolean updateCategory(Models.Category category, String categoryName, String sectorName) { return true; }
        @Override public boolean updateSector(Models.Sector sector, String sectorName) { return true; }
        @Override public ObservableList<Item> checkForLowStock(ObservableList<Item> items) { return FXCollections.observableArrayList(); }
        @Override public ObservableList<Item> getItems() { return FXCollections.observableArrayList(); }
        @Override public void insertItem(Item item) {}
        @Override public void deleteItem(String itemID) {}
    }
}
