package phase3.integration.marin;

import Controllers.EmployeeManagementController;
import DAO.UserFileHandler;
import stubs.StubEmployeesListView;
import Models.Access;
import Models.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeManagementControllerHandlerIT {

    @BeforeAll
    static void initializeJavaFX() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {
            // JavaFX already initialized
        }
    }

    @Test
    void insertUser_valid_persistsUser(@TempDir Path tempDir) {
        File dataFile = tempDir.resolve("employees.dat").toFile();
        UserFileHandler handler = new UserFileHandler(dataFile);
        StubEmployeesListView view = new StubEmployeesListView();

        view.fullName = "John Doe";
        view.username = "jdoe";
        view.password = "pass123";
        view.email = "john@example.com";
        view.phone = "1234567890";
        view.salaryText = "1200";
        view.dob = LocalDate.of(1995, 5, 5);
        view.accessLevel = Access.Cashier;
        view.selectedPermissions = FXCollections.observableArrayList("INVENTORY_ACCESS");
        view.selectedSectors = FXCollections.observableArrayList("SectorA");
        view.allPermissions = FXCollections.observableArrayList("INVENTORY_ACCESS");
        view.allSectors = FXCollections.observableArrayList("SectorA");

        new EmployeeManagementController(view, handler);

        view.fireAddEmployee();

        assertEquals(1, handler.getAllUsers().size(), "User should be inserted");
        UserFileHandler reload = new UserFileHandler(dataFile);
        assertNotNull(reload.selectUser("jdoe"), "User should be persisted to file");
    }

    @Test
    void deleteUser_valid_removesUser(@TempDir Path tempDir) {
        File dataFile = tempDir.resolve("employees.dat").toFile();
        UserFileHandler handler = new UserFileHandler(dataFile);
        StubEmployeesListView view = new StubEmployeesListView();

        User user = new User(
                "deleteUser", "pass", "Delete User", LocalDate.now(),
                "123", "delete@example.com", 900.0, Access.Cashier,
                FXCollections.observableArrayList("INVENTORY_ACCESS"),
                FXCollections.observableArrayList("SectorA")
        );
        handler.insertUser(user);

        view.selectedEmployee = user;

        new EmployeeManagementController(view, handler);

        view.fireDeleteEmployee();

        assertTrue(handler.getAllUsers().isEmpty(), "User should be removed from handler list");
        UserFileHandler reload = new UserFileHandler(dataFile);
        assertNull(reload.selectUser("deleteUser"), "User should be removed from file");
    }

    @Test
    void updateUser_valid_updatesFieldsAndSaves(@TempDir Path tempDir) {
        File dataFile = tempDir.resolve("employees.dat").toFile();
        UserFileHandler handler = new UserFileHandler(dataFile);
        StubEmployeesListView view = new StubEmployeesListView();

        User user = new User(
                "updateUser", "pass", "Old Name", LocalDate.now(),
                "123", "old@example.com", 900.0, Access.Manager,
                FXCollections.observableArrayList("INVENTORY_ACCESS"),
                FXCollections.observableArrayList("SectorA")
        );
        handler.insertUser(user);

        view.selectedEmployee = user;

        new EmployeeManagementController(view, handler);

        view.fireEditEmployee();
        view.editFullName = "New Name";
        view.editUsername = "updateUser";
        view.editPassword = "pass";
        view.editEmail = "new@example.com";
        view.editPhone = "555";
        view.editSalaryText = "1500";
        view.editDob = LocalDate.of(1990, 1, 1);
        view.editAccessLevel = Access.Manager;
        view.editSelectedPermissions = FXCollections.observableArrayList("INVENTORY_ACCESS");
        view.editSelectedSectors = List.of("SectorA");
        view.fireUpdateEmployee();

        User updated = handler.selectUser("updateUser");
        assertNotNull(updated, "Updated user should exist");
        assertEquals("New Name", updated.getFullName(), "Full name should be updated");
        UserFileHandler reload = new UserFileHandler(dataFile);
        assertEquals("New Name", reload.selectUser("updateUser").getFullName(), "Update should persist to file");
    }

}
