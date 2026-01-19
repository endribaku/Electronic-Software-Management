package stubs;

import Interfaces.Views.IEmployeesListView;
import Models.Access;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.util.List;

public class StubEmployeesListView implements IEmployeesListView {
    public HBox employeesPage = new HBox();
    public User selectedEmployee;

    public String fullName = "";
    public String username = "";
    public String password = "";
    public String email = "";
    public String phone = "";
    public String salaryText = "";
    public LocalDate dob;
    public Access accessLevel = Access.Cashier;
    public ObservableList<String> selectedPermissions = FXCollections.observableArrayList();
    public ObservableList<String> selectedSectors = FXCollections.observableArrayList();
    public ObservableList<String> allPermissions = FXCollections.observableArrayList();
    public ObservableList<String> allSectors = FXCollections.observableArrayList();

    public String editFullName = "";
    public String editUsername = "";
    public String editEmail = "";
    public String editPassword = "";
    public String editPhone = "";
    public String editSalaryText = "";
    public LocalDate editDob;
    public Access editAccessLevel = Access.Cashier;
    public ObservableList<String> editSelectedPermissions = FXCollections.observableArrayList();
    public List<String> editSelectedSectors = List.of();

    public Runnable addEmployeeAction;
    public Runnable deleteEmployeeAction;
    public Runnable editEmployeeAction;
    public Runnable updateEmployeeAction;

    public void fireAddEmployee() {
        if (addEmployeeAction != null) {
            addEmployeeAction.run();
        }
    }

    public void fireDeleteEmployee() {
        if (deleteEmployeeAction != null) {
            deleteEmployeeAction.run();
        }
    }

    public void fireEditEmployee() {
        if (editEmployeeAction != null) {
            editEmployeeAction.run();
        }
    }

    public void fireUpdateEmployee() {
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
