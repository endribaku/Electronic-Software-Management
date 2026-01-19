package phase3.integration.moel;

import Controllers.SuppliersController;
import DAO.SuppliersFileHandler;
import Models.Supplier;
import Models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import stubs.InventoryFileHandlerStub2;
import stubs.SupplierManagementViewStub;

import javafx.application.Platform;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class SuppliersController_ViewIT_DeleteSupplierTest {

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
    void clickingDeleteSupplier_callsInventoryDelete_andShowsSuccess() {

        // Arrange
        SupplierManagementViewStub view = new SupplierManagementViewStub();
        InventoryFileHandlerStub2 inventoryStub = new InventoryFileHandlerStub2();
        SuppliersFileHandler suppliersHandler = new SuppliersFileHandler();

        User dummyUser = null;
        new SuppliersController(dummyUser, view, suppliersHandler, inventoryStub);

        // Put 1 supplier in the inventory list so selection is meaningful
        Supplier s = new Supplier("Delete Me", List.of());
        inventoryStub.getSuppliersList().add(s);

        // Simulate selecting supplier in UI
        view.selectedSupplier = s;

        // Act: simulate clicking "Delete Supplier"
        assertNotNull(view.onDeleteSupplierAction, "Controller should wire onDeleteSupplier action");
        view.onDeleteSupplierAction.run();

        // Assert: inventory delete called with correct supplier
        assertNotNull(inventoryStub.lastDeletedSupplier, "deleteSupplier should be called");
        assertEquals(s.getSupplierID(), inventoryStub.lastDeletedSupplier.getSupplierID());

        // Assert: view feedback
        assertEquals("Success", view.lastInfoTitle);
        assertEquals("Supplier Deleted Successfully", view.lastInfoMessage);
        assertNull(view.lastErrorTitle, "No error expected for valid delete");
    }
}
