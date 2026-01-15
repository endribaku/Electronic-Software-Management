package phase2.moel;

import Controllers.EmployeeManagementController;
import Models.Access;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class AdminConfigurationCoverageTest {

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
    void isValidAdminConfiguration_MCDC_and_Coverage() {

        EmployeeManagementController controller =
                new EmployeeManagementController();

        ObservableList<String> allPermissions =
                FXCollections.observableArrayList("P1", "P2");

        ObservableList<String> allSectors =
                FXCollections.observableArrayList("S1", "S2");

        ObservableList<String> permsAll =
                FXCollections.observableArrayList("P1", "P2");

        ObservableList<String> permsMissing =
                FXCollections.observableArrayList("P1");

        ObservableList<String> sectorsAll =
                FXCollections.observableArrayList("S1", "S2");

        ObservableList<String> sectorsMissing =
                FXCollections.observableArrayList("S1");

        // Case 1: Non-admin -> always valid
        assertTrue(controller.isValidAdminConfiguration(
                Access.Cashier,
                permsMissing,
                sectorsMissing,
                allPermissions,
                allSectors
        ));

        // Case 2: Admin, all permissions & sectors selected (D2=true, D3=true)
        assertTrue(controller.isValidAdminConfiguration(
                Access.Administrator,
                permsAll,
                sectorsAll,
                allPermissions,
                allSectors
        ));

        // Case 3: Admin, permissions missing (D2=false, D3=true)
        assertFalse(controller.isValidAdminConfiguration(
                Access.Administrator,
                permsMissing,
                sectorsAll,
                allPermissions,
                allSectors
        ));

        // Case 4: Admin, sectors missing (D2=true, D3=false)
        assertFalse(controller.isValidAdminConfiguration(
                Access.Administrator,
                permsAll,
                sectorsMissing,
                allPermissions,
                allSectors
        ));
    }
}
