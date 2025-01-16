package Controllers;

import DAO.UserFileHandler;
import Models.Access;
import Models.Administrator;
import Models.Cashier;
import Models.Manager;
import Views.AdminView;
import Views.EmployeesListView;
import javafx.scene.control.Alert;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

public class AdminController {
    private AdminView view;
    private UserFileHandler adminDAO;

    public AdminController() {
        this.view = new AdminView();
        this.adminDAO = new UserFileHandler();
    }

    public AdminView getView() {
        return view;
    }

    public UserFileHandler getAdminDAO() {
        return adminDAO;
    }
}
