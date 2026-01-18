package phase2.marin;

import Controllers.EmployeePerformanceController;
import Models.Access;
import Models.Bill;
import Models.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class FilterBillsECTest {

    private EmployeePerformanceController employeePerformanceController;

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
        employeePerformanceController = new EmployeePerformanceController(testUser);
    }

    @Test
    void filterBills_EC_representativeFilters() throws Exception {
        LocalDate today = LocalDate.now();
        LocalDate thisMonth = today.withDayOfMonth(15);
        LocalDate thisYear = today.withMonth(6).withDayOfMonth(15);
        LocalDate otherYear = today.minusYears(1);

        Bill billToday = createTestBill("user1", today, 100.0);
        Bill billThisMonth = createTestBill("user2", thisMonth, 200.0);
        Bill billThisYear = createTestBill("user3", thisYear, 300.0);
        Bill billOtherYear = createTestBill("user4", otherYear, 400.0);

        ObservableList<Bill> allBills = FXCollections.observableArrayList(
                billToday, billThisMonth, billThisYear, billOtherYear
        );
        TableView<Bill> billTableView = new TableView<>();

        Method filterBillsMethod = EmployeePerformanceController.class.getDeclaredMethod(
                "filterBills", String.class, ObservableList.class, TableView.class
        );
        filterBillsMethod.setAccessible(true);

        filterBillsMethod.invoke(employeePerformanceController, "Today's Bills", allBills, billTableView);
        ObservableList<Bill> filtered = billTableView.getItems();
        assertEquals(1, filtered.size());
        assertTrue(filtered.contains(billToday));

        filterBillsMethod.invoke(employeePerformanceController, "This Month's Bills", allBills, billTableView);
        filtered = billTableView.getItems();
        assertEquals(2, filtered.size());
        assertTrue(filtered.contains(billToday));
        assertTrue(filtered.contains(billThisMonth));

        filterBillsMethod.invoke(employeePerformanceController, "This Year's Bills", allBills, billTableView);
        filtered = billTableView.getItems();
        assertEquals(3, filtered.size());
        assertTrue(filtered.contains(billToday));
        assertTrue(filtered.contains(billThisMonth));
        assertTrue(filtered.contains(billThisYear));

        filterBillsMethod.invoke(employeePerformanceController, "Total Bills", allBills, billTableView);
        filtered = billTableView.getItems();
        assertEquals(4, filtered.size());
        assertTrue(filtered.contains(billOtherYear));

        filterBillsMethod.invoke(employeePerformanceController, "Unknown Filter", allBills, billTableView);
        filtered = billTableView.getItems();
        assertEquals(4, filtered.size());
    }

    private Bill createTestBill(String username, LocalDate date, double totalAmount) {
        Bill bill = new Bill();
        bill.setUsername(username);
        bill.setDateOfSale(date);
        bill.setTotalAmount(totalAmount);
        return bill;
    }
}
