package Controllers;

import DAO.UserFileHandler;
import Models.Access;
import Models.Administrator;
import Models.Cashier;
import Models.Manager;
import Views.AdminView;
import javafx.scene.control.Alert;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

public class AdminController {
    private Administrator model;
    private AdminView view;
    private AdminView.EmployeesListView empListView;
    private UserFileHandler adminDAO;

    public AdminController() {
        this.view = new AdminView();
        this.adminDAO = new UserFileHandler();
        this.empListView = view.new EmployeesListView();
        this.empListView.getAddEmployeeButton().setOnAction(e -> onEmployeeAdd());
        this.empListView.getEmployeesTableView().setItems(adminDAO.getAllUsers());
        setEditListeners();
    }

    public Administrator getModel() {
        return model;
    }

    public AdminView getView() {
        return view;
    }

    public AdminView.EmployeesListView getEmpListView() {
        return empListView;
    }

    public UserFileHandler getAdminDAO() {
        return adminDAO;
    }

    private void setEditListeners() {
        this.empListView.getEmployeeIDColumn().setOnEditCommit(e -> {
            adminDAO.getAllUsers().get(e.getTablePosition().getRow()).setUserID(e.getNewValue());
        });
        this.empListView.getEmployeeFullNameColumn().setOnEditCommit(e -> {
            adminDAO.getAllUsers().get(e.getTablePosition().getRow()).setFullName(e.getNewValue());
        });
        this.empListView.getEmployeeUsernameColumn().setOnEditCommit(e -> {
            adminDAO.getAllUsers().get(e.getTablePosition().getRow()).setUsername(e.getNewValue());
        });
        this.empListView.getEmployeePasswordColumn().setOnEditCommit(e -> {
            adminDAO.getAllUsers().get(e.getTablePosition().getRow()).setPassword(e.getNewValue());
        });
        this.empListView.getEmployeeEmailColumn().setOnEditCommit(e -> {
            adminDAO.getAllUsers().get(e.getTablePosition().getRow()).setEmail(e.getNewValue());
        });
        this.empListView.getEmployeeDOBColumn().setOnEditCommit(e -> {
            adminDAO.getAllUsers().get(e.getTablePosition().getRow()).setDateOfBirth(e.getNewValue());
        });
        this.empListView.getEmployeePhoneNumberColumn().setOnEditCommit(e -> {
            adminDAO.getAllUsers().get(e.getTablePosition().getRow()).setPhoneNumber(e.getNewValue());
        });
        this.empListView.getEmployeeAccessLevelColumn().setOnEditCommit(e -> {
            adminDAO.getAllUsers().get(e.getTablePosition().getRow()).setAccessLevel(e.getNewValue());
        });
        this.empListView.getEmployeeSalaryColumn().setOnEditCommit(e -> {
            adminDAO.getAllUsers().get(e.getTablePosition().getRow()).setSalary((double)e.getNewValue());
        });

        this.empListView.getUpdateEmployeeListButton().setOnAction(e -> {
            if(this.adminDAO.updateAll()) {
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
        String employeeFullName = empListView.getEmployeeFullNameField().getText();
        String employeeUsername = empListView.getEmployeeUsernameField().getText();
        String employeePassword = empListView.getEmployeePasswordField().getText();
        LocalDate employeeDOB = empListView.getEmployeeDOBField().getValue();
        String employeeEmail = empListView.getEmployeeEmailField().getText();
        String employeePhoneNumber = empListView.getEmployeePhoneNumberField().getText();
        if(employeeDOB == null)
            employeeDOB = LocalDate.now();
        double employeeSalary = Double.parseDouble(empListView.getEmployeeSalaryField().getText());
        Access employeeAccessLevel = empListView.getAccessLevelList().getValue();
        if(employeeAccessLevel == null)
            employeeAccessLevel = Access.Cashier;
        if (employeeFullName.isEmpty() || employeeUsername.isEmpty() || employeePassword.isEmpty() || employeeEmail.isEmpty() || employeePhoneNumber.isEmpty() || employeeSalary == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.show();
        }
        try {
            if (employeeAccessLevel.equals(Access.Cashier)) {
                adminDAO.insertUser(new Cashier(employeeUsername, employeePassword, employeeFullName, employeeDOB, employeePhoneNumber, employeeEmail, employeeSalary));
            } else if (employeeAccessLevel.equals(Access.Manager)) {
                adminDAO.insertUser(new Manager(employeeUsername, employeePassword, employeeFullName, employeeDOB, employeePhoneNumber, employeeEmail, employeeSalary));
            } else if (employeeAccessLevel.equals(Access.Administrator)) {
                adminDAO.insertUser(new Administrator(employeeUsername, employeePassword, employeeFullName, employeeDOB, employeePhoneNumber, employeeEmail, employeeSalary));
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Employee Registered Successfully");
            alert.show();
        } catch(FileNotFoundException fnfe) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File not found");
            alert.setHeaderText("input file not found");
            alert.show();
        } catch(IOException ioe) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("input file problem");
            alert.show();
        } catch(ClassNotFoundException cnfe) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("class not found file problem");
            alert.show();
        }
    }
}
