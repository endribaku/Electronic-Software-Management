package phase2.daron;

import DAO.InventoryFileHandler;
import Models.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CheckForLowStockECTest {

    private static Item itemWithQuantity(int qty) {
        return new Item(
                "TestItem",
                "TestCategory",
                "TestSupplier",
                LocalDate.now(),
                5.0,
                10.0,
                qty
        );
    }

    @Test
    void ec_checkForLowStock_representatives_lowVsSufficient_noBoundariesUsed() {
        Item low = itemWithQuantity(2);
        Item sufficient1 = itemWithQuantity(7);
        Item sufficient2 = itemWithQuantity(10);

        ObservableList<Item> items =
                FXCollections.observableArrayList(low, sufficient1, sufficient2);

        InventoryFileHandler inventoryFileHandler =
                new InventoryFileHandler(new File("build/tmp/phase2-daron-ec.dat"));

        ObservableList<Item> lowStock =
                inventoryFileHandler.checkForLowStock(items);

        assertEquals(1, lowStock.size(), "Only low-stock representative should be returned");
        assertSame(low, lowStock.get(0), "Returned list should contain only the low-stock item");
    }
}