package phase3.integration.marin;

import Controllers.EmployeeManagementController;
import DAO.UserFileHandler;
import Interfaces.Views.IEmployeesListView;
import Models.Access;
import Models.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
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

    private static class StubEmployeesListView implements IEmployeesListView {
        HBox employeesPage = new HBox();
        User selectedEmployee;

        String fullName = "";
        String username = "";
        String password = "";
        String email = "";
        String phone = "";
        String salaryText = "";
        LocalDate dob;
        Access accessLevel = Access.Cashier;
        ObservableList<String> selectedPermissions = FXCollections.observableArrayList();
        ObservableList<String> selectedSectors = FXCollections.observableArrayList();
        ObservableList<String> allPermissions = FXCollections.observableArrayList();
        ObservableList<String> allSectors = FXCollections.observableArrayList();

        String editFullName = "";
        String editUsername = "";
        String editEmail = "";
        String editPassword = "";
        String editPhone = "";
        String editSalaryText = "";
        LocalDate editDob;
        Access editAccessLevel = Access.Cashier;
        ObservableList<String> editSelectedPermissions = FXCollections.observableArrayList();
        List<String> editSelectedSectors = List.of();

        Runnable addEmployeeAction;
        Runnable deleteEmployeeAction;
        Runnable editEmployeeAction;
        Runnable updateEmployeeAction;

        void fireAddEmployee() {
            if (addEmployeeAction != null) {
                addEmployeeAction.run();
            }
        }

        void fireDeleteEmployee() {
            if (deleteEmployeeAction != null) {
                deleteEmployeeAction.run();
            }
        }

        void fireEditEmployee() {
            if (editEmployeeAction != null) {
                editEmployeeAction.run();
            }
        }

        void fireUpdateEmployee() {
            if (updateEmployeeAction != null) {
                updateEmployeeAction.run();
            }
        }

        @Override
        public HBox getEmployeesPage() {
            return employeesPage;
        }

        @Override
        public void setEmployeesTableItems(ObservableList<User> users) {
            // no-op for stub
        }

        @Override
        public User getSelectedEmployee() {
            return selectedEmployee;
        }

        @Override
        public String getEmployeeFullName() {
            return fullName;
        }

        @Override
        public String getEmployeeUsername() {
            return username;
        }

        @Override
        public String getEmployeePassword() {
            return password;
        }

        @Override
        public String getEmployeeEmail() {
            return email;
        }

        @Override
        public String getEmployeePhoneNumber() {
            return phone;
        }

        @Override
        public String getEmployeeSalaryText() {
            return salaryText;
        }

        @Override
        public LocalDate getEmployeeDOB() {
            return dob;
        }

        @Override
        public Access getEmployeeAccessLevel() {
            return accessLevel;
        }

        @Override
        public ObservableList<String> getSelectedPermissions() {
            return selectedPermissions;
        }

        @Override
        public ObservableList<String> getSelectedSectors() {
            return selectedSectors;
        }

        @Override
        public ObservableList<String> getAllPermissions() {
            return allPermissions;
        }

        @Override
        public ObservableList<String> getAllSectors() {
            return allSectors;
        }

        @Override
        public void showEditEmployeeBox() {
            // no-op for stub
        }

        @Override
        public void showCreateEmployeeBox() {
            // no-op for stub
        }

        @Override
        public void setEditFullName(String value) {
            editFullName = value;
        }

        @Override
        public void setEditUsername(String value) {
            editUsername = value;
        }

        @Override
        public void setEditEmail(String value) {
            editEmail = value;
        }

        @Override
        public void setEditPassword(String value) {
            editPassword = value;
        }

        @Override
        public void setEditPhoneNumber(String value) {
            editPhone = value;
        }

        @Override
        public void setEditDOB(LocalDate value) {
            editDob = value;
        }

        @Override
        public void setEditSalaryText(String value) {
            editSalaryText = value;
        }

        @Override
        public void setEditAccessLevel(Access value) {
            editAccessLevel = value;
        }

        @Override
        public LocalDate getEditDOB() {
            return editDob;
        }

        @Override
        public String getEditFullName() {
            return editFullName;
        }

        @Override
        public String getEditEmail() {
            return editEmail;
        }

        @Override
        public String getEditPhoneNumber() {
            return editPhone;
        }

        @Override
        public String getEditUsername() {
            return editUsername;
        }

        @Override
        public String getEditPassword() {
            return editPassword;
        }

        @Override
        public String getEditSalaryText() {
            return editSalaryText;
        }

        @Override
        public Access getEditAccessLevel() {
            return editAccessLevel;
        }

        @Override
        public ObservableList<String> getEditSelectedPermissions() {
            return editSelectedPermissions;
        }

        @Override
        public List<String> getEditSelectedSectors() {
            return editSelectedSectors;
        }

        @Override
        public void onAddEmployee(Runnable action) {
            addEmployeeAction = action;
        }

        @Override
        public void onUpdateEmployeeList(Runnable action) {
            // no-op for stub
        }

        @Override
        public void onEditEmployee(Runnable action) {
            editEmployeeAction = action;
        }

        @Override
        public void onDeleteEmployee(Runnable action) {
            deleteEmployeeAction = action;
        }

        @Override
        public void onCancelUpdate(Runnable action) {
            // no-op for stub
        }

        @Override
        public void onUpdateEmployee(Runnable action) {
            updateEmployeeAction = action;
        }

        @Override
        public Access getAccessLevelSelection() {
            return accessLevel;
        }

        @Override
        public void setSelectionModeCashier() {
            // no-op for stub
        }

        @Override
        public void setSelectionModeManager() {
            // no-op for stub
        }

        @Override
        public void setSelectionModeAdministrator() {
            // no-op for stub
        }

        @Override
        public void selectAllPermissions() {
            // no-op for stub
        }

        @Override
        public void selectAllSectors() {
            // no-op for stub
        }

        @Override
        public void onAccessLevelChanged(Runnable action) {
            // no-op for stub
        }

        @Override
        public void showError(String title, String message) {
            // no-op for stub
        }

        @Override
        public void showInfo(String title, String message) {
            // no-op for stub
        }

        @Override
        public void clearEmployeeInputs() {
            // no-op for stub
        }
    }
}
