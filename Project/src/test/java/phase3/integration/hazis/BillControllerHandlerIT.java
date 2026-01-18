package phase3.integration.hazis;

import Controllers.BillManagementController;
import DAO.BillFileHandler;
import Exceptions.ItemStockException;
import Models.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import stubs.BillGenerateViewStub;
import stubs.InventoryFileHandlerStub;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class BillControllerHandlerIT {

    private static final PrintStream ORIGINAL_OUT = System.out;
    private static final PrintStream ORIGINAL_ERR = System.err;

    @TempDir
    File tempDir;

    private File billsDataFile;
    private File billsRepository;
    private BillFileHandler billFileHandler;
    private BillGenerateViewStub viewStub;
    private InventoryFileHandlerStub inventoryStub;
    private User testUser;

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

    @BeforeEach
    void setUp() {
        billsDataFile = new File(tempDir, "bills.dat");
        billsRepository = new File(tempDir, "BillsRepository");
        billsRepository.mkdirs();
        billFileHandler = new BillFileHandler(billsDataFile, billsRepository);
        viewStub = new BillGenerateViewStub();
        inventoryStub = new InventoryFileHandlerStub();

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
    }

    // Test 1: generateBill_valid_insertsBillInFile
    @Test
    void generateBill_valid_insertsBillInFile() throws Exception {
        // Arrange
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
        viewStub.addBillItem(billItem);
        suppressConsoleOutput();

        BillManagementController controller = new BillManagementController(
                testUser,
                viewStub,
                billFileHandler,
                inventoryStub
        );
        restoreConsoleOutput();

        controller.onGenerateBill();
        ObservableList<Bill> persistedBills = billFileHandler.getBills();

        assertEquals(1, persistedBills.size(), "One bill should be persisted");
        Bill persistedBill = persistedBills.get(0);
        assertEquals("testCashier", persistedBill.getUsername());
        assertEquals(1500.0, persistedBill.getTotalAmount(), 0.01);
        assertEquals(1, persistedBill.getItemsSold().size());
        assertTrue(viewStub.infoShown, "Success info should be displayed");
        assertEquals("Success", viewStub.lastInfoTitle);
    }

    // Test 2: generateBill_invalidItemQuantity_rejectsAndDoesNotInsert
    @Test
    void generateBill_invalidItemQuantity_rejectsAndDoesNotInsert() {
        Item testItem = new Item(
                "Phone",
                "Electronics",
                "TechSupplier",
                LocalDate.now(),
                300.0,
                500.0,
                5  // Only 5 in stock
        );

        assertThrows(ItemStockException.class, () -> {
            BillManagementController.validateAndCreateBillItem(testItem, 10);
        }, "Should throw ItemStockException when quantity exceeds stock");
        ObservableList<Bill> persistedBills = billFileHandler.getBills();
        assertEquals(0, persistedBills.size(), "No bills should be persisted");
    }

    // Test 3: saveBillToTxt_createsTextFile
    @Test
    void saveBillToTxt_createsTextFile() {
        Item testItem = new Item(
                "Tablet",
                "Electronics",
                "TechSupplier",
                LocalDate.now(),
                200.0,
                350.0,
                20
        );

        Bill_Item billItem = new Bill_Item(testItem, 3);
        ObservableList<Bill_Item> billItems = FXCollections.observableArrayList(billItem);
        Bill testBill = new Bill();
        testBill.setUser(testUser);
        testBill.setItemsSold(billItems);
        testBill.setTotalAmountfromItemsSold();
        testBill.setDateOfSale(LocalDate.of(2025, 1, 15));
        boolean saveResult = billFileHandler.saveBillToFile(testBill);

        assertTrue(saveResult, "saveBillToFile should return true");
        File[] txtFiles = billsRepository.listFiles((dir, name) -> name.endsWith(".txt"));
        assertNotNull(txtFiles, "Bills repository should contain files");
        assertEquals(1, txtFiles.length, "One text file should be created");

        String filename = txtFiles[0].getName();
        assertTrue(filename.startsWith("Bill"), "Filename should start with 'Bill'");
        assertTrue(filename.contains("15-01-2025"), "Filename should contain date");
        assertTrue(filename.endsWith(".txt"), "Filename should end with .txt");
        assertTrue(txtFiles[0].length() > 0, "Text file should have content");
    }
}