package phase2.hazis;

import Controllers.BillManagementController;
import Models.*;
import Exceptions.BillCreationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class GenerateBillECTest {

    @Test
    @DisplayName("EC-01: Null bill list + null user → BillCreationException")
    void testNullListNullUser_ThrowsException() {
        ObservableList<Bill_Item> nullList = null;
        User nullUser = null;

        BillCreationException exception = assertThrows(
                BillCreationException.class,
                () -> BillManagementController.generateBill(nullList, nullUser),
                "Null list should throw BillCreationException"
        );

        assertTrue(exception.getMessage().contains("No items added to bill"),
                "Exception should mention no items");
    }

    @Test
    @DisplayName("EC-02: Null bill list + valid user → BillCreationException")
    void testNullListValidUser_ThrowsException() {
        ObservableList<Bill_Item> nullList = null;
        User validUser = createUser();

        BillCreationException exception = assertThrows(
                BillCreationException.class,
                () -> BillManagementController.generateBill(nullList, validUser),
                "Null list should throw exception even with valid user"
        );

        assertTrue(exception.getMessage().contains("No items added to bill"));
    }

    @Test
    @DisplayName("EC-03: Empty bill list + null user → BillCreationException")
    void testEmptyListNullUser_ThrowsException() {
        ObservableList<Bill_Item> emptyList = FXCollections.observableArrayList();
        User nullUser = null;

        assertTrue(emptyList.isEmpty(), "Precondition: list should be empty");

        BillCreationException exception = assertThrows(
                BillCreationException.class,
                () -> BillManagementController.generateBill(emptyList, nullUser),
                "Empty list should throw BillCreationException"
        );

        assertTrue(exception.getMessage().contains("No items added to bill"));
    }

    @Test
    @DisplayName("EC-04: Empty bill list + valid user → BillCreationException")
    void testEmptyListValidUser_ThrowsException() {
        ObservableList<Bill_Item> emptyList = FXCollections.observableArrayList();
        User validUser = createUser();

        assertTrue(emptyList.isEmpty(), "Precondition: list should be empty");

        BillCreationException exception = assertThrows(
                BillCreationException.class,
                () -> BillManagementController.generateBill(emptyList, validUser),
                "Empty list should throw exception even with valid user"
        );

        assertTrue(exception.getMessage().contains("No items added to bill"));
    }

    @Test
    @DisplayName("EC-05: Non-empty list + null user → BillCreationException (if user validation added)")
    void testNonEmptyListNullUser_ThrowsException() {

        ObservableList<Bill_Item> billItems = FXCollections.observableArrayList();
        Item item = createItem("ITEM-001", "Laptop", 10, 750.0);
        billItems.add(new Bill_Item(item, 2));
        User nullUser = null;

        BillCreationException exception = assertThrows(
                BillCreationException.class,
                () -> BillManagementController.generateBill(billItems, nullUser),
                "Null user should throw exception"
        );

        assertTrue(exception.getMessage().contains("User must be logged in"));
    }

    @Test
    @DisplayName("EC-06: Non-empty list (1 item) + valid user → Bill created ✅")
    void testSingleItemValidUser_Success() {
        ObservableList<Bill_Item> billItems = FXCollections.observableArrayList();
        Item item = createItem("ITEM-001", "Laptop", 10, 750.0);
        billItems.add(new Bill_Item(item, 2));

        User validUser = createUser();

        assertEquals(1, billItems.size(), "Precondition: should have 1 item");

        Bill result = assertDoesNotThrow(
                () -> BillManagementController.generateBill(billItems, validUser),
                "Should not throw exception with valid inputs"
        );

        assertNotNull(result, "Bill should not be null");
        assertEquals(validUser.getUsername(), result.getUsername(),
                "Bill should have the correct user");
        assertEquals(1, result.getItemsSold().size(),
                "Bill should have 1 item");
        assertEquals(item, result.getItemsSold().get(0).getItem(),
                "Bill should contain the correct item");
        assertTrue(result.getTotalAmount() > 0,
                "Bill should have positive total amount");

        assertEquals(1500.0, result.getTotalAmount(), 0.01,
                "Bill total should be $1500.00");
    }

    @Test
    @DisplayName("EC-07: Non-empty list (multiple items) + valid user → Bill created ✅")
    void testMultipleItemsValidUser_Success() {
        ObservableList<Bill_Item> billItems = FXCollections.observableArrayList();

        Item item1 = createItem("ITEM-001", "Laptop", 10, 750.0);
        Item item2 = createItem("ITEM-002", "Mouse", 50, 25.0);
        Item item3 = createItem("ITEM-003", "Keyboard", 30, 60.0);

        billItems.add(new Bill_Item(item1, 2));
        billItems.add(new Bill_Item(item2, 5));
        billItems.add(new Bill_Item(item3, 1));

        User validUser = createUser();

        assertEquals(3, billItems.size(), "Precondition: should have 3 items");

        Bill result = assertDoesNotThrow(
                () -> BillManagementController.generateBill(billItems, validUser),
                "Should not throw exception with multiple items"
        );

        assertNotNull(result, "Bill should not be null");
        assertEquals(validUser.getUsername(), result.getUsername(),
                "Bill should have the correct user");
        assertEquals(3, result.getItemsSold().size(),
                "Bill should have 3 items");

        double expectedTotal = (2 * 750.0) + (5 * 25.0) + (1 * 60.0);
        assertEquals(expectedTotal, result.getTotalAmount(), 0.01,
                "Bill total should be $1685.00");
    }

    @Test
    @DisplayName("EC: Large quantity of items")
    void testLargeQuantity_Success() {
        ObservableList<Bill_Item> billItems = FXCollections.observableArrayList();

        for (int i = 1; i <= 10; i++) {
            Item item = createItem("ITEM-" + i, "Product " + i, 100, 50.0);
            billItems.add(new Bill_Item(item, 2));
        }

        User validUser = createUser();
        Bill result = BillManagementController.generateBill(billItems, validUser);

        assertEquals(10, result.getItemsSold().size());
        assertEquals(1000.0, result.getTotalAmount(), 0.01); // 10 items * 2 qty * $50
    }

    @Test
    @DisplayName("EC: Boundary - Exactly 1 item (minimum valid)")
    void testExactlyOneItem_Success() {
        ObservableList<Bill_Item> billItems = FXCollections.observableArrayList();
        Item item = createItem("ITEM-001", "Single Item", 5, 100.0);
        billItems.add(new Bill_Item(item, 1)); // Minimum quantity

        User validUser = createUser();
        Bill result = BillManagementController.generateBill(billItems, validUser);

        assertNotNull(result);
        assertEquals(1, result.getItemsSold().size());
        assertEquals(100.0, result.getTotalAmount(), 0.01);
    }


    private Item createItem(String id, String name, int stock, double price) {
        Item item = new Item("Item1", "Category1", "Supplier1", LocalDate.now(), 20.00, 50.00, 20);
        item.setItemID(id);
        item.setName(name);
        item.setQuantity(stock);
        item.setSellingPrice(price);
        item.setCategory("Test Category");
        item.setSupplier("Test Supplier");
        item.setPurchasePrice(price * 0.7);
        item.setPurchaseDate(LocalDate.now());
        return item;
    }

    private User createUser() {
        return new User("admin", "admin", "admin", LocalDate.now(), "123", "email@email.com", 500.00, Access.Administrator, FXCollections.observableArrayList(), FXCollections.observableArrayList());
    }
}