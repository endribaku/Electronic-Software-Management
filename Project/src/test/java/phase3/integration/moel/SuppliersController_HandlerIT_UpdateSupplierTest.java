package phase3.integration.moel;

import Controllers.SuppliersController;
import DAO.InventoryFileHandler;
import Interfaces.Views.ISupplierManagementView;
import Models.Inventory;
import Models.Item;
import Models.Supplier;
import Models.User;
import Views.SupplierManagementView;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class SuppliersController_HandlerIT_UpdateSupplierTest {

    @BeforeAll
    static void initJavaFx() throws Exception {
        try {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(latch::countDown);
            assertTrue(latch.await(3, TimeUnit.SECONDS),
                    "JavaFX toolkit did not initialize");
        } catch (IllegalStateException alreadyInitialized) {
            // ok
        }
    }

    private static void runOnFxThreadAndWait(Runnable action) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } finally {
                latch.countDown();
            }
        });
        assertTrue(latch.await(5, TimeUnit.SECONDS), "FX action did not finish in time");
    }

    @Test
    void updateSupplier_valid_updatesSupplierName(@TempDir Path tempDir) throws Exception {

        // Real path used by InventoryFileHandler (hardcoded)
        Path inventoryPath = Path.of(InventoryFileHandler.FILE_PATH);
        Path inventoryDir = inventoryPath.getParent();
        assertNotNull(inventoryDir, "Inventory directory path should not be null");

        // Backup location in tempDir
        Path backupPath = tempDir.resolve("inventory_backup.dat");
        boolean hadOriginal = Files.exists(inventoryPath);

        // Create a supplier we will later update
        Supplier supplier = new Supplier("Old Supplier Name", List.<Item>of());
        String supplierId = supplier.getSupplierID();

        try {
            // Ensure folder exists
            Files.createDirectories(inventoryDir);

            // Backup existing inventory.dat if it exists
            if (hadOriginal) {
                Files.move(inventoryPath, backupPath, StandardCopyOption.REPLACE_EXISTING);
            }

            // Write a clean inventory containing our supplier
            Inventory initialInventory = new Inventory();
            initialInventory.addSupplier(supplier);

            InventoryFileHandler inventoryHandler = new InventoryFileHandler();
            inventoryHandler.setInventory(initialInventory);

            // Create controller
            User dummyUser = null;
            SuppliersController controller = new SuppliersController(dummyUser);

            // Access view (interface) and set the edit name input (needs concrete view)
            ISupplierManagementView view = controller.getView();
            ((SupplierManagementView) view).getSuppliersEditNameField().setText("New Supplier Name");

            // Invoke private onSupplierUpdate(Supplier supplier) on FX thread
            Method updateMethod = SuppliersController.class.getDeclaredMethod("onSupplierUpdate", Supplier.class);
            updateMethod.setAccessible(true);

            runOnFxThreadAndWait(() -> {
                try {
                    updateMethod.invoke(controller, supplier);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });

            // Verify persistence by reading back inventory
            Inventory loaded = new InventoryFileHandler().getInventory().get();
            assertNotNull(loaded, "Inventory should not be null");

            Supplier updated = loaded.getSuppliers().stream()
                    .filter(s -> supplierId.equals(s.getSupplierID()))
                    .findFirst()
                    .orElse(null);

            assertNotNull(updated, "Updated supplier should exist in the inventory");
            assertEquals("New Supplier Name", updated.getName(),
                    "Supplier name should be updated and persisted");

        } finally {
            // Clean up test-created file
            try {
                Files.deleteIfExists(inventoryPath);
            } catch (Exception ignored) {}

            // Restore original inventory.dat if it existed
            if (hadOriginal) {
                Files.move(backupPath, inventoryPath, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
}
