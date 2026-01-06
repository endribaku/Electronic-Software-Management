package Controllers;

import DAO.UserFileHandler;
import Exceptions.EmployeeCreationException;
import Exceptions.InvalidCredentialsException;
import Models.*;
import Views.EmployeesListView;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;

import java.time.LocalDate;
import java.util.List;

public class EmployeeManagementController {
    private final EmployeesListView employeesListView = new EmployeesListView();
    private final UserFileHandler employeeFileHandler = new UserFileHandler();
    private static final String SUCCESS = "Success";
    private static final String ERROR = "Error";

    // Controller setting the currentUser as the one who controls
    public EmployeeManagementController() {
        employeesListView.getEmployeesTableView().setItems(employeeFileHandler.getAllUsers());
        this.employeesListView.getAddEmployeeButton().setOnAction(e -> onEmployeeAdd());
        this.employeesListView.getUpdateEmployeeListButton().setOnAction(e -> employeesListView.getEmployeesTableView().setItems(employeeFileHandler.getAllUsers()));
        this.employeesListView.getEditEmployeeButton().setOnAction(e -> onEditEmployee());
        this.employeesListView.getDeleteEmployeeButton().setOnAction(e -> onDeleteEmployee());
        this.employeesListView.getCancelUpdateButton().setOnAction(e -> {
            this.employeesListView.getManageEmployeeBox().getChildren().remove(this.employeesListView.getEditEmployeeBox());
            this.employeesListView.getManageEmployeeBox().getChildren().add(this.employeesListView.getCreateEmployeeBox());
        });
        this.employeesListView.getAccessLevelList().setOnAction(e -> {
            if(this.employeesListView.getAccessLevelList().getValue().equals(Access.Cashier)) {
                this.employeesListView.getPermissionListView().getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
                this.employeesListView.getSectorListView().getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            }
            if(this.employeesListView.getAccessLevelList().getValue().equals(Access.Manager)) {
                this.employeesListView.getPermissionListView().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                this.employeesListView.getSectorListView().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            }
            if(this.employeesListView.getAccessLevelList().getValue().equals(Access.Administrator)) {
                this.employeesListView.getPermissionListView().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                this.employeesListView.getSectorListView().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                this.employeesListView.getPermissionListView().getSelectionModel().selectAll();
                this.employeesListView.getSectorListView().getSelectionModel().selectAll();
            }
        });
        setEditRows();
    }

    public EmployeesListView getEmpListView() {
        return employeesListView;
    }

    public UserFileHandler getAdminDAO() {
        return employeeFileHandler;
    }

