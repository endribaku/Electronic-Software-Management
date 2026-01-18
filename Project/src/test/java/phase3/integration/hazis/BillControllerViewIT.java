package phase3.integration.hazis;

import Controllers.BillManagementController;
import Models.*;
import Views.BillGenerateView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stubs.BillFileHandlerStub;
import stubs.InventoryFileHandlerStub;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class BillControllerViewIT {

    private static final PrintStream ORIGINAL_OUT = System.out;
    private static final PrintStream ORIGINAL_ERR = System.err;

    private BillGenerateView view;
    private BillFileHandlerStub billHandlerStub;
    private InventoryFileHandlerStub inventoryStub;
    private User testUser;
    private BillManagementController controller;

    @BeforeAll
    static void initJavaFX() throws InterruptedException {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // Toolkit already initialized
        }

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(latch::countDown);
        latch.await();
    }

    private static void suppressConsoleOutput() {
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        System.setErr(new PrintStream(new ByteArrayOutputStream()));
    }

    private static void restoreConsoleOutput() {
        System.setOut(ORIGINAL_OUT);
        System.setErr(ORIGINAL_ERR);
    }

    private void runOnFxThreadAndWait(Runnable action) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } finally {
                latch.countDown();
            }
        });
        assertTrue(latch.await(5, TimeUnit.SECONDS), "JavaFX operation timed out");
    }

    @BeforeEach
    void setUp() throws InterruptedException {
        // Create test user
        testUser = new User(
                "testCashier",
                "password123",
                "Test Cashier",
                LocalDate.of(1990, 1, 1),
                "123456789",
                "test@email.com",
                1000.0,
                Access.Cashier,
                FXCollections.observableArrayList("BILL_GENERATION"),
                FXCollections.observableArrayList("Electronics")
        );

        billHandlerStub = new BillFileHandlerStub();
        inventoryStub = new InventoryFileHandlerStub();
        runOnFxThreadAndWait(() -> {
            suppressConsoleOutput();
            view = new BillGenerateView();
            controller = new BillManagementController(
                    testUser,
                    view,
                    billHandlerStub,
                    inventoryStub
            );
            restoreConsoleOutput();
        });
    }

    // Test 1: generateBillButton_callsInsertBill
    @Test
    void generateBillButton_callsInsertBill() throws InterruptedException {
        Item testItem = new Item(
                "Laptop",
                "Electronics",
                "TechSupplier",
                LocalDate.now(),
                500.0,
                750.0,
                10
        );
        Bill_Item billItem = new Bill_Item(testItem, 2);

        runOnFxThreadAndWait(() -> {
            view.addBillItem(billItem);
        });
        runOnFxThreadAndWait(() -> {
            view.getCreateBillButton().fire();
        });

        assertTrue(billHandlerStub.insertBillCalled,
                "insertBill() should be called when Create Bill button is clicked");
        assertNotNull(billHandlerStub.lastInsertedBill,
                "A bill should have been passed to insertBill()");
        assertEquals("testCashier", billHandlerStub.lastInsertedBill.getUsername(),
                "Inserted bill should have correct username");
    }

    // Test 2: success_showsSuccessAlertOrMessage
    @Test
    void success_showsSuccessAlertOrMessage() throws InterruptedException {
        Item testItem = new Item(
                "Phone",
                "Electronics",
                "TechSupplier",
                LocalDate.now(),
                300.0,
                500.0,
                15
        );

        Bill_Item billItem = new Bill_Item(testItem, 1);

        runOnFxThreadAndWait(() -> {
            view.addBillItem(billItem);
        });
        runOnFxThreadAndWait(() -> {
            view.getCreateBillButton().fire();
        });

        assertTrue(billHandlerStub.insertBillCalled,
                "Bill should be inserted on success");
        assertTrue(billHandlerStub.saveBillToFileCalled,
                "Bill should be saved to text file on success");
        runOnFxThreadAndWait(() -> {
            assertEquals(0, view.getBillItems().size(),
                    "Bill items should be cleared after successful generation");
        });
    }

    // Test 3: failure_showsErrorAlertOrMessage
    @Test
    void failure_showsErrorAlertOrMessage() throws InterruptedException {

        runOnFxThreadAndWait(() -> {
            view.getCreateBillButton().fire();
        });
        assertFalse(billHandlerStub.insertBillCalled,
                "insertBill() should NOT be called when bill has no items");
        assertFalse(billHandlerStub.saveBillToFileCalled,
                "saveBillToFile() should NOT be called when bill creation fails");
    }
}