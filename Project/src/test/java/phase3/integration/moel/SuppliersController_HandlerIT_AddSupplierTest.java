package phase3.integration.moel;

import Controllers.SuppliersController;
import DAO.InventoryFileHandler;
import Interfaces.Views.ISupplierManagementView;
import Models.Inventory;
import Models.User;
import Views.SupplierManagementView;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class SuppliersController_HandlerIT_AddSupplierTest {

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
    void addSupplier_valid_persistsSupplier(@TempDir Path tempDir) throws Exception {

        // Real path used by InventoryFileHandler (hardcoded)
        Path inventoryPath = Path.of(InventoryFileHandler.FILE_PATH);
        Path inventoryDir = inventoryPath.getParent();
        assertNotNull(inventoryDir, "Inventory directory path should not be null");

        // Backup location in tempDir
        Path backupPath = tempDir.resolve("inventory_backup.dat");

        boolean hadOriginal = Files.exists(inventoryPath);

        try {
            // Ensure folder exists in the REAL project working directory
            Files.createDirectories(inventoryDir);

            // Backup existing inventory.dat if it exists (so we don't destroy real data)
            if (hadOriginal) {
                Files.move(inventoryPath, backupPath, StandardCopyOption.REPLACE_EXISTING);
            }

            // Create a clean inventory file for this test
            InventoryFileHandler inventoryHandler = new InventoryFileHandler();
            inventoryHandler.setInventory(new Inventory());

            // Create controller
            User dummyUser = null;
            SuppliersController controller = new SuppliersController(dummyUser);

            // Access view via interface, then cast to concrete to set TextField
            ISupplierManagementView view = controller.getView();
            ((SupplierManagementView) view).getSuppliersNameField().setText("Test Supplier A");

            // Invoke private onSupplierAdd on FX thread (because it shows Alerts)
            Method addMethod = SuppliersController.class.getDeclaredMethod("onSupplierAdd");
            addMethod.setAccessible(true);

            runOnFxThreadAndWait(() -> {
                try {
                    addMethod.invoke(controller);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });

            // Verify persistence
            Inventory loaded = new InventoryFileHandler().getInventory().get();
            assertNotNull(loaded, "Inventory should not be null");

            assertTrue(
                    loaded.getSuppliers().stream().anyMatch(s -> "Test Supplier A".equals(s.getName())),
                    "Supplier should be persisted in inventory.dat"
            );

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
