package phase2.endri;

import Misc.StockUpdateResult;
import Controllers.InventoryController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EvaluateStockUpdateECTest {

    /*
     * EC1: quantity < 0
     */
    @Test
    void negativeQuantity_returnsInvalid() {
        StockUpdateResult result =
                InventoryController.evaluateStockUpdate(-5);

        assertEquals(StockUpdateResult.INVALID, result);
    }

    /*
     * EC2: quantity == 0
     */
    @Test
    void zeroQuantity_returnsOutOfStock() {
        StockUpdateResult result =
                InventoryController.evaluateStockUpdate(0);

        assertEquals(StockUpdateResult.OUT_OF_STOCK, result);
    }

    /*
     * EC3: quantity > 0
     */
    @Test
    void positiveQuantity_returnsValid() {
        StockUpdateResult result =
                InventoryController.evaluateStockUpdate(10);

        assertEquals(StockUpdateResult.VALID, result);
    }
}
