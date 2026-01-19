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
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
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

public class SuppliersController_HandlerIT_DeleteSupplierTest {

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
    void deleteSupplier_valid_removesSupplierFromList(@TempDir Path tempDir) throws Exception {

        Path inventoryPath = Path.of(InventoryFileHandler.FILE_PATH);
        Path inventoryDir = inventoryPath.getParent();
        assertNotNull(inventoryDir);

        Path backupPath = tempDir.resolve("inventory_backup.dat");
        boolean hadOriginal = Files.exists(inventoryPath);

        Supplier supplier = new Supplier("Supplier To Delete", List.<Item>of());
        String supplierId = supplier.getSupplierID();

        try {
            Files.createDirectories(inventoryDir);

            if (hadOriginal) {
                Files.move(inventoryPath, backupPath, StandardCopyOption.REPLACE_EXISTING);
            }

            // Write inventory with supplier
            Inventory inv = new Inventory();
            inv.addSupplier(supplier);

            InventoryFileHandler inventoryHandler = new InventoryFileHandler();
            inventoryHandler.setInventory(inv);

            // Create controller
            User dummyUser = null;
            SuppliersController controller = new SuppliersController(dummyUser);

            // Access view + table and bind items
            ISupplierManagementView iView = controller.getView();
            SupplierManagementView view = (SupplierManagementView) iView;

            TableView<Supplier> table = view.getSuppliersTableView();

            ObservableList<Supplier> suppliersList = new InventoryFileHandler().getSuppliersList();
            table.setItems(suppliersList);

            // Ensure supplier exists before delete
            Supplier toSelect = table.getItems().stream()
                    .filter(s -> supplierId.equals(s.getSupplierID()))
                    .findFirst()
                    .orElse(null);

            assertNotNull(toSelect, "Supplier should exist before deletion");

            runOnFxThreadAndWait(() -> table.getSelectionModel().select(toSelect));

            // Invoke private onSupplierDelete()
            Method deleteMethod = SuppliersController.class.getDeclaredMethod("onSupplierDelete");
            deleteMethod.setAccessible(true);

            runOnFxThreadAndWait(() -> {
                try {
                    deleteMethod.invoke(controller);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });

            // Verify supplier removed from the live list (controller↔handler integration)
            assertTrue(
                    suppliersList.stream().noneMatch(s -> supplierId.equals(s.getSupplierID())),
                    "Supplier should be removed from suppliers list after deletion"
            );

        } finally {
            // Clean up broken test-created file
            try {
                Files.deleteIfExists(inventoryPath);
            } catch (Exception ignored) {}

            // Restore original if existed
            if (hadOriginal) {
                Files.move(backupPath, inventoryPath, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
}
