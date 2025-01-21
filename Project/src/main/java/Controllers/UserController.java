package Controllers;

import DAO.InventoryFileHandler;
import DAO.UserFileHandler;
import Exceptions.ItemStockException;
import Models.Access;
import Models.Item;
import Models.Permission;
import Models.User;
import Views.UserView;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class UserController {
    private UserView view;
    private UserFileHandler handler;
    private User currentUser;

    public UserController(User user) {
        this.view = new UserView();
        this.handler = new UserFileHandler();
        this.currentUser = user;
        onInitialize();
    }

    public UserController() {
        this.view = new UserView();
        this.handler = new UserFileHandler();
    }

    public UserView getView() {
        return view;
    }

    public UserFileHandler getHandler() {
        return handler;
    }

    private void onInitialize() {

        //Welcome label
        this.view.getHomeWelcomeLabel().setText("Weclome, " + currentUser.getUsername() + "!");

        //initialize set actions for the user menu bar items
        this.view.getHomeItem().setOnAction(e -> this.view.getRoot().setCenter(new UserController(currentUser).getView().getHomePage()));
        this.view.getInventoryItem().setOnAction(e -> this.view.getRoot().setCenter(new InventoryController(currentUser).getView().getInventoryPage()));
        this.view.getEmployeeItem().setOnAction(e -> this.view.getRoot().setCenter(new EmployeeManagementController(currentUser).getEmpListView().getEmployeesPage()));
        this.view.getExitItem().setOnAction(e -> System.exit(0));
        this.view.getNewBillItem().setOnAction(e -> this.view.getRoot().setCenter(new BillManagementController(currentUser).getGenerateView().getBillGeneratePage()));
        this.view.getViewPerformanceItem().setOnAction(e -> this.view.getRoot().setCenter(new EmployeePerformanceController(currentUser).getView().getEmployeePerformancePage()));
        this.view.getProfileItem().setOnAction(e -> this.view.getRoot().setCenter(new ProfileController(currentUser).getView().getProfilePage()));
        this.view.getLogoutItem().setOnAction(e -> System.exit(0));

        //initialize set actions for the user sidebar labels
        this.view.getHomeLabel().onMouseClickedProperty().set(e -> this.view.getRoot().setCenter(new UserController(currentUser).getView().getHomePage()));
        this.view.getInventoryLabel().onMouseClickedProperty().set(e -> this.view.getRoot().setCenter(new InventoryController(currentUser).getView().getInventoryPage()));
        this.view.getEmployeeLabel().onMouseClickedProperty().set(e -> this.view.getRoot().setCenter(new EmployeeManagementController(currentUser).getEmpListView().getEmployeesPage()));
        this.view.getPerformanceLabel().onMouseClickedProperty().set(e -> this.view.getRoot().setCenter(new EmployeePerformanceController(currentUser).getView().getEmployeePerformancePage()));
        this.view.getBillGenerateLabel().onMouseClickedProperty().set(e -> this.view.getRoot().setCenter(new BillManagementController(currentUser).getGenerateView().getBillGeneratePage()));
        this.view.getSuppliersLabel().onMouseClickedProperty().set(e -> this.view.getRoot().setCenter(new SuppliersController(currentUser).getView().getSuppliersPage()));
        this.view.getProfileLabel().onMouseClickedProperty().set(e -> this.view.getRoot().setCenter(new ProfileController(currentUser).getView().getProfilePage()));

        //Initialize for Home Screen buttons
        this.view.getGenerateBillButton().setOnAction(e -> this.view.getRoot().setCenter(new BillManagementController(currentUser).getGenerateView().getBillGeneratePage()));
        this.view.getManageEmployeeButton().setOnAction(e -> this.view.getRoot().setCenter(new EmployeeManagementController(currentUser).getEmpListView().getEmployeesPage()));
        this.view.getViewPerformanceButton().setOnAction(e -> this.view.getRoot().setCenter(new EmployeePerformanceController(currentUser).getView().getEmployeePerformancePage()));
        this.view.getManageSuppliersButton().setOnAction(e -> this.view.getRoot().setCenter(new SuppliersController(currentUser).getView().getSuppliersPage()));
        this.view.getManageInventoryButton().setOnAction(e -> this.view.getRoot().setCenter(new InventoryController(currentUser).getView().getInventoryPage()));
        this.view.getProfileButton().setOnAction(e -> this.view.getRoot().setCenter(new ProfileController(currentUser).getView().getProfilePage()));

        if(!currentUser.hasPermission(Permission.BILL_GENERATION)){
            this.view.getButtonGrid().getChildren().remove(this.view.getGenerateBillButton());
            this.view.getSidebar().getChildren().remove(this.view.getBillGenerateLabel());
            this.view.getBillMenu().getItems().remove(this.view.getNewBillItem());
        }
        if(!currentUser.hasPermission(Permission.EMPLOYEE_MANAGEMENT)){
            this.view.getButtonGrid().getChildren().remove(this.view.getManageEmployeeButton());
            this.view.getSidebar().getChildren().remove(this.view.getEmployeeLabel());
            this.view.getMenu().getItems().remove(this.view.getEmployeeItem());
        }
        if(!currentUser.hasPermission(Permission.INVENTORY_ACCESS)){
            this.view.getButtonGrid().getChildren().remove(this.view.getManageInventoryButton());
            this.view.getSidebar().getChildren().remove(this.view.getInventoryLabel());
            this.view.getMenu().getItems().remove(this.view.getInventoryItem());
        }
        if(!currentUser.hasPermission(Permission.PERFORMANCE_VIEW)){
            this.view.getButtonGrid().getChildren().remove(this.view.getViewPerformanceButton());
            this.view.getSidebar().getChildren().remove(this.view.getPerformanceLabel());
            this.view.getBillMenu().getItems().remove(this.view.getViewPerformanceItem());
        }
        if(!currentUser.hasPermission(Permission.SUPPLIER_MANAGEMENT)){
            this.view.getButtonGrid().getChildren().remove(this.view.getManageSuppliersButton());
            this.view.getSidebar().getChildren().remove(this.view.getSuppliersLabel());
        }
    }
}
