package phase2.marin;

import Controllers.InventoryController;
import Models.Access;
import Models.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidateItemNumericFieldsBVTTest {

    private InventoryController inventoryController;

    @BeforeAll
    static void initJavaFxToolkit() throws Exception {
        try {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(latch::countDown);
            assertTrue(latch.await(3, TimeUnit.SECONDS), "JavaFX toolkit did not initialize in time");
        } catch (IllegalStateException alreadyInitialized) {
            // JavaFX toolkit already initialized -> OK
        }
    }

    @BeforeEach
    void setUp() {
        ObservableList<String> emptyPermissions = FXCollections.observableArrayList();
        ObservableList<String> emptySectors = FXCollections.observableArrayList();
        User testUser = new User(
                "testuser",
                "password",
                "Test User",
                LocalDate.of(1990, 1, 1),
                "123456789",
                "test@test.com",
                1000.0,
                Access.Cashier,
                emptyPermissions,
                emptySectors
        );
        inventoryController = new InventoryController(testUser);
    }

    @Test
    void validateItemNumericFields_BVT_boundaries() {
        int[] quantities = {-1, 0, 1};
        double[] prices = {-1.0, 0.0, 1.0};

        for (int quantity : quantities) {
            for (double purchasePrice : prices) {
                for (double sellingPrice : prices) {
                    boolean expected = quantity > 0 && purchasePrice > 0 && sellingPrice > 0;
                    boolean result = inventoryController.validateItemNumericFields(
                            quantity, purchasePrice, sellingPrice
                    );
                    assertEquals(expected, result,
                            String.format("BVT failed for quantity=%d, purchasePrice=%.1f, sellingPrice=%.1f",
                                    quantity, purchasePrice, sellingPrice));
                }
            }
        }
    }
}
