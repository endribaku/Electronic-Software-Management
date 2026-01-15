package phase2.hazis;

import Controllers.BillManagementController;
import Exceptions.BillCreationException;
import Exceptions.ItemStockException;
import Models.Bill_Item;
import Models.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ValidateAndCreateBillItemCoverageTest {

    @Test
    @DisplayName("COV-01: Both conditions true (item=null, qty<=0)")
    void testBothConditionsTrue_ThrowsException() {
        assertThrows(BillCreationException.class,
                () -> BillManagementController.validateAndCreateBillItem(null, -5));
    }

    @Test
    @DisplayName("COV-02: MC/DC Pair 1 - item=null, qty>0")
    void testNullItemValidQuantity_ThrowsException() {
        assertThrows(BillCreationException.class,
                () -> BillManagementController.validateAndCreateBillItem(null, 5));
    }

    @Test
    @DisplayName("COV-03: MC/DC Pair 2 - item=valid, qty<=0")
    void testValidItemInvalidQuantity_ThrowsException() {
        Item validItem = createItem("ITEM-001", "Test Item", 10);
        assertThrows(BillCreationException.class,
                () -> BillManagementController.validateAndCreateBillItem(validItem, -5));
    }

    @Test
    @DisplayName("COV-04: Success path")
    void testValidItemAndQuantity_Success() {
        Item validItem = createItem("ITEM-001", "Laptop", 10);
        Bill_Item result = BillManagementController.validateAndCreateBillItem(validItem, 5);

        assertNotNull(result);
        assertEquals(validItem, result.getItem());
        assertEquals(5, result.getQuantity());
    }

    @Test
    @DisplayName("COV-05: Quantity equals stock")
    void testQuantityEqualsStock_ThrowsException() {
        Item validItem = createItem("ITEM-001", "Test Item", 10);
        assertThrows(ItemStockException.class,
                () -> BillManagementController.validateAndCreateBillItem(validItem, 10));
    }

    @Test
    @DisplayName("COV-06: Quantity exceeds stock")
    void testQuantityExceedsStock_ThrowsException() {
        Item validItem = createItem("ITEM-001", "Test Item", 10);
        assertThrows(ItemStockException.class,
                () -> BillManagementController.validateAndCreateBillItem(validItem, 15));
    }

    @Test
    @DisplayName("COV: Zero quantity")
    void testZeroQuantity_ThrowsException() {
        Item validItem = createItem("ITEM-001", "Test Item", 10);
        assertThrows(BillCreationException.class,
                () -> BillManagementController.validateAndCreateBillItem(validItem, 0));
    }

    @Test
    @DisplayName("COV: Minimum valid quantity")
    void testMinimumValidQuantity_Success() {
        Item validItem = createItem("ITEM-001", "Test Item", 2);
        Bill_Item result = BillManagementController.validateAndCreateBillItem(validItem, 1);

        assertNotNull(result);
        assertEquals(1, result.getQuantity());
    }

    private Item createItem(String id, String name, int stock) {
        Item item = new Item("Item1", "Category1", "Supplier1", LocalDate.now(), 20.00, 50.00, 20);
        item.setItemID(id);
        item.setName(name);
        item.setQuantity(stock);
        item.setSellingPrice(100.0);
        return item;
    }
}