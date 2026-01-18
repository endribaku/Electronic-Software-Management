package phase2.daron;

import Controllers.InventoryController;
import Models.Category;
import Models.Supplier;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class IsItemAddInputValidCoverageTest {

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

    private static InventoryController controllerNoCtor() {
        return allocateWithoutConstructor(InventoryController.class);
    }

    private static Category dummyCategory() {
        return allocateWithoutConstructor(Category.class);
    }

    private static Supplier dummySupplier() {
        return allocateWithoutConstructor(Supplier.class);
    }

    private static String parseNameToken(String token) {
        if ("<NULL>".equals(token)) return null;
        if ("''".equals(token)) return "";
        return token;
    }

    @ParameterizedTest(name = "CCT-{0} expectedValid={8}")
    @CsvSource({
            // id, itemNameToken, categoryNull, supplierNull, quantity, pPrice, sPrice, expectedValid
            "1, Test,   false, false, 10, 5.0, 10.0, true",
            "2, <NULL>, false, false, 10, 5.0, 10.0, false",
            "3, '',     false, false, 10, 5.0, 10.0, false",
            "4, Test,   true,  false, 10, 5.0, 10.0, false",
            "5, Test,   false, true,  10, 5.0, 10.0, false",
            "6, Test,   false, false, 0,  5.0, 10.0, false",
            "7, Test,   false, false, 10, 0.0, 10.0, false",
            "8, Test,   false, false, 10, 5.0, 0.0,  false"
    })
    void cct_isItemAddInputValid_statementBranchConditionAndMcdc(
            int id,
            String itemNameToken,
            boolean categoryNull,
            boolean supplierNull,
            int quantity,
            double purchasePrice,
            double sellingPrice,
            boolean expectedValid
    ) {
        InventoryController c = controllerNoCtor();

        String itemName = parseNameToken(itemNameToken);
        Category category = categoryNull ? null : dummyCategory();
        Supplier supplier = supplierNull ? null : dummySupplier();

        boolean actual = c.isItemAddInputValid(
                itemName, category, supplier,
                quantity, purchasePrice, sellingPrice
        );

        assertEquals(expectedValid, actual, "CCT-" + id + " must match expected outcome");
    }
}