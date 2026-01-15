package Controllers;

import DAO.UserFileHandler;
import Exceptions.InvalidCredentialsException;
import Interfaces.DAO.IUserFileHandler;
import Interfaces.Views.IEmployeesListView;
import Models.Access;
import Models.User;
import Views.EmployeesListView;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

public class EmployeeManagementController {

    // ✅ keep field names unchanged
    private final IEmployeesListView employeesListView;
    private final IUserFileHandler employeeFileHandler;

    private static final String SUCCESS = "Success";
    private static final String ERROR = "Error";

    // Production constructor
    public EmployeeManagementController() {
        this(new EmployeesListView(), new UserFileHandler());
    }

    // Test constructor (inject mocks)
    public EmployeeManagementController(
            IEmployeesListView employeesListView,
            IUserFileHandler employeeFileHandler
    ) {
        this.employeesListView = employeesListView;
        this.employeeFileHandler = employeeFileHandler;

        initialize();
    }

    // Keep method name; return interface for testability
    public IEmployeesListView getEmpListView() {
        return employeesListView;
    }

    public IUserFileHandler getAdminDAO() {
        return employeeFileHandler;
    }

    private void initialize() {
        employeesListView.setEmployeesTableItems(employeeFileHandler.getAllUsers());

        employeesListView.onAddEmployee(this::onEmployeeAdd);
        employeesListView.onUpdateEmployeeList(() ->
                employeesListView.setEmployeesTableItems(employeeFileHandler.getAllUsers())
        );
        employeesListView.onEditEmployee(this::onEditEmployee);
        employeesListView.onDeleteEmployee(this::onDeleteEmployee);

        employeesListView.onCancelUpdate(() -> {
            employeesListView.showCreateEmployeeBox();
        });

        employeesListView.onAccessLevelChanged(() -> {
            Access selected = employeesListView.getAccessLevelSelection();

            if (selected == Access.Cashier) {
                employeesListView.setSelectionModeCashier();
            }

            if (selected == Access.Manager) {
                employeesListView.setSelectionModeManager();
            }

            if (selected == Access.Administrator) {
                employeesListView.setSelectionModeAdministrator();
                employeesListView.selectAllPermissions();
                employeesListView.selectAllSectors();
            }
        });
    }

    private void onEmployeeAdd() {

        String fullName = employeesListView.getEmployeeFullName();
        String username = employeesListView.getEmployeeUsername();
        String password = employeesListView.getEmployeePassword();
        String email = employeesListView.getEmployeeEmail();
        String phone = employeesListView.getEmployeePhoneNumber();
        LocalDate dob = employeesListView.getEmployeeDOB();
        Access access = employeesListView.getEmployeeAccessLevel();

        ObservableList<String> permissions = employeesListView.getSelectedPermissions();
        ObservableList<String> sectors = employeesListView.getSelectedSectors();

        if (dob == null) {
            dob = LocalDate.now();
        }

        double salary = parseSalary(employeesListView.getEmployeeSalaryText());

        if (!isEmployeeInputValid(fullName, username, password, email, phone, salary, permissions)) {
            employeesListView.showError(ERROR, "Invalid Input");
            return;
        }

        if (!isValidAdminConfiguration(
                access,
                permissions,
                sectors,
                employeesListView.getAllPermissions(),
                employeesListView.getAllSectors())) {

            employeesListView.showError(ERROR, "Admin must have all permissions and sectors selected!");
            return;
        }

        employeeFileHandler.insertUser(
                new User(username, password, fullName, dob, phone,
                        email, salary, access, permissions, sectors)
        );

        employeesListView.setEmployeesTableItems(employeeFileHandler.getAllUsers());
        employeesListView.clearEmployeeInputs();
        employeesListView.showInfo(SUCCESS, "Employee Registered Successfully");
    }

    public void onEditEmployee() {
        User selectedUser = employeesListView.getSelectedEmployee();

        if (selectedUser == null) {
            employeesListView.showError(ERROR, "No employee selected");
            return;
        }

        employeesListView.showEditEmployeeBox();

        employeesListView.setEditFullName(selectedUser.getFullName());
        employeesListView.setEditUsername(selectedUser.getUsername());
        employeesListView.setEditEmail(selectedUser.getEmail());
        employeesListView.setEditPassword(selectedUser.getPassword());
        employeesListView.setEditPhoneNumber(selectedUser.getPhoneNumber());
        employeesListView.setEditDOB(selectedUser.getDateOfBirth());
        employeesListView.setEditSalaryText(String.valueOf(selectedUser.getSalary()));
        employeesListView.setEditAccessLevel(selectedUser.getAccessLevel());

        employeesListView.onUpdateEmployee(() -> {
            try {
                onUpdateEmployee();
            } catch (InvalidCredentialsException credentialsError) {
                employeesListView.showError(ERROR, credentialsError.getMessage());
            } catch (NumberFormatException numberError) {
                employeesListView.showError(ERROR, "Salary must be a valid number.");
            }
        });
    }

    public void onUpdateEmployee() throws InvalidCredentialsException {

        LocalDate dobEdit = employeesListView.getEditDOB();
        String fnameEdit = employeesListView.getEditFullName();
        String emailEdit = employeesListView.getEditEmail();
        String phoneEdit = employeesListView.getEditPhoneNumber();
        String username = employeesListView.getEditUsername();
        String password = employeesListView.getEditPassword();
        double salary = Double.parseDouble(employeesListView.getEditSalaryText());

        Access accessLevel = employeesListView.getEditAccessLevel();
        ObservableList<String> permissions = employeesListView.getEditSelectedPermissions();
        List<String> sector = employeesListView.getEditSelectedSectors();

        boolean updated = employeeFileHandler.updateUser(
                username, password, fnameEdit, dobEdit, phoneEdit, emailEdit,
                salary, accessLevel, permissions, sector
        );

        if (updated) {
            employeesListView.showInfo(SUCCESS, "Profile Updated Successfully");
            employeesListView.showCreateEmployeeBox();
        } else {
            throw new InvalidCredentialsException("Invalid credentials. Please try again");
        }
    }

    public void onDeleteEmployee() {
        User selectedUser = employeesListView.getSelectedEmployee();

        if (selectedUser == null) {
            employeesListView.showError(ERROR, "No employee selected");
            return;
        }

        boolean deleted = employeeFileHandler.deleteUser(selectedUser);

        if (deleted) {
            employeesListView.showInfo(SUCCESS, "Employee Deleted Successfully");
            employeesListView.setEmployeesTableItems(employeeFileHandler.getAllUsers());
        } else {
            employeesListView.showError(ERROR, "Employee Not Found");
        }
    }

    private double parseSalary(String salaryText) {
        try {
            return Double.parseDouble(salaryText);
        } catch (NumberFormatException numberError) {
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
}
