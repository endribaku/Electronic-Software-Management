package phase3.integration.moel;

import Controllers.SuppliersController;
import DAO.SuppliersFileHandler;
import Models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import stubs.InventoryFileHandlerStub2;
import stubs.SupplierManagementViewStub;

import javafx.application.Platform;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class SuppliersController_ViewIT_AddSupplierTest {

    @BeforeAll
    static void initJavaFxToolkit() throws Exception {
        try {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(latch::countDown);
            assertTrue(latch.await(3, TimeUnit.SECONDS),
                    "JavaFX toolkit did not initialize in time");
        } catch (IllegalStateException alreadyInitialized) {
            // JavaFX toolkit already initialized -> OK
        }
    }

    @Test
    void clickingAddSupplier_callsInventoryAdd_andShowsSuccess() {

        // Arrange
        SupplierManagementViewStub view = new SupplierManagementViewStub();
        InventoryFileHandlerStub2 inventoryStub = new InventoryFileHandlerStub2();
        SuppliersFileHandler suppliersHandler = new SuppliersFileHandler();

        view.supplierNameInput = "Moel Supplier";

        User dummyUser = null;
        new SuppliersController(dummyUser, view, suppliersHandler, inventoryStub);

        // Act
        assertNotNull(view.onAddSupplierAction, "Controller should wire onAddSupplier action");
        view.onAddSupplierAction.run();

        // Assert
        assertNotNull(inventoryStub.lastAddedSupplier, "Supplier should be added to inventory");
        assertEquals("Moel Supplier", inventoryStub.lastAddedSupplier.getName());

        assertTrue(view.addInputsCleared, "Inputs should be cleared after successful add");
        assertEquals("Success", view.lastInfoTitle);
        assertEquals("Supplier Added Successfully", view.lastInfoMessage);
        assertNull(view.lastErrorTitle, "No error expected for valid input");
    }
}
