package phase2.marin;

import Controllers.EmployeeManagementController;
import Controllers.EmployeePerformanceController;
import Controllers.InventoryController;
import Models.Access;
import Models.Bill;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Method;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Suite for Marin Tartaraj's Assigned Methods
 * 
 * This suite contains exactly THREE test analyses as required:
 * 1. ONE Boundary Value Test (BVT) for validateItemNumericFields
 * 2. ONE Equivalence Class Test (EC) for filterBills
 * 3. ONE Code Coverage Test Suite for isValidAdminConfiguration
 */
public class MarinTestSuite {

    private InventoryController inventoryController;
    private EmployeeManagementController employeeManagementController;
    private EmployeePerformanceController employeePerformanceController;
    private User testUser;

    @BeforeAll
    static void initializeJavaFX() {
        // JavaFX toolkit initialization may be needed for some tests
        // This is a placeholder - actual initialization depends on test environment
    }

    @BeforeEach
    void setUp() {
        // Create a test user for controllers that require it
        ObservableList<String> emptyPermissions = FXCollections.observableArrayList();
        ObservableList<String> emptySectors = FXCollections.observableArrayList();
        testUser = new User(
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
        employeeManagementController = new EmployeeManagementController();
        employeePerformanceController = new EmployeePerformanceController(testUser);
    }

    // ====================================================================
    // ANALYSIS 1: BOUNDARY VALUE TEST (BVT)
    // ====================================================================
    /**
     * Method: validateItemNumericFields(int quantity, double purchasePrice, double sellingPrice)
     * Location: InventoryController.java (line 538-540)
     * Analysis Type: Boundary Value Testing (BVT)
     * 
     * Tests boundary values around 0: -1, 0, 1 for each numeric parameter.
     * The method returns true only if all three parameters are > 0.
     */
    @ParameterizedTest(name = "BVT: quantity={0}, purchasePrice={1}, sellingPrice={2}, expected={3}")
    @CsvSource({
        // Boundary value -1 (just below boundary)
        "-1, -1.0, -1.0, false",
        "-1, -1.0, 0.0, false",
        "-1, -1.0, 1.0, false",
        "-1, 0.0, -1.0, false",
        "-1, 0.0, 0.0, false",
        "-1, 0.0, 1.0, false",
        "-1, 1.0, -1.0, false",
        "-1, 1.0, 0.0, false",
        "-1, 1.0, 1.0, false",
        
        // Boundary value 0 (on boundary)
        "0, -1.0, -1.0, false",
        "0, -1.0, 0.0, false",
        "0, -1.0, 1.0, false",
        "0, 0.0, -1.0, false",
        "0, 0.0, 0.0, false",
        "0, 0.0, 1.0, false",
        "0, 1.0, -1.0, false",
        "0, 1.0, 0.0, false",
        "0, 1.0, 1.0, false",
        
        // Boundary value 1 (just above boundary)
        "1, -1.0, -1.0, false",
        "1, -1.0, 0.0, false",
        "1, -1.0, 1.0, false",
        "1, 0.0, -1.0, false",
        "1, 0.0, 0.0, false",
        "1, 0.0, 1.0, false",
        "1, 1.0, -1.0, false",
        "1, 1.0, 0.0, false",
        "1, 1.0, 1.0, true"  // Only case where all are > 0
    })
    void testValidateItemNumericFields_BVT(int quantity, double purchasePrice, double sellingPrice, boolean expected) {
        boolean result = inventoryController.validateItemNumericFields(quantity, purchasePrice, sellingPrice);
        assertEquals(expected, result, 
            String.format("BVT failed for quantity=%d, purchasePrice=%.1f, sellingPrice=%.1f", 
                quantity, purchasePrice, sellingPrice));
    }

    // ====================================================================
    // ANALYSIS 2: EQUIVALENCE CLASS TEST (EC)
    // ====================================================================
    /**
     * Method: filterBills(String filter, ObservableList<Bill> bills, TableView<Bill> billTableView)
     * Location: EmployeePerformanceController.java (line 94-124)
     * Analysis Type: Equivalence Class Testing (EC)
     * 
     * Tests different equivalence classes for filter string:
     * - EC1: "Today's Bills" -> bills with date == today
     * - EC2: "This Month's Bills" -> bills with same month and year
     * - EC3: "This Year's Bills" -> bills with same year
     * - EC4: "Total Bills" -> all bills
     * - EC5: default/null -> all bills
     */
    @Test
    void testFilterBills_EC() throws Exception {
        // Create bills with different dates for equivalence class testing
        LocalDate today = LocalDate.now();
        LocalDate thisMonth = today.withDayOfMonth(15); // Same month, different day
        LocalDate thisYear = today.withMonth(6).withDayOfMonth(15); // Same year, different month
        LocalDate otherYear = today.minusYears(1); // Different year

        // Create test bills
        Bill billToday = createTestBill("user1", today, 100.0);
        Bill billThisMonth = createTestBill("user2", thisMonth, 200.0);
        Bill billThisYear = createTestBill("user3", thisYear, 300.0);
        Bill billOtherYear = createTestBill("user4", otherYear, 400.0);

        ObservableList<Bill> allBills = FXCollections.observableArrayList(
            billToday, billThisMonth, billThisYear, billOtherYear
        );

        TableView<Bill> billTableView = new TableView<>();

        // Use reflection to access private filterBills method
        Method filterBillsMethod = EmployeePerformanceController.class.getDeclaredMethod(
            "filterBills", String.class, ObservableList.class, TableView.class
        );
        filterBillsMethod.setAccessible(true);

        // EC1: "Today's Bills" -> should only include billToday
        filterBillsMethod.invoke(employeePerformanceController, "Today's Bills", allBills, billTableView);
        ObservableList<Bill> filtered = billTableView.getItems();
        assertEquals(1, filtered.size(), "EC1: Today's Bills should filter to 1 bill");
        assertTrue(filtered.contains(billToday), "EC1: Should contain today's bill");
        assertFalse(filtered.contains(billThisMonth), "EC1: Should not contain this month's bill");
        assertFalse(filtered.contains(billThisYear), "EC1: Should not contain this year's bill");
        assertFalse(filtered.contains(billOtherYear), "EC1: Should not contain other year's bill");

        // EC2: "This Month's Bills" -> should include billToday and billThisMonth
        filterBillsMethod.invoke(employeePerformanceController, "This Month's Bills", allBills, billTableView);
        filtered = billTableView.getItems();
        assertEquals(2, filtered.size(), "EC2: This Month's Bills should filter to 2 bills");
        assertTrue(filtered.contains(billToday), "EC2: Should contain today's bill");
        assertTrue(filtered.contains(billThisMonth), "EC2: Should contain this month's bill");
        assertFalse(filtered.contains(billThisYear), "EC2: Should not contain this year's bill");
        assertFalse(filtered.contains(billOtherYear), "EC2: Should not contain other year's bill");

        // EC3: "This Year's Bills" -> should include billToday, billThisMonth, and billThisYear
        filterBillsMethod.invoke(employeePerformanceController, "This Year's Bills", allBills, billTableView);
        filtered = billTableView.getItems();
        assertEquals(3, filtered.size(), "EC3: This Year's Bills should filter to 3 bills");
        assertTrue(filtered.contains(billToday), "EC3: Should contain today's bill");
        assertTrue(filtered.contains(billThisMonth), "EC3: Should contain this month's bill");
        assertTrue(filtered.contains(billThisYear), "EC3: Should contain this year's bill");
        assertFalse(filtered.contains(billOtherYear), "EC3: Should not contain other year's bill");

        // EC4: "Total Bills" -> should include all bills
        filterBillsMethod.invoke(employeePerformanceController, "Total Bills", allBills, billTableView);
        filtered = billTableView.getItems();
        assertEquals(4, filtered.size(), "EC4: Total Bills should include all 4 bills");
        assertTrue(filtered.contains(billToday), "EC4: Should contain today's bill");
        assertTrue(filtered.contains(billThisMonth), "EC4: Should contain this month's bill");
        assertTrue(filtered.contains(billThisYear), "EC4: Should contain this year's bill");
        assertTrue(filtered.contains(billOtherYear), "EC4: Should contain other year's bill");

        // EC5: default/null -> should include all bills (same as Total Bills)
        filterBillsMethod.invoke(employeePerformanceController, "Unknown Filter", allBills, billTableView);
        filtered = billTableView.getItems();
        assertEquals(4, filtered.size(), "EC5: Default filter should include all 4 bills");
        assertTrue(filtered.contains(billToday), "EC5: Should contain today's bill");
        assertTrue(filtered.contains(billThisMonth), "EC5: Should contain this month's bill");
        assertTrue(filtered.contains(billThisYear), "EC5: Should contain this year's bill");
        assertTrue(filtered.contains(billOtherYear), "EC5: Should contain other year's bill");
    }

    // ====================================================================
    // ANALYSIS 3: CODE COVERAGE TEST SUITE
    // ====================================================================
    /**
     * Method: isValidAdminConfiguration(Access access, ObservableList<String> selectedPermissions,
     *                                   ObservableList<String> selectedSectors,
     *                                   ObservableList<String> allPermissions,
     *                                   ObservableList<String> allSectors)
     * Location: EmployeeManagementController.java (line 290-303)
     * Analysis Type: Code Coverage Test Suite (Statement/Branch/Condition/MC/DC)
     * 
     * Coverage Goals:
     * - Statement Coverage: All statements executed
     * - Branch Coverage: Both branches (if-else) covered
     * - Condition Coverage: All conditions independently tested
     * - MC/DC: Each condition independently affects the decision outcome
     * 
     * Conditions:
     * - C1: access != Access.Administrator (true/false)
     * - C2: selectedPermissions.size() == allPermissions.size() (true/false)
     * - C3: selectedSectors.size() == allSectors.size() (true/false)
     * 
     * MC/DC Pairs:
     * - Pair 1: (C1=true) -> returns true (C1 independently determines outcome)
     * - Pair 2: (C1=false, C2=true, C3=true) -> returns true
     * - Pair 3: (C1=false, C2=true, C3=false) -> returns false (C3 independently determines outcome)
     * - Pair 4: (C1=false, C2=false, C3=true) -> returns false (C2 independently determines outcome)
     * - Pair 5: (C1=false, C2=false, C3=false) -> returns false
     */
    
    @Test
    void testIsValidAdminConfiguration_Coverage_NonAdmin() {
        // MC/DC Pair 1: C1=true (access != Administrator)
        // This condition independently determines the outcome (returns true)
        // Tests Branch 1: if (access != Access.Administrator) return true;
        
        ObservableList<String> selectedPerms = FXCollections.observableArrayList("PERM1");
        ObservableList<String> selectedSectors = FXCollections.observableArrayList("SECTOR1");
        ObservableList<String> allPerms = FXCollections.observableArrayList("PERM1", "PERM2");
        ObservableList<String> allSectors = FXCollections.observableArrayList("SECTOR1", "SECTOR2");

        // Test with Manager (non-Administrator)
        boolean result = employeeManagementController.isValidAdminConfiguration(
            Access.Manager, selectedPerms, selectedSectors, allPerms, allSectors
        );
        assertTrue(result, "Non-admin should return true regardless of permission/sector selection");

        // Test with Cashier (non-Administrator)
        result = employeeManagementController.isValidAdminConfiguration(
            Access.Cashier, selectedPerms, selectedSectors, allPerms, allSectors
        );
        assertTrue(result, "Non-admin should return true regardless of permission/sector selection");
    }

    @Test
    void testIsValidAdminConfiguration_Coverage_AdminAllSelected() {
        // MC/DC Pair 2: C1=false, C2=true, C3=true -> returns true
        // Tests Branch 2: return selectedPermissions.size() == allPermissions.size() 
        //                && selectedSectors.size() == allSectors.size();
        
        ObservableList<String> allPerms = FXCollections.observableArrayList("PERM1", "PERM2");
        ObservableList<String> allSectors = FXCollections.observableArrayList("SECTOR1", "SECTOR2");
        ObservableList<String> selectedPerms = FXCollections.observableArrayList("PERM1", "PERM2");
        ObservableList<String> selectedSectors = FXCollections.observableArrayList("SECTOR1", "SECTOR2");

        boolean result = employeeManagementController.isValidAdminConfiguration(
            Access.Administrator, selectedPerms, selectedSectors, allPerms, allSectors
        );
        assertTrue(result, "Admin with all permissions and sectors selected should return true");
    }

    @Test
    void testIsValidAdminConfiguration_Coverage_AdminMissingSectors() {
        // MC/DC Pair 3: C1=false, C2=true, C3=false -> returns false
        // C3 (selectedSectors.size() == allSectors.size()) independently determines outcome
        // This demonstrates that C3 can independently affect the decision
        
        ObservableList<String> allPerms = FXCollections.observableArrayList("PERM1", "PERM2");
        ObservableList<String> allSectors = FXCollections.observableArrayList("SECTOR1", "SECTOR2", "SECTOR3");
        ObservableList<String> selectedPerms = FXCollections.observableArrayList("PERM1", "PERM2"); // All selected
        ObservableList<String> selectedSectors = FXCollections.observableArrayList("SECTOR1", "SECTOR2"); // Missing one

        boolean result = employeeManagementController.isValidAdminConfiguration(
            Access.Administrator, selectedPerms, selectedSectors, allPerms, allSectors
        );
        assertFalse(result, "Admin missing sectors should return false (C3 independently determines outcome)");
    }

    @Test
    void testIsValidAdminConfiguration_Coverage_AdminMissingPermissions() {
        // MC/DC Pair 4: C1=false, C2=false, C3=true -> returns false
        // C2 (selectedPermissions.size() == allPermissions.size()) independently determines outcome
        // This demonstrates that C2 can independently affect the decision
        
        ObservableList<String> allPerms = FXCollections.observableArrayList("PERM1", "PERM2", "PERM3");
        ObservableList<String> allSectors = FXCollections.observableArrayList("SECTOR1", "SECTOR2");
        ObservableList<String> selectedPerms = FXCollections.observableArrayList("PERM1", "PERM2"); // Missing one
        ObservableList<String> selectedSectors = FXCollections.observableArrayList("SECTOR1", "SECTOR2"); // All selected

        boolean result = employeeManagementController.isValidAdminConfiguration(
            Access.Administrator, selectedPerms, selectedSectors, allPerms, allSectors
        );
        assertFalse(result, "Admin missing permissions should return false (C2 independently determines outcome)");
    }

    @Test
    void testIsValidAdminConfiguration_Coverage_AdminMissingBoth() {
        // MC/DC Pair 5: C1=false, C2=false, C3=false -> returns false
        // Tests the case where both conditions are false
        
        ObservableList<String> allPerms = FXCollections.observableArrayList("PERM1", "PERM2", "PERM3");
        ObservableList<String> allSectors = FXCollections.observableArrayList("SECTOR1", "SECTOR2", "SECTOR3");
        ObservableList<String> selectedPerms = FXCollections.observableArrayList("PERM1"); // Missing two
        ObservableList<String> selectedSectors = FXCollections.observableArrayList("SECTOR1"); // Missing two

        boolean result = employeeManagementController.isValidAdminConfiguration(
            Access.Administrator, selectedPerms, selectedSectors, allPerms, allSectors
        );
        assertFalse(result, "Admin missing both permissions and sectors should return false");
    }

    // ====================================================================
    // HELPER METHODS
    // ====================================================================

    /**
     * Helper method to create a test Bill with a specific date
     */
    private Bill createTestBill(String username, LocalDate date, double totalAmount) {
        Bill bill = new Bill();
        bill.setUsername(username);
        bill.setDateOfSale(date);
        bill.setTotalAmount(totalAmount);
        return bill;
    }
}
