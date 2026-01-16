package Interfaces.Views;

import Models.Access;
import Models.User;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.util.List;

public interface IEmployeesListView {

    // ✅ Root UI node (needed by controllers)
    HBox getEmployeesPage();

    // ==== Table ====
    void setEmployeesTableItems(ObservableList<User> users);
    User getSelectedEmployee();

    // ==== Register inputs ====
    String getEmployeeFullName();
    String getEmployeeUsername();
    String getEmployeePassword();
    String getEmployeeEmail();
    String getEmployeePhoneNumber();
    String getEmployeeSalaryText();
    LocalDate getEmployeeDOB();
    Access getEmployeeAccessLevel();

    ObservableList<String> getSelectedPermissions();
    ObservableList<String> getSelectedSectors();

    ObservableList<String> getAllPermissions();
    ObservableList<String> getAllSectors();

    // ==== Edit inputs ====
    void showEditEmployeeBox();
    void showCreateEmployeeBox();

    void setEditFullName(String value);
    void setEditUsername(String value);
    void setEditEmail(String value);
    void setEditPassword(String value);
    void setEditPhoneNumber(String value);
    void setEditDOB(LocalDate value);
    void setEditSalaryText(String value);
    void setEditAccessLevel(Access value);

    LocalDate getEditDOB();
    String getEditFullName();
    String getEditEmail();
    String getEditPhoneNumber();
    String getEditUsername();
    String getEditPassword();
    String getEditSalaryText();
    Access getEditAccessLevel();
    ObservableList<String> getEditSelectedPermissions();
    List<String> getEditSelectedSectors();

    // ==== UI actions (wiring) ====
    void onAddEmployee(Runnable action);
    void onUpdateEmployeeList(Runnable action);
    void onEditEmployee(Runnable action);
    void onDeleteEmployee(Runnable action);
    void onCancelUpdate(Runnable action);
    void onUpdateEmployee(Runnable action);

    // ==== Access-level selection behavior ====
    Access getAccessLevelSelection();
    void setSelectionModeCashier();
    void setSelectionModeManager();
    void setSelectionModeAdministrator();
    void selectAllPermissions();
    void selectAllSectors();
    void onAccessLevelChanged(Runnable action);

    // ==== Messages ====
    void showError(String title, String message);
    void showInfo(String title, String message);

    // ==== Helpers ====
    void clearEmployeeInputs();
}