    private void setEditRows() throws EmployeeCreationException {
        this.employeesListView.getEmployeeIDColumn().setOnEditCommit(e -> {
            employeeFileHandler.getAllUsers().get(e.getTablePosition().getRow()).setUserID(e.getNewValue());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ERROR);
            alert.setHeaderText("Can't edit ID");
        });
        this.employeesListView.getEmployeeFullNameColumn().setOnEditCommit(e -> {
            employeeFileHandler.getAllUsers().get(e.getTablePosition().getRow()).setFullName(e.getNewValue());
            int row = e.getTablePosition().getRow();

            User updatedUser = employeeFileHandler.getAllUsers().get(row);
            updatedUser.setFullName(e.getNewValue());

            employeeFileHandler.updateAll(employeeFileHandler.getUsers());
        });
        this.employeesListView.getEmployeeUsernameColumn().setOnEditCommit(e -> {
            employeeFileHandler.getAllUsers().get(e.getTablePosition().getRow()).setUsername(e.getNewValue());
            int row = e.getTablePosition().getRow();

            User updatedUser = employeeFileHandler.getAllUsers().get(row);
            updatedUser.setUsername(e.getNewValue());

            employeeFileHandler.updateAll(employeeFileHandler.getUsers());
        });
        this.employeesListView.getEmployeePasswordColumn().setOnEditCommit(e -> {
            employeeFileHandler.getAllUsers().get(e.getTablePosition().getRow()).setPassword(e.getNewValue());
            int row = e.getTablePosition().getRow();

            User updatedUser = employeeFileHandler.getAllUsers().get(row);
            updatedUser.setPassword(e.getNewValue());

            employeeFileHandler.updateAll(employeeFileHandler.getUsers());
        });
        this.employeesListView.getEmployeeEmailColumn().setOnEditCommit(e -> {
            employeeFileHandler.getAllUsers().get(e.getTablePosition().getRow()).setEmail(e.getNewValue());
            int row = e.getTablePosition().getRow();

            User updatedUser = employeeFileHandler.getAllUsers().get(row);
            updatedUser.setEmail(e.getNewValue());

            employeeFileHandler.updateAll(employeeFileHandler.getUsers());
        });
        this.employeesListView.getEmployeeDOBColumn().setOnEditCommit(e -> {
            employeeFileHandler.getAllUsers().get(e.getTablePosition().getRow()).setDateOfBirth(e.getNewValue());
            int row = e.getTablePosition().getRow();

            User updatedUser = employeeFileHandler.getAllUsers().get(row);
            updatedUser.setDateOfBirth(e.getNewValue());

            employeeFileHandler.updateAll(employeeFileHandler.getUsers());
        });
        this.employeesListView.getEmployeePhoneNumberColumn().setOnEditCommit(e -> {
            employeeFileHandler.getAllUsers().get(e.getTablePosition().getRow()).setPhoneNumber(e.getNewValue());
            int row = e.getTablePosition().getRow();

            User updatedUser = employeeFileHandler.getAllUsers().get(row);
            updatedUser.setPhoneNumber(e.getNewValue());

            employeeFileHandler.updateAll(employeeFileHandler.getUsers());
        });
        this.employeesListView.getEmployeeAccessLevelColumn().setOnEditCommit(e -> {
            employeeFileHandler.getAllUsers().get(e.getTablePosition().getRow()).setAccessLevel(e.getNewValue());
            int row = e.getTablePosition().getRow();

            User updatedUser = employeeFileHandler.getAllUsers().get(row);
            updatedUser.setAccessLevel(e.getNewValue());

            employeeFileHandler.updateAll(employeeFileHandler.getUsers());
        });
        this.employeesListView.getEmployeeSalaryColumn().setOnEditCommit(e -> {
            employeeFileHandler.getAllUsers().get(e.getTablePosition().getRow()).setSalary(e.getNewValue());
            int row = e.getTablePosition().getRow();

            User updatedUser = employeeFileHandler.getAllUsers().get(row);
            updatedUser.setSalary(e.getNewValue());

            employeeFileHandler.updateAll(employeeFileHandler.getUsers());
        });
    }

    private void onEmployeeAdd() {

        String fullName = employeesListView.getEmployeeFullNameField().getText();
        String username = employeesListView.getEmployeeUsernameField().getText();
        String password = employeesListView.getEmployeePasswordField().getText();
        String email = employeesListView.getEmployeeEmailField().getText();
        String phone = employeesListView.getEmployeePhoneNumberField().getText();
        LocalDate dob = employeesListView.getEmployeeDOBField().getValue();
        Access access = employeesListView.getAccessLevelList().getValue();

        ObservableList<String> permissions =
                employeesListView.getPermissionListView()
                        .getSelectionModel()
                        .getSelectedItems();

        ObservableList<String> sectors =
                employeesListView.getSectorListView()
                        .getSelectionModel()
                        .getSelectedItems();

        if (dob == null) {
            dob = LocalDate.now();
        }

        double salary =
                parseSalary(employeesListView.getEmployeeSalaryField().getText());

        if (!isEmployeeInputValid(
                fullName, username, password, email, phone, salary, permissions)) {

            showError("Invalid Input");
            return;
        }

        if (!isValidAdminConfiguration(
                access,
                permissions,
                sectors,
                employeesListView.getPermissionListView().getItems(),
                employeesListView.getSectorListView().getItems())) {

            showError("Admin must have all permissions and sectors selected!");
            return;
        }

        employeeFileHandler.insertUser(
                new User(username, password, fullName, dob, phone,
                        email, salary, access, permissions, sectors)
        );

        employeesListView.getEmployeesTableView()
                .setItems(employeeFileHandler.getAllUsers());

        clearEmployeeInputs();
        showSuccess("Employee Registered Successfully");
    }


    public void onEditEmployee() {
        this.employeesListView.getManageEmployeeBox().getChildren().remove(this.employeesListView.getCreateEmployeeBox());
        this.employeesListView.getManageEmployeeBox().getChildren().add(this.employeesListView.getEditEmployeeBox());

        User selectedUser = this.employeesListView.getEmployeesTableView().getSelectionModel().getSelectedItem();
        this.employeesListView.getEmployeeEditFullNameField().setText(selectedUser.getFullName());
        this.employeesListView.getEmployeeEditUsernameField().setText(selectedUser.getUsername());
        this.employeesListView.getEmployeeEditEmailField().setText(selectedUser.getEmail());
        this.employeesListView.getEmployeeEditPasswordField().setText(selectedUser.getPassword());
        this.employeesListView.getEmployeeEditPhoneNumberField().setText(selectedUser.getPhoneNumber());
        this.employeesListView.getEmployeeEditDOBField().setValue(selectedUser.getDateOfBirth());
        this.employeesListView.getEmployeeEditSalaryField().setText(selectedUser.getSalary() + "");
        this.employeesListView.getSectorEditListView().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        for(int i = 0; i<selectedUser.getSector().size(); i++) {
            this.employeesListView.getSectorEditListView().getSelectionModel().select(selectedUser.getSector().get(i));
        }
        this.employeesListView.getPermissionEditListView().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.employeesListView.getPermissionEditListView().getSelectionModel().select(selectedUser.getPermissions().toString());
        this.employeesListView.getAccessLevelEditList().getSelectionModel().select(selectedUser.getAccessLevel());
        this.employeesListView.getUpdateEmployeeButton().setOnAction(e -> onUpdateEmployee());
    }

    public void onUpdateEmployee() throws InvalidCredentialsException {
        LocalDate dobEdit = this.employeesListView.getEmployeeEditDOBField().getValue();
        String fnameEdit = this.employeesListView.getEmployeeEditFullNameField().getText();
        String emailEdit = this.employeesListView.getEmployeeEditEmailField().getText();
        String phoneEdit = this.employeesListView.getEmployeeEditPhoneNumberField().getText();
        String username = this.employeesListView.getEmployeeEditUsernameField().getText();
        String password = this.employeesListView.getEmployeeEditPasswordField().getText();
        double salary = Double.parseDouble(this.employeesListView.getEmployeeEditSalaryField().getText());
        Access accessLevel = this.employeesListView.getAccessLevelEditList().getSelectionModel().getSelectedItem();
        ObservableList<String> permissions = this.employeesListView.getPermissionEditListView().getSelectionModel().getSelectedItems();
        List<String> sector = this.employeesListView.getSectorEditListView().getSelectionModel().getSelectedItems();

        if(this.employeeFileHandler.updateUser(username, password, fnameEdit, dobEdit, phoneEdit, emailEdit, salary, accessLevel, permissions, sector)) {
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle(SUCCESS);
            success.setHeaderText("Profile Updated Successfully");
            success.show();

            this.employeesListView.getManageEmployeeBox().getChildren().remove(this.employeesListView.getEditEmployeeBox());
            this.employeesListView.getManageEmployeeBox().getChildren().add(this.employeesListView.getCreateEmployeeBox());
        }
        else
            throw new InvalidCredentialsException("Invalid credentials. Please try again");

    }

    public void onDeleteEmployee() {
        User selectedUser = this.employeesListView.getEmployeesTableView().getSelectionModel().getSelectedItem();

        if(employeeFileHandler.deleteUser(selectedUser))
        {
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle(SUCCESS);
            success.setHeaderText("Employee Deleted Successfully");
            success.show();
        } else {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle(ERROR);

            error.setHeaderText("Employee Not Found");
            error.show();
        }
    }

    private double parseSalary(String salaryText) {
        try {
            return Double.parseDouble(salaryText);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public boolean isEmployeeInputValid(
            String fullName,
            String username,
            String password,
            String email,
            String phone,
            double salary,
            ObservableList<String> permissions
    ) {
        return !(fullName.isEmpty()
                || username.isEmpty()
                || password.isEmpty()
                || email.isEmpty()
                || phone.isEmpty()
                || salary <= 0
                || permissions.isEmpty());
    }

    public boolean isValidAdminConfiguration(
            Access access,
            ObservableList<String> selectedPermissions,
            ObservableList<String> selectedSectors,
            ObservableList<String> allPermissions,
            ObservableList<String> allSectors
    ) {
        if (access != Access.Administrator) {
            return true;
        }

        return selectedPermissions.size() == allPermissions.size()
                && selectedSectors.size() == allSectors.size();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR);
        alert.setHeaderText(message);
        alert.show();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(SUCCESS);
        alert.setHeaderText(message);
        alert.show();
    }

    private void clearEmployeeInputs() {
        employeesListView.getEmployeeFullNameField().clear();
        employeesListView.getEmployeeUsernameField().clear();
        employeesListView.getEmployeePasswordField().clear();
        employeesListView.getEmployeeDOBField().setValue(null);
        employeesListView.getEmployeeEmailField().clear();
        employeesListView.getEmployeePhoneNumberField().clear();
        employeesListView.getEmployeeSalaryField().clear();
        employeesListView.getAccessLevelList().setValue(Access.Cashier);
    }


}
