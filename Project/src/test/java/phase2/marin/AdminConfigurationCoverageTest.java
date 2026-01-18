package phase2.marin;

import Controllers.EmployeeManagementController;
import Models.Access;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdminConfigurationCoverageTest {

    private EmployeeManagementController controller;

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
        controller = new EmployeeManagementController();
    }

    @Test
    void isValidAdminConfiguration_nonAdmin_returnsTrue() {
        ObservableList<String> selectedPerms = FXCollections.observableArrayList("PERM1");
        ObservableList<String> selectedSectors = FXCollections.observableArrayList("SECTOR1");
        ObservableList<String> allPerms = FXCollections.observableArrayList("PERM1", "PERM2");
        ObservableList<String> allSectors = FXCollections.observableArrayList("SECTOR1", "SECTOR2");

        assertTrue(controller.isValidAdminConfiguration(
                Access.Manager, selectedPerms, selectedSectors, allPerms, allSectors
        ));
        assertTrue(controller.isValidAdminConfiguration(
                Access.Cashier, selectedPerms, selectedSectors, allPerms, allSectors
        ));
    }

    @Test
    void isValidAdminConfiguration_adminAllSelected_returnsTrue() {
        ObservableList<String> allPerms = FXCollections.observableArrayList("PERM1", "PERM2");
        ObservableList<String> allSectors = FXCollections.observableArrayList("SECTOR1", "SECTOR2");
        ObservableList<String> selectedPerms = FXCollections.observableArrayList("PERM1", "PERM2");
        ObservableList<String> selectedSectors = FXCollections.observableArrayList("SECTOR1", "SECTOR2");

        assertTrue(controller.isValidAdminConfiguration(
                Access.Administrator, selectedPerms, selectedSectors, allPerms, allSectors
        ));
    }

    @Test
    void isValidAdminConfiguration_adminMissingSectors_returnsFalse() {
        ObservableList<String> allPerms = FXCollections.observableArrayList("PERM1", "PERM2");
        ObservableList<String> allSectors = FXCollections.observableArrayList("SECTOR1", "SECTOR2", "SECTOR3");
        ObservableList<String> selectedPerms = FXCollections.observableArrayList("PERM1", "PERM2");
        ObservableList<String> selectedSectors = FXCollections.observableArrayList("SECTOR1", "SECTOR2");

        assertFalse(controller.isValidAdminConfiguration(
                Access.Administrator, selectedPerms, selectedSectors, allPerms, allSectors
        ));
    }

    @Test
    void isValidAdminConfiguration_adminMissingPermissions_returnsFalse() {
        ObservableList<String> allPerms = FXCollections.observableArrayList("PERM1", "PERM2", "PERM3");
        ObservableList<String> allSectors = FXCollections.observableArrayList("SECTOR1", "SECTOR2");
        ObservableList<String> selectedPerms = FXCollections.observableArrayList("PERM1", "PERM2");
        ObservableList<String> selectedSectors = FXCollections.observableArrayList("SECTOR1", "SECTOR2");

        assertFalse(controller.isValidAdminConfiguration(
                Access.Administrator, selectedPerms, selectedSectors, allPerms, allSectors
        ));
    }

    @Test
    void isValidAdminConfiguration_adminMissingBoth_returnsFalse() {
        ObservableList<String> allPerms = FXCollections.observableArrayList("PERM1", "PERM2", "PERM3");
        ObservableList<String> allSectors = FXCollections.observableArrayList("SECTOR1", "SECTOR2", "SECTOR3");
        ObservableList<String> selectedPerms = FXCollections.observableArrayList("PERM1");
        ObservableList<String> selectedSectors = FXCollections.observableArrayList("SECTOR1");

        assertFalse(controller.isValidAdminConfiguration(
                Access.Administrator, selectedPerms, selectedSectors, allPerms, allSectors
        ));
    }
}
