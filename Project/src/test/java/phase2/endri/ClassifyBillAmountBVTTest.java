package phase2.endri;

import org.junit.jupiter.api.Test;


import static Controllers.EmployeePerformanceController.classifyBillAmount;
import static org.junit.jupiter.api.Assertions.*;

class ClassifyBillAmountBVTTest {



    // Normal Boundary Value Testing
    @Test
    void classifyBillAmount_atZero_returnsZero() {
        int result = classifyBillAmount(0.0);
        assertEquals(0, result);
    }

    @Test
    void classifyBillAmount_justAboveZero_returnsPositiveClassification() {
        int result = classifyBillAmount(0.01);
        assertEquals(1, result);
    }

    // Robust Boundary Value Testing

    @Test
    void classifyBillAmount_justBelowZero_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> classifyBillAmount(-0.01)
        );
    }


}

