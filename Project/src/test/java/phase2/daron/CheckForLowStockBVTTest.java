package phase2.daron;

import DAO.InventoryFileHandler;
import Models.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CheckForLowStockBVTTest {

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

    @ParameterizedTest(name = "BVT: quantity={0} => included={1}")
    @CsvSource({
            "4, true",
            "5, false",
            "3, true",
            "6, false"
    })
    void bvt_checkForLowStock_boundaryAround5(int quantity, boolean shouldBeIncluded) {
        ObservableList<Item> items =
                FXCollections.observableArrayList(itemWithQuantity(quantity));

        InventoryFileHandler inventoryFileHandler =
                new InventoryFileHandler(new File("build/tmp/phase2-bvt-inventory.dat"));

        ObservableList<Item> lowStock = inventoryFileHandler.checkForLowStock(items);

        if (shouldBeIncluded) {
            assertEquals(1, lowStock.size(), "Expected the item to be included as low stock");
            assertSame(items.get(0), lowStock.get(0), "Expected the same item reference to be returned");
        } else {
            assertTrue(lowStock.isEmpty(), "Expected no low stock items for quantity >= 5");
        }
    }
}