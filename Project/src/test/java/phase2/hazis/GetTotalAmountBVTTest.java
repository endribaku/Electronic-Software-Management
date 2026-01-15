package phase2.hazis;

import Models.Bill;
import Models.Bill_Item;
import Models.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class GetTotalAmountBVTTest {

    private Bill bill;

    @BeforeEach
    void setUp() {
        bill = new Bill();
    }

    @Test
    @DisplayName("BVT-01: Empty list returns 0.0 (minimum boundary)")
    void testEmptyList_ReturnsZero() throws Exception {
        ListProperty<Bill_Item> emptyList = new SimpleListProperty<>(
                FXCollections.observableArrayList()
        );

        double result = bill.getTotalAmountfromItemsSold(emptyList);
        assertEquals(0.0, result, 0.001,
                "Empty list should return 0.0");
    }

    @Test
    @DisplayName("BVT-02: Single item with price $0.00 (minimum valid price)")
    void testSingleItemZeroPrice_ReturnsZero() throws Exception {
        Item freeItem = createItem("FREE-001", "Free Sample", 0.0);
        Bill_Item billItem = new Bill_Item(freeItem, 1);
        ListProperty<Bill_Item> items = createListProperty(billItem);

        double result = bill.getTotalAmountfromItemsSold(items);

        assertEquals(0.0, result, 0.001,
                "Free item should result in $0.00 total");
    }

    @Test
    @DisplayName("BVT-03: Negative item price throws exception (below boundary)")
    void testNegativePrice_ThrowsException() {
        Item invalidItem = createItem("INVALID-001", "Bad Item", -0.01);
        Bill_Item billItem = new Bill_Item(invalidItem, 1);
        ListProperty<Bill_Item> items = createListProperty(billItem);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> bill.getTotalAmountfromItemsSold(items),
                "Negative price should throw IllegalArgumentException"
        );

        assertTrue(exception.getMessage().contains("cannot be negative"));
    }

    @Test
    @DisplayName("BVT-04: Item price $0.01 (just above zero boundary)")
    void testPriceJustAboveZero_Success() throws Exception {
        Item cheapItem = createItem("CHEAP-001", "Penny Item", 0.01);
        Bill_Item billItem = new Bill_Item(cheapItem, 1);
        ListProperty<Bill_Item> items = createListProperty(billItem);

        double result = bill.getTotalAmountfromItemsSold(items);

        assertEquals(0.01, result, 0.001,
                "Item priced at $0.01 should return $0.01");
    }

    @Test
    @DisplayName("BVT-05: Single item mid-range price")
    void testSingleItemMidRange_Success() throws Exception {
        Item normalItem = createItem("NORMAL-001", "Regular Item", 100.0);
        Bill_Item billItem = new Bill_Item(normalItem, 1);
        ListProperty<Bill_Item> items = createListProperty(billItem);

        double result = bill.getTotalAmountfromItemsSold(items);

        assertEquals(100.0, result, 0.001,
                "Item priced at $100.00 should return $100.00");
    }

    @Test
    @DisplayName("BVT-06: Multiple items mid-range total")
    void testMultipleItemsMidRange_Success() throws Exception {
        // Arrange
        Item item1 = createItem("ITEM-001", "Item 1", 500.0);
        Item item2 = createItem("ITEM-002", "Item 2", 500.0);
        ListProperty<Bill_Item> items = createListProperty(
                new Bill_Item(item1, 1),
                new Bill_Item(item2, 1)
        );

        double result = bill.getTotalAmountfromItemsSold(items);

        assertEquals(1000.0, result, 0.001,
                "Two $500 items should total $1000.00");
    }

    @Test
    @DisplayName("BVT-07: Total $999,999.99 (just below maximum boundary)")
    void testJustBelowMaximum_Success() throws Exception {
        Item expensiveItem = createItem("EXPENSIVE-001", "Almost Max", 999999.99);
        Bill_Item billItem = new Bill_Item(expensiveItem, 1);
        ListProperty<Bill_Item> items = createListProperty(billItem);

        double result = bill.getTotalAmountfromItemsSold(items);

        assertEquals(999999.99, result, 0.01,
                "Total of $999,999.99 should be accepted");
    }

    @Test
    @DisplayName("BVT-08: Total exactly $1,000,000.00 (at maximum boundary)")
    void testExactlyAtMaximum_Success() throws Exception {
        Item maxItem = createItem("MAX-001", "Maximum Price", 1000000.0);
        Bill_Item billItem = new Bill_Item(maxItem, 1);
        ListProperty<Bill_Item> items = createListProperty(billItem);

        double result = bill.getTotalAmountfromItemsSold(items);

        assertEquals(1000000.0, result, 0.01,
                "Total of exactly $1,000,000.00 should be accepted");
    }

    @Test
    @DisplayName("BVT-09: Total $1,000,000.01 (just above maximum) throws exception")
    void testJustAboveMaximum_ThrowsException() {
        Item overLimitItem = createItem("OVERLIMIT-001", "Too Expensive", 1000000.01);
        Bill_Item billItem = new Bill_Item(overLimitItem, 1);
        ListProperty<Bill_Item> items = createListProperty(billItem);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> bill.getTotalAmountfromItemsSold(items),
                "Total exceeding $1,000,000 should throw exception"
        );

        assertTrue(exception.getMessage().contains("exceeds maximum"));
        assertTrue(exception.getMessage().contains("$1,000,000"));
    }

    @Test
    @DisplayName("BVT-10: Cumulative total exceeds max throws exception")
    void testCumulativeExceedsMax_ThrowsException() {
        Item item1 = createItem("ITEM-001", "Expensive 1", 700000.0);
        Item item2 = createItem("ITEM-002", "Expensive 2", 300000.01);
        ListProperty<Bill_Item> items = createListProperty(
                new Bill_Item(item1, 1),
                new Bill_Item(item2, 1)
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> bill.getTotalAmountfromItemsSold(items),
                "Cumulative total > $1,000,000 should throw exception"
        );

        assertTrue(exception.getMessage().contains("exceeds maximum"));
    }

    @Test
    @DisplayName("BVT-11: Multiple items totaling exactly $1,000,000")
    void testMultipleItemsAtMaxBoundary_Success() throws Exception {

        Item item1 = createItem("ITEM-001", "Item 1", 500000.0);
        Item item2 = createItem("ITEM-002", "Item 2", 300000.0);
        Item item3 = createItem("ITEM-003", "Item 3", 200000.0);
        ListProperty<Bill_Item> items = createListProperty(
                new Bill_Item(item1, 1),
                new Bill_Item(item2, 1),
                new Bill_Item(item3, 1)
        );

        double result = bill.getTotalAmountfromItemsSold(items);
        assertEquals(1000000.0, result, 0.01,
                "Three items totaling exactly $1,000,000 should be accepted");
    }

    @Test
    @DisplayName("BVT-12: Many items totaling exactly $1,000,000")
    void testManyItemsAtMaxBoundary_Success() throws Exception {

        ObservableList<Bill_Item> billItemsList = FXCollections.observableArrayList();
        for (int i = 1; i <= 10; i++) {
            Item item = createItem("ITEM-" + i, "Item " + i, 100000.0);
            billItemsList.add(new Bill_Item(item, 1));
        }
        ListProperty<Bill_Item> items = new SimpleListProperty<>(billItemsList);

        double result = bill.getTotalAmountfromItemsSold(items);

        assertEquals(1000000.0, result, 0.01,
                "10 items of $100,000 each should total exactly $1,000,000");
    }

    @Test
    @DisplayName("BVT: Null item in list throws exception")
    void testNullItemInList_ThrowsException() {
        ObservableList<Bill_Item> billItemsList = FXCollections.observableArrayList();
        billItemsList.add(null); //null item
        ListProperty<Bill_Item> items = new SimpleListProperty<>(billItemsList);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> bill.getTotalAmountfromItemsSold(items),
                "Null item in list should throw exception"
        );
        assertTrue(exception.getMessage().contains("null item"));
    }

    @Test
    @DisplayName("BVT: Null list returns 0.0")
    void testNullList_ReturnsZero() throws Exception {
        ListProperty<Bill_Item> nullList = null;
        double result = bill.getTotalAmountfromItemsSold(nullList);

        assertEquals(0.0, result, 0.001,
                "Null list should return 0.0");
    }

    private Item createItem(String id, String name, double price) {
        Item item = new Item("Item1", "Category1", "Supplier1", LocalDate.now(), 20.00, 50.00, 20);
        item.setItemID(id);
        item.setName(name);
        item.setSellingPrice(price);
        item.setQuantity(10);
        return item;
    }

    private ListProperty<Bill_Item> createListProperty(Bill_Item... items) {
        ObservableList<Bill_Item> list = FXCollections.observableArrayList(items);
        return new SimpleListProperty<>(list);
    }
}