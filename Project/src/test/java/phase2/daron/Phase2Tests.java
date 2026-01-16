package phase2.daron;

import DAO.InventoryFileHandler;
import Controllers.InventoryController;
import Models.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class Phase2Tests {

    private static Item itemWithQuantity(int qty) {

        return new Item(
                "TestItem",
                "TestCategory",
                "TestSupplier",
                LocalDate.now(),
                5.0,
                10.0,
                qty
        );
    }

    private static <T> T allocateWithoutConstructor(Class<T> cls) {
        try {
            Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);

            @SuppressWarnings("unchecked")
            T instance = (T) unsafe.allocateInstance(cls);
            return instance;
        } catch (Exception e) {
            fail("Failed to allocate " + cls.getSimpleName() + " without constructor: " + e.getMessage());
            return null;
        }
    }

    private static <T> T dummyNonNull(Class<T> cls) {
        return allocateWithoutConstructor(cls);
    }

    @ParameterizedTest(name = "BVT: quantity={0} => included={1}")
    @CsvSource({
            "4, true",
            "5, false",
            "3, true",
            "6, false"
    })
    void bvt_checkForLowStock_boundaryAround5(int quantity, boolean shouldBeIncluded) {
        ObservableList<Item> items = FXCollections.observableArrayList(itemWithQuantity(quantity));

        ObservableList<Item> lowStock = InventoryFileHandler.checkForLowStock(items);

        if (shouldBeIncluded) {
            assertEquals(1, lowStock.size(), "Expected the item to be included as low stock");
            assertSame(items.get(0), lowStock.get(0), "Expected the same item reference to be returned");
        } else {
            assertTrue(lowStock.isEmpty(), "Expected no low stock items for quantity >= 5");
        }
    }

    @Test
    void ec_checkForLowStock_representatives_lowVsSufficient_noBoundariesUsed() {
        Item low = itemWithQuantity(2);
        Item sufficient1 = itemWithQuantity(7);
        Item sufficient2 = itemWithQuantity(10);

        ObservableList<Item> items = FXCollections.observableArrayList(low, sufficient1, sufficient2);

        ObservableList<Item> lowStock = InventoryFileHandler.checkForLowStock(items);

        assertEquals(1, lowStock.size(), "Only the low-stock representative should be returned");
        assertSame(low, lowStock.get(0), "Returned list should contain only the low-stock item");
    }

    private static final String VALID_NAME = "Test";
    private static final int VALID_QTY = 10;
    private static final double VALID_PPRICE = 5.0;
    private static final double VALID_SPRICE = 10.0;

    private static final Models.Category NON_NULL_CATEGORY = dummyNonNull(Models.Category.class);
    private static final Models.Supplier NON_NULL_SUPPLIER = dummyNonNull(Models.Supplier.class);

    private static InventoryController controllerNoCtor() {
        return allocateWithoutConstructor(InventoryController.class);
    }

    @Test
    void cct1_validBaseline_returnsTrue() {
        InventoryController c = controllerNoCtor();

        boolean result = c.isItemAddInputValid(
                VALID_NAME, NON_NULL_CATEGORY, NON_NULL_SUPPLIER,
                VALID_QTY, VALID_PPRICE, VALID_SPRICE
        );

        assertTrue(result);
    }

    @Test
    void cct2_itemNameNull_returnsFalse() {
        InventoryController c = controllerNoCtor();

        boolean result = c.isItemAddInputValid(
                null, NON_NULL_CATEGORY, NON_NULL_SUPPLIER,
                VALID_QTY, VALID_PPRICE, VALID_SPRICE
        );

        assertFalse(result);
    }

    @Test
    void cct3_itemNameEmpty_returnsFalse() {
        InventoryController c = controllerNoCtor();

        boolean result = c.isItemAddInputValid(
                "", NON_NULL_CATEGORY, NON_NULL_SUPPLIER,
                VALID_QTY, VALID_PPRICE, VALID_SPRICE
        );

        assertFalse(result);
    }

    @Test
    void cct4_categoryNull_returnsFalse() {
        InventoryController c = controllerNoCtor();

        boolean result = c.isItemAddInputValid(
                VALID_NAME, null, NON_NULL_SUPPLIER,
                VALID_QTY, VALID_PPRICE, VALID_SPRICE
        );

        assertFalse(result);
    }

    @Test
    void cct5_supplierNull_returnsFalse() {
        InventoryController c = controllerNoCtor();

        boolean result = c.isItemAddInputValid(
                VALID_NAME, NON_NULL_CATEGORY, null,
                VALID_QTY, VALID_PPRICE, VALID_SPRICE
        );

        assertFalse(result);
    }

    @Test
    void cct6_quantityZero_returnsFalse() {
        InventoryController c = controllerNoCtor();

        boolean result = c.isItemAddInputValid(
                VALID_NAME, NON_NULL_CATEGORY, NON_NULL_SUPPLIER,
                0, VALID_PPRICE, VALID_SPRICE
        );

        assertFalse(result);
    }

    @Test
    void cct7_purchasePriceZero_returnsFalse() {
        InventoryController c = controllerNoCtor();

        boolean result = c.isItemAddInputValid(
                VALID_NAME, NON_NULL_CATEGORY, NON_NULL_SUPPLIER,
                VALID_QTY, 0.0, VALID_SPRICE
        );

        assertFalse(result);
    }

    @Test
    void cct8_sellingPriceZero_returnsFalse() {
        InventoryController c = controllerNoCtor();

        boolean result = c.isItemAddInputValid(
                VALID_NAME, NON_NULL_CATEGORY, NON_NULL_SUPPLIER,
                VALID_QTY, VALID_PPRICE, 0.0
        );

        assertFalse(result);
    }
}