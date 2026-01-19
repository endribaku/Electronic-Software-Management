package phase3.integration.marin;

import Controllers.EmployeeManagementController;
import stubs.CapturingEmployeesListView;
import stubs.FakeUserFileHandler;
import Models.Access;
import Models.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmployeeManagementControllerViewIT {

    @BeforeAll
    static void initializeJavaFX() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {
            // JavaFX already initialized
        }
    }

    @Test
    void createUserButton_callsInsertUser() throws Exception {
        FakeUserFileHandler handler = new FakeUserFileHandler();
        CapturingEmployeesListView view = new CapturingEmployeesListView();
        handler.setUsers(FXCollections.observableArrayList());

        new EmployeeManagementController(view, handler);

        runFxAndWait(() -> {
            view.getEmployeeFullNameField().setText("John Doe");
            view.getEmployeeUsernameField().setText("john");
            view.getEmployeePasswordField().setText("pass");
            view.getEmployeeEmailField().setText("john@example.com");
            view.getEmployeePhoneNumberField().setText("123");
            view.getEmployeeSalaryField().setText("1000");
            view.getAccessLevelList().getSelectionModel().select(Access.Cashier);
            view.getPermissionListView().getSelectionModel().select(0);
            view.getSectors().add("SectorA");
            view.getSectorListView().getSelectionModel().select("SectorA");
            view.getAddEmployeeButton().fire();
        });

        assertEquals(1, handler.insertUserCalls);
    }

    @Test
    void deleteUserButton_callsDeleteUser() throws Exception {
        FakeUserFileHandler handler = new FakeUserFileHandler();
        CapturingEmployeesListView view = new CapturingEmployeesListView();

        User existing = new User(
                "toDelete", "pass", "Delete User", LocalDate.now(),
                "123", "delete@example.com", 900.0, Access.Cashier,
                FXCollections.observableArrayList("INVENTORY_ACCESS"),
                FXCollections.observableArrayList("SectorA")
        );
        ObservableList<User> users = FXCollections.observableArrayList(existing);
        handler.setUsers(users);
        handler.deleteUserResult = true;

        new EmployeeManagementController(view, handler);

        runFxAndWait(() -> {
            view.getEmployeesTableView().getSelectionModel().select(existing);
            view.getDeleteEmployeeButton().fire();
        });

        assertEquals(1, handler.deleteUserCalls);
        assertEquals(existing, handler.lastDeletedUser);
    }

    @Test
    void invalidForm_showsErrorMessage() throws Exception {
        FakeUserFileHandler handler = new FakeUserFileHandler();
        CapturingEmployeesListView view = new CapturingEmployeesListView();
        handler.setUsers(FXCollections.observableArrayList());

        new EmployeeManagementController(view, handler);

        runFxAndWait(() -> {
            view.getEmployeeFullNameField().setText("");
            view.getEmployeeUsernameField().setText("");
            view.getEmployeePasswordField().setText("");
            view.getEmployeeEmailField().setText("");
            view.getEmployeePhoneNumberField().setText("");
            view.getEmployeeSalaryField().setText("0");
            view.getAccessLevelList().getSelectionModel().select(Access.Cashier);
            view.getAddEmployeeButton().fire();
        });

        assertEquals("Error", view.lastErrorTitle);
        assertEquals("Invalid Input", view.lastErrorMessage);
        assertEquals(0, handler.insertUserCalls);
    }

    private static void runFxAndWait(Runnable action) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } finally {
                latch.countDown();
            }
        });
        assertTrue(latch.await(5, TimeUnit.SECONDS), "JavaFX action timed out");
    }

}
