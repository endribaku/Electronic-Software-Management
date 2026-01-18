package phase3.integration.endri;

import Controllers.InventoryController;
import DAO.InventoryFileHandler;
import Models.*;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import stubs.InventoryViewStub;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InventoryControllerHandlerIT {

    @TempDir
    File tempDir;

    private InventoryController controller;
    private InventoryFileHandler fileHandler;
    private User testUser;

    private Category category;
    private Supplier supplier;

    @BeforeEach
    void setUp() throws IOException {

        // ---------- Create temp inventory file ----------
        File inventoryFile = new File(tempDir, "inventory.dat");
        inventoryFile.createNewFile();

        // ---------- Initialize empty inventory ----------
        Inventory inventory = new Inventory();
        inventory.setSectors(FXCollections.observableArrayList());
        inventory.setSuppliers(FXCollections.observableArrayList());

        fileHandler = new InventoryFileHandler(inventoryFile);
        fileHandler.updateInventory(inventory);

        // ---------- User ----------
        testUser = new User("test-user", Access.Manager);
        testUser.setSector(
                FXCollections.observableArrayList("Electronics")
        );

        // ---------- Sector ----------
        Sector sector = new Sector("Electronics");
        fileHandler.addSector(sector);

        // ---------- Category ----------
        category = new Category("Phones", "Electronics");
        fileHandler.addCategory("Electronics", category);

        // ---------- Supplier ----------
        supplier = new Supplier(
                "Apple",
                FXCollections.observableArrayList()
        );
        fileHandler.addSupplier(supplier);

        // ---------- Controller (NO UI wiring) ----------
        controller = new InventoryController(
                testUser,
                new InventoryViewStub(),
                fileHandler,
                false
        );
    }

    // =========================================================
    // TEST 1: addItem_valid_persistsInInventoryFile
    // =========================================================
    @Test
    void addItem_valid_persistsInInventoryFile() {

        // ---------- Act ----------
        boolean success = controller.onItemAdd(
                "iPhone 14",
                category,
                supplier,
                10,
                800.0,
                1000.0
        );

        // ---------- Assert ----------
        assertTrue(success, "addItem should return true");

        Item addedItem =
                fileHandler.getItemsList()
                        .stream()
                        .filter(i -> i.getName().equals("iPhone 14"))
                        .findFirst()
                        .orElse(null);

        assertNotNull(addedItem);
        assertEquals("Phones", addedItem.getCategory());
        assertEquals("Apple", addedItem.getSupplier());
        assertEquals(10, addedItem.getQuantity());
    }

    // =========================================================
    // TEST 2: updateItem_valid_updatesExistingItem
    // =========================================================
    @Test
    void updateItem_valid_updatesExistingItem() {

        // ---------- Arrange ----------
        Item existingItem = new Item(
                "iPhone 14",
                "Phones",
                "Apple",
                LocalDate.now(),
                800.0,
                1000.0,
                10
        );

        fileHandler.addItem(category, supplier, existingItem);

        // ---------- Act ----------
        boolean updated = fileHandler.updateItem(
                existingItem.getItemID(),
                "iPhone 15",
                "Phones",
                "Apple",
                existingItem.getPurchaseDate(),
                900.0,
                1100.0,
                5
        );

        // ---------- Assert ----------
        assertTrue(updated, "Update should return true");

        Item updatedItem =
                fileHandler.getItemsList()
                        .stream()
                        .filter(i -> i.getItemID().equals(existingItem.getItemID()))
                        .findFirst()
                        .orElse(null);

        assertNotNull(updatedItem);
        assertEquals("iPhone 15", updatedItem.getName());
        assertEquals(900.0, updatedItem.getPurchasePrice());
        assertEquals(1100.0, updatedItem.getSellingPrice());
        assertEquals(5, updatedItem.getQuantity());
    }

    // =========================================================
    // TEST 3: deleteItem_valid_removesAndSaves
    // =========================================================
    @Test
    void deleteItem_valid_removesAndSaves() {

        // ---------- Arrange ----------
        Item itemToDelete = new Item(
                "iPhone 13",
                "Phones",
                "Apple",
                LocalDate.now(),
                700.0,
                900.0,
                8
        );

        fileHandler.addItem(category, supplier, itemToDelete);

        // Sanity check: item exists before deletion
        assertTrue(
                fileHandler.getItemsList()
                        .stream()
                        .anyMatch(i -> i.getItemID().equals(itemToDelete.getItemID())),
                "Item must exist before deletion"
        );

        // ---------- Act ----------
        boolean deleted = fileHandler.deleteItem(itemToDelete);

        // ---------- Assert ----------
        assertTrue(deleted, "deleteItem should return true");

        boolean stillExists =
                fileHandler.getItemsList()
                        .stream()
                        .anyMatch(i -> i.getItemID().equals(itemToDelete.getItemID()));

        assertFalse(stillExists, "Item should be removed from inventory");
    }

}
