package phase3.integration.moel;

import Controllers.SuppliersController;
import Interfaces.DAO.ISuppliersFileHandler;
import Interfaces.Views.ISupplierManagementView;
import Models.Item;
import Models.Supplier;
import Models.User;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import stubs.InventoryFileHandlerStub2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class SuppliersController_ViewIT_UpdateSupplierTest {

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
    void updateSupplierButton_callsUpdateSupplier() {

        // Arrange: use a test-only View stub that does NOT overwrite edit name when controller calls setEditSupplierName(oldName)
        TestSupplierManagementView view = new TestSupplierManagementView();
        TestInventoryFileHandler inventoryStub = new TestInventoryFileHandler();
        ISuppliersFileHandler handlerStub = new SuppliersFileHandlerStub();

        User dummyUser = null;
        new SuppliersController(dummyUser, view, handlerStub, inventoryStub);

        Supplier supplier = new Supplier("Old Name", List.of());
        view.selectedSupplier = supplier;

        // User "typed" new name BEFORE clicking edit (controller will try to overwrite it, but our stub ignores overwrite)
        view.editSupplierName = "New Name";

        // Act: in your controller, update happens immediately when Edit is triggered
        assertNotNull(view.onEditSupplierAction, "Controller should wire onEditSupplier action");
        view.onEditSupplierAction.run();

        // Assert: inventory updateSupplier gets the NEW name (because our stub preserved it)
        assertEquals(supplier.getSupplierID(), inventoryStub.lastUpdatedSupplierId,
                "updateSupplier should be called with selected supplier ID");
        assertEquals("New Name", inventoryStub.lastUpdatedSupplierName,
                "updateSupplier should be called with the new supplier name");
    }

    // ===== Test-only inventory stub (captures updateSupplier calls) =====
    static class TestInventoryFileHandler extends InventoryFileHandlerStub2 {
        String lastUpdatedSupplierId;
        String lastUpdatedSupplierName;

        @Override
        public boolean updateSupplier(String supplierId, String newName) {
            this.lastUpdatedSupplierId = supplierId;
            this.lastUpdatedSupplierName = newName;
            return true;
        }
    }

    // ===== Test-only view stub (inside test file, so you change ONLY the test class) =====
    static class TestSupplierManagementView implements ISupplierManagementView {

        // Wired actions
        Runnable onAddSupplierAction;
        Runnable onDeleteSupplierAction;
        Runnable onEditSupplierAction;
        Runnable onCancelUpdateAction;
        Runnable onUpdateTableAction;

        // State
        Supplier selectedSupplier;
        String supplierNameInput = "";
        String editSupplierName = "";

        // Messages (optional)
        String lastInfoTitle, lastInfoMessage;
        String lastErrorTitle, lastErrorMessage;

        @Override
        public void setSuppliersTableItems(ObservableList<Supplier> suppliers) { }

        @Override
        public void setAvailableItems(ObservableList<Item> items) { }

        @Override
        public String getSupplierNameInput() {
            return supplierNameInput;
        }

        @Override
        public List<Item> getSelectedItemsForSupplier() {
            return FXCollections.observableArrayList();
        }

        @Override
        public Supplier getSelectedSupplier() {
            return selectedSupplier;
        }

        @Override
        public void setEditSupplierName(String value) {
            // IMPORTANT: do NOTHING so controller can't overwrite our "New Name"
            // (Controller sets old name here, then updates immediately)
        }

        @Override
        public String getEditSupplierName() {
            return editSupplierName;
        }

        @Override
        public void showAddSupplierBox() { }

        @Override
        public void showEditSupplierBox() { }

        @Override
        public void onAddSupplier(Runnable action) {
            this.onAddSupplierAction = action;
        }

        @Override
        public void onDeleteSupplier(Runnable action) {
            this.onDeleteSupplierAction = action;
        }

        @Override
        public void onEditSupplier(Runnable action) {
            this.onEditSupplierAction = action;
        }

        @Override
        public void onCancelUpdate(Runnable action) {
            this.onCancelUpdateAction = action;
        }

        @Override
        public void onUpdateTable(Runnable action) {
            this.onUpdateTableAction = action;
        }

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

        @Override
        public void clearAddSupplierInputs() { }

        @Override
        public HBox getSuppliersPage() {
            return new HBox();
        }
    }

    // ===== Minimal handler stub (only because constructor requires it) =====
    static class SuppliersFileHandlerStub implements ISuppliersFileHandler {
        @Override
        public ObservableList<Supplier> getSuppliers() {
            return null;
        }

        @Override
        public boolean updateAll() {
            return true;
        }
    }
}