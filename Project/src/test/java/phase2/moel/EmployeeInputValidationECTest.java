package phase2.moel;

import Controllers.EmployeeManagementController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeInputValidationECTest {

    @BeforeAll
    static void initJavaFxToolkit() throws Exception {
        try {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(latch::countDown);
            assertTrue(latch.await(3, TimeUnit.SECONDS),
                    "JavaFX toolkit did not initialize in time");
        } catch (IllegalStateException alreadyInitialized) {
            // already initialized -> ok
        }
    }

    @Test
    void isEmployeeInputValid_equivalenceClasses() {
        EmployeeManagementController controller = new EmployeeManagementController();

        ObservableList<String> validPermissions =
                FXCollections.observableArrayList("PERMISSION_1");

        ObservableList<String> emptyPermissions =
                FXCollections.observableArrayList();

        // EC1: VALID input
        assertTrue(controller.isEmployeeInputValid(
                "Moel Plasa",
                "moel",
                "password123",
                "moel@example.com",
                "123456789",
                1200.0,
                validPermissions
        ));

        // EC2: INVALID – empty full name
        assertFalse(controller.isEmployeeInputValid(
                "",
                "moel",
                "password123",
                "moel@example.com",
                "123456789",
                1200.0,
                validPermissions
        ));

        // EC3: INVALID – salary <= 0
        assertFalse(controller.isEmployeeInputValid(
                "Moel Plasa",
                "moel",
                "password123",
                "moel@example.com",
                "123456789",
                0.0,
                validPermissions
        ));

        // EC4: INVALID – empty permissions
        assertFalse(controller.isEmployeeInputValid(
                "Moel Plasa",
                "moel",
                "password123",
                "moel@example.com",
                "123456789",
                1200.0,
                emptyPermissions
        ));
    }
}
