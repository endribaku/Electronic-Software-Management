package phase2.moel;

import Controllers.BillManagementController;
import Models.Item;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class CanAddToBillBVTTest {

    @BeforeAll
    static void initJavaFxToolkit() throws Exception {
        // JavaFX Toolkit must be initialized once before any JavaFX Controls are created.
        // Platform.startup can only be called once per JVM, so we wrap it safely.
        try {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(latch::countDown);
            // Wait a bit to ensure toolkit is ready
            assertTrue(latch.await(3, TimeUnit.SECONDS), "JavaFX toolkit did not initialize in time");
        } catch (IllegalStateException alreadyInitialized) {
            // JavaFX toolkit already initialized -> OK
        }
    }

    @Test
    void canAddToBill_BVT_boundaries() {
        BillManagementController controller = new BillManagementController();

        Item itemStock10 = new Item(
                "TestItem",
                "TestCategory",
                "TestSupplier",
                LocalDate.now(),
                1.0,
                2.0,
                10
        );

        // Boundary around quantity > 0
        assertFalse(controller.canAddToBill(itemStock10, -1));
        assertFalse(controller.canAddToBill(itemStock10, 0));
        assertTrue(controller.canAddToBill(itemStock10, 1));

        // Boundary around stock rule (item.getQuantity() > quantity)
        assertTrue(controller.canAddToBill(itemStock10, 9));
        assertFalse(controller.canAddToBill(itemStock10, 10));
        assertFalse(controller.canAddToBill(itemStock10, 11));

        // Null item
        assertFalse(controller.canAddToBill(null, 1));
    }
}
