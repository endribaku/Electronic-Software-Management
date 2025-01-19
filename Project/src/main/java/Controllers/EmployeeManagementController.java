package Controllers;

import DAO.UserFileHandler;
import Models.*;
import Views.EmployeesListView;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.EnumSet;

public class EmployeeManagementController {
    private final EmployeesListView employeesListView = new EmployeesListView();
    private final UserFileHandler employeeFileHandler = new UserFileHandler();
    private User currentUser;

    public EmployeeManagementController() {

        employeesListView.getEmployeesTableView().setItems(employeeFileHandler.getAllUsers());
        //this.employeesListView.getEmployeesTableView().
        this.employeesListView.getAddEmployeeButton().setOnAction(e -> onEmployeeAdd());
        this.employeesListView.getUpdateEmployeeListButton().setOnAction(e -> employeesListView.getEmployeesTableView().setItems(employeeFileHandler.getAllUsers()));
        setEditListeners();
    }

    // Controller setting the currentUser as the one who controls
    public EmployeeManagementController(User currentUser) {
        this.currentUser = currentUser;
        employeesListView.getEmployeesTableView().setItems(employeeFileHandler.getAllUsers());
        //this.employeesListView.getEmployeesTableView().
        this.employeesListView.getAddEmployeeButton().setOnAction(e -> onEmployeeAdd());
        this.employeesListView.getUpdateEmployeeListButton().setOnAction(e -> employeesListView.getEmployeesTableView().setItems(employeeFileHandler.getAllUsers()));
        setEditListeners();
    }

    public EmployeesListView getEmpListView() {
        return employeesListView;
    }

    public UserFileHandler getAdminDAO() {
        return employeeFileHandler;
    }

    private void setEditListeners() {
        this.employeesListView.getEmployeeIDColumn().setOnEditCommit(e -> {
            employeeFileHandler.getAllUsers().get(e.getTablePosition().getRow()).setUserID(e.getNewValue());
        });
        this.employeesListView.getEmployeeFullNameColumn().setOnEditCommit(e -> {
            employeeFileHandler.getAllUsers().get(e.getTablePosition().getRow()).setFullName(e.getNewValue());
        });
        this.employeesListView.getEmployeeUsernameColumn().setOnEditCommit(e -> {
            employeeFileHandler.getAllUsers().get(e.getTablePosition().getRow()).setUsername(e.getNewValue());
        });
        this.employeesListView.getEmployeePasswordColumn().setOnEditCommit(e -> {
            employeeFileHandler.getAllUsers().get(e.getTablePosition().getRow()).setPassword(e.getNewValue());
        });
        this.employeesListView.getEmployeeEmailColumn().setOnEditCommit(e -> {
            employeeFileHandler.getAllUsers().get(e.getTablePosition().getRow()).setEmail(e.getNewValue());
        });
        this.employeesListView.getEmployeeDOBColumn().setOnEditCommit(e -> {
            employeeFileHandler.getAllUsers().get(e.getTablePosition().getRow()).setDateOfBirth(e.getNewValue());
        });
        this.employeesListView.getEmployeePhoneNumberColumn().setOnEditCommit(e -> {
            employeeFileHandler.getAllUsers().get(e.getTablePosition().getRow()).setPhoneNumber(e.getNewValue());
        });
        this.employeesListView.getEmployeeAccessLevelColumn().setOnEditCommit(e -> {
            employeeFileHandler.getAllUsers().get(e.getTablePosition().getRow()).setAccessLevel(e.getNewValue());
        });
        this.employeesListView.getEmployeeSalaryColumn().setOnEditCommit(e -> {
            employeeFileHandler.getAllUsers().get(e.getTablePosition().getRow()).setSalary(e.getNewValue());
        });


        this.employeesListView.getUpdateEmployeeListButton().setOnAction(e -> {
            if(this.employeeFileHandler.updateAll()) {
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Success");
                success.setHeaderText("Employee Table Updated Successfully");
                success.show();
            } else {
                Alert fail = new Alert(Alert.AlertType.ERROR);
                fail.setTitle("Success");
                fail.setHeaderText("Employee Table Update Error");
                fail.show();
            }
        });
    }

    private void onEmployeeAdd() {
        String employeeFullName = employeesListView.getEmployeeFullNameField().getText();
        String employeeUsername = employeesListView.getEmployeeUsernameField().getText();
        String employeePassword = employeesListView.getEmployeePasswordField().getText();
        LocalDate employeeDOB = employeesListView.getEmployeeDOBField().getValue();
        String employeeEmail = employeesListView.getEmployeeEmailField().getText();
        String employeePhoneNumber = employeesListView.getEmployeePhoneNumberField().getText();
        Access employeeAccessLevel = employeesListView.getAccessLevelList().getValue();
        ObservableList<String> employeePermissionsSelected = employeesListView.getPermissionListView().getSelectionModel().getSelectedItems();
        ObservableList<String> employeeSectorsSelected = employeesListView.getSectorListView().getSelectionModel().getSelectedItems();

        if(employeeDOB == null)
            employeeDOB = LocalDate.now();
        double employeeSalary;
        try {
            employeeSalary = Double.parseDouble(employeesListView.getEmployeeSalaryField().getText());
        } catch (NumberFormatException e) {
            employeeSalary = 0;
        }

        if(employeeAccessLevel == null)
            employeeAccessLevel = Access.Cashier;
        if (employeeFullName.isEmpty() || employeeUsername.isEmpty() || employeePassword.isEmpty()
                || employeeEmail.isEmpty() || employeePhoneNumber.isEmpty() || employeeSalary == 0 || employeePermissionsSelected.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Input");
                alert.show();
        } else {
            employeeFileHandler.insertUser(new User(employeeUsername, employeePassword, employeeFullName, employeeDOB, employeePhoneNumber, employeeEmail, employeeSalary, employeeAccessLevel, employeePermissionsSelected, employeeSectorsSelected));
        }
            employeesListView.getEmployeesTableView().setItems(employeeFileHandler.getAllUsers());

            employeesListView.getEmployeeFullNameField().clear();
            employeesListView.getEmployeeUsernameField().clear();
            employeesListView.getEmployeePasswordField().clear();
            employeesListView.getEmployeeDOBField().setValue(null);
            employeesListView.getEmployeeEmailField().clear();
            employeesListView.getEmployeePhoneNumberField().clear();
            employeesListView.getEmployeeSalaryField().clear();
            employeesListView.getAccessLevelList().setValue(null);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Employee Registered Successfully");
            alert.show();

//        } catch(FileNotFoundException fnfe) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("File not found");
//            alert.setHeaderText("Input File not found");
//            alert.show();
//        } catch(IOException ioe) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText("IOException problem");
//            alert.show();
//        } catch(ClassNotFoundException cnfe) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText("Class Not Found file problem");
//            alert.show();
//        }
    }

}
