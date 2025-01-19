package Controllers;

import DAO.UserFileHandler;
import Models.User;
import Views.UserView;

public class UserController {
    private UserView view;
    private UserFileHandler adminDAO;
    private User currentUser;

    public UserController(User user) {
        this.view = new UserView();
        this.adminDAO = new UserFileHandler();
        this.currentUser = user;
        onInitialize();
    }

    public UserController() {
        this.view = new UserView();
        this.adminDAO = new UserFileHandler();
    }

    public UserView getView() {
        return view;
    }

    public UserFileHandler getAdminDAO() {
        return adminDAO;
    }

    private void onInitialize() {

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

    }
}
