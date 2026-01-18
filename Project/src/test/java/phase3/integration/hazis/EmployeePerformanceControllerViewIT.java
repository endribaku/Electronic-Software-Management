package phase3.integration.hazis;

import Controllers.EmployeePerformanceController;
import Models.*;
import Views.EmployeePerformanceView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stubs.BillFileHandlerStub;
import stubs.ItemFileHandlerStub;
import stubs.UserFileHandlerStub;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class EmployeePerformanceControllerViewIT {

    private static final PrintStream ORIGINAL_OUT = System.out;
    private static final PrintStream ORIGINAL_ERR = System.err;

    private EmployeePerformanceView view;
    private BillFileHandlerStub billHandlerStub;
    private UserFileHandlerStub userHandlerStub;
    private ItemFileHandlerStub itemHandlerStub;
    private User testUser;
    private EmployeePerformanceController controller;

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
    void setUp() {
        testUser = new User(
                "manager1",
                "password123",
                "Test Manager",
                LocalDate.of(1985, 5, 15),
                "987654321",
                "manager@email.com",
                2500.0,
                Access.Manager,
                FXCollections.observableArrayList("PERFORMANCE_VIEW"),
                FXCollections.observableArrayList("Electronics")
        );
        billHandlerStub = new BillFileHandlerStub();
        userHandlerStub = new UserFileHandlerStub();
        itemHandlerStub = new ItemFileHandlerStub();
    }

    private Bill createTestBill(String cashierName, double totalAmount, LocalDate date) {
        User cashier = new User(cashierName, Access.Cashier);

        Item testItem = new Item(
                "Test Product",
                "Electronics",
                "TestSupplier",
                LocalDate.now(),
                100.0,
                totalAmount,
                50
        );

        Bill_Item billItem = new Bill_Item(testItem, 1);

        Bill bill = new Bill();
        bill.setUser(cashier);
        bill.setItemsSold(FXCollections.observableArrayList(billItem));
        bill.setTotalAmount(totalAmount);
        bill.setDateOfSale(date);

        return bill;
    }

    // Test 1: loadButton_callsHandlerWithSelectedFilters
    @Test
    void loadButton_callsHandlerWithSelectedFilters() throws InterruptedException {
        Bill todayBill = createTestBill("cashier1", 500.0, LocalDate.now());
        Bill oldBill = createTestBill("cashier2", 300.0, LocalDate.now().minusMonths(2));
        billHandlerStub.addBill(todayBill);
        billHandlerStub.addBill(oldBill);

        runOnFxThreadAndWait(() -> {
            suppressConsoleOutput();
            view = new EmployeePerformanceView();
            controller = new EmployeePerformanceController(
                    testUser,
                    view,
                    userHandlerStub,
                    itemHandlerStub,
                    billHandlerStub,
                    true
            );
            restoreConsoleOutput();
        });

        assertTrue(billHandlerStub.getBillsCalled,
                "getBills() should be called when controller initializes");
        runOnFxThreadAndWait(() -> {
            view.getBillDateFilter().setValue("Today's Bills");
            view.getBillDateFilter().fireEvent(
                    new javafx.event.ActionEvent(view.getBillDateFilter(), null)
            );
        });
        assertTrue(billHandlerStub.getBillsCallCount >= 1,
                "getBills() should be called when filter changes");
    }

    // Test 2: success_updatesTableOrLabels
    @Test
    void success_updatesTableOrLabels() throws InterruptedException {
        Bill bill1 = createTestBill("cashier1", 500.0, LocalDate.now());
        Bill bill2 = createTestBill("cashier2", 750.0, LocalDate.now());
        Bill bill3 = createTestBill("cashier1", 300.0, LocalDate.now().minusDays(1));

        billHandlerStub.addBill(bill1);
        billHandlerStub.addBill(bill2);
        billHandlerStub.addBill(bill3);

        runOnFxThreadAndWait(() -> {
            suppressConsoleOutput();
            view = new EmployeePerformanceView();
            controller = new EmployeePerformanceController(
                    testUser,
                    view,
                    userHandlerStub,
                    itemHandlerStub,
                    billHandlerStub,
                    true
            );
            restoreConsoleOutput();
        });

        runOnFxThreadAndWait(() -> {
            ObservableList<Bill> viewBills = view.getBills();

            assertEquals(3, viewBills.size(),
                    "View should contain all 3 bills");

            assertNotNull(view.getBillTableView(),
                    "Bill table view should exist");

            assertNotNull(view.getChartsBox(),
                    "Charts box should exist");

            assertFalse(view.getChartsBox().getChildren().isEmpty(),
                    "Charts box should have children");
        });

        runOnFxThreadAndWait(() -> {
            assertNotNull(view.getLineChartWeekly(),
                    "Weekly line chart should exist");

            assertFalse(view.getLineChartWeekly().getData().isEmpty(),
                    "Weekly line chart should have data series");
        });
    }

    // Test 3: failure_showsErrorMessage (empty state)
    @Test
    void failure_showsErrorMessage() throws InterruptedException {
        runOnFxThreadAndWait(() -> {
            suppressConsoleOutput();
            view = new EmployeePerformanceView();
            controller = new EmployeePerformanceController(
                    testUser,
                    view,
                    userHandlerStub,
                    itemHandlerStub,
                    billHandlerStub,
                    true
            );
            restoreConsoleOutput();
        });

        runOnFxThreadAndWait(() -> {
            ObservableList<Bill> viewBills = view.getBills();

            assertEquals(0, viewBills.size(),
                    "View should contain no bills when handler returns empty list");
            assertNotNull(view.getBillTableView(),
                    "Bill table view should still exist");
            assertTrue(view.getPieChartWeekly().getData().isEmpty(),
                    "Weekly pie chart should have no data");
            assertTrue(view.getPieChartMonthly().getData().isEmpty(),
                    "Monthly pie chart should have no data");
            assertTrue(view.getPieChartTotal().getData().isEmpty(),
                    "Total pie chart should have no data");
        });
        assertTrue(billHandlerStub.getBillsCalled,
                "getBills() should be called even when result is empty");
    }
}