package phase2.endri;

import Controllers.InventoryController;
import Models.Access;
import Models.Item;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CanSendLowStockAlertCoverageTest {

    private InventoryController controller;
    private User testUser;
    private ObservableList<Item> lowStockItems;

    @BeforeEach
    void setUp() {
        // Default user with NO access
        testUser = new User("test-user", Access.Cashier);

        // Controller is safe to construct (method under test is pure)
        controller = new InventoryController(testUser);

        // Default: empty low-stock list
        lowStockItems = FXCollections.observableArrayList();
    }

    /*
     * TC1
     * C1 = F, C2 = F
     * User has no access → false
     */
    @Test
    void noAccess_returnsFalse() {
        boolean result =
                controller.canSendLowStockAlert(testUser, lowStockItems);

        assertFalse(result);
    }

    /*
     * TC2
     * C1 = T, C2 = F, C3 = F
     * Manager, but no low-stock items → false
     */
    @Test
    void managerNoLowStock_returnsFalse() {
        testUser.setAccessLevel(Access.Manager);

        boolean result =
                controller.canSendLowStockAlert(testUser, lowStockItems);

        assertFalse(result);
    }

    /*
     * TC3
     * C1 = F, C2 = T, C3 = F
     * Administrator, but no low-stock items → false
     */
    @Test
    void adminNoLowStock_returnsFalse() {
        testUser.setAccessLevel(Access.Administrator);

        boolean result =
                controller.canSendLowStockAlert(testUser, lowStockItems);

        assertFalse(result);
    }

    /*
     * TC4
     * C1 = T, C2 = F, C3 = T
     * Manager with low-stock items → true
     */
    @Test
    void managerWithLowStock_returnsTrue() {
        testUser.setAccessLevel(Access.Manager);

        // Add a dummy low-stock item
        lowStockItems.add(new Item(
                "Test Item",
                "Category",
                "Supplier",
                java.time.LocalDate.now(),
                10.0,
                15.0,
                1
        ));

        boolean result =
                controller.canSendLowStockAlert(testUser, lowStockItems);

        assertTrue(result);
    }
}

