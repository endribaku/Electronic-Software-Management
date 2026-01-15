package Controllers;

import DAO.BillFileHandler;
import DAO.UserFileHandler;
import Interfaces.Views.IUserView;
import Models.*;
import Views.UserView;
import javafx.scene.chart.PieChart;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController {

    private IUserView view;               // ✅ interface type, same field name
    private UserFileHandler handler;      // keep as-is
    private User currentUser;

    // Optional but recommended: avoid static calls for testability
    private BillFileHandler billFileHandler = new BillFileHandler();

    public UserController(User user) {
        this(user, new UserView(), new UserFileHandler());
    }

    // ✅ constructor for integration testing (inject mock view)
    public UserController(User user, IUserView view, UserFileHandler handler) {
        this.view = view;
        this.handler = handler;
        this.currentUser = user;
        onInitialize();
    }

    public IUserView getView() {
        return view;
    }

    public UserFileHandler getHandler() {
        return handler;
    }

    private void onInitialize() {

        // Welcome label
        this.view.getHomeWelcomeLabel().setText("Welcome, " + currentUser.getUsername() + "!");

        if (!currentUser.hasPermission(Permission.BILL_GENERATION)) {
            this.view.getButtonGrid().getChildren().remove(this.view.getGenerateBillButton());
            this.view.getSidebar().getChildren().remove(this.view.getBillGenerateLabel());
            this.view.getBillMenu().getItems().remove(this.view.getNewBillItem());
        }
        if (!currentUser.hasPermission(Permission.EMPLOYEE_MANAGEMENT)) {
            this.view.getButtonGrid().getChildren().remove(this.view.getManageEmployeeButton());
            this.view.getSidebar().getChildren().remove(this.view.getEmployeeLabel());
            this.view.getMenu().getItems().remove(this.view.getEmployeeItem());
        }
        if (!currentUser.hasPermission(Permission.INVENTORY_ACCESS)) {
            this.view.getButtonGrid().getChildren().remove(this.view.getManageInventoryButton());
            this.view.getSidebar().getChildren().remove(this.view.getInventoryLabel());
            this.view.getMenu().getItems().remove(this.view.getInventoryItem());
        }
        if (!currentUser.hasPermission(Permission.PERFORMANCE_VIEW)) {
            this.view.getButtonGrid().getChildren().remove(this.view.getViewPerformanceButton());
            this.view.getSidebar().getChildren().remove(this.view.getPerformanceLabel());
            this.view.getBillMenu().getItems().remove(this.view.getViewPerformanceItem());
        }
        if (!currentUser.hasPermission(Permission.SUPPLIER_MANAGEMENT)) {
            this.view.getButtonGrid().getChildren().remove(this.view.getManageSuppliersButton());
            this.view.getSidebar().getChildren().remove(this.view.getSuppliersLabel());
        }

        // Menu actions
        this.view.getHomeItem().setOnAction(e ->
                this.view.getRoot().setCenter(new UserController(currentUser).getView().getHomePage()));

        this.view.getInventoryItem().setOnAction(e ->
                this.view.getRoot().setCenter(new InventoryController(currentUser).getView().getInventoryPage()));

        this.view.getEmployeeItem().setOnAction(e ->
                this.view.getRoot().setCenter(new EmployeeManagementController().getEmpListView().getEmployeesPage()));

        this.view.getExitItem().setOnAction(e -> System.exit(0));

        this.view.getNewBillItem().setOnAction(e ->
                this.view.getRoot().setCenter(new BillManagementController(currentUser).getGenerateView().getBillGeneratePage()));

        this.view.getViewPerformanceItem().setOnAction(e ->
                this.view.getRoot().setCenter(new EmployeePerformanceController(currentUser).getView().getEmployeePerformancePage()));

        this.view.getProfileItem().setOnAction(e ->
                this.view.getRoot().setCenter(new ProfileController(currentUser).getView().getProfilePage()));

        this.view.getLogoutItem().setOnAction(e -> System.exit(0));

        // Sidebar actions
        this.view.getHomeLabel().onMouseClickedProperty().set(e ->
                this.view.getRoot().setCenter(new UserController(currentUser).getView().getHomePage()));

        this.view.getInventoryLabel().onMouseClickedProperty().set(e ->
                this.view.getRoot().setCenter(new InventoryController(currentUser).getView().getInventoryPage()));

        this.view.getEmployeeLabel().onMouseClickedProperty().set(e ->
                this.view.getRoot().setCenter(new EmployeeManagementController().getEmpListView().getEmployeesPage()));

        this.view.getPerformanceLabel().onMouseClickedProperty().set(e ->
                this.view.getRoot().setCenter(new EmployeePerformanceController(currentUser).getView().getEmployeePerformancePage()));

        this.view.getBillGenerateLabel().onMouseClickedProperty().set(e ->
                this.view.getRoot().setCenter(new BillManagementController(currentUser).getGenerateView().getBillGeneratePage()));

        this.view.getSuppliersLabel().onMouseClickedProperty().set(e ->
                this.view.getRoot().setCenter(new SuppliersController(currentUser).getView().getSuppliersPage()));

        this.view.getProfileLabel().onMouseClickedProperty().set(e ->
                this.view.getRoot().setCenter(new ProfileController(currentUser).getView().getProfilePage()));

        // Home buttons
        this.view.getGenerateBillButton().setOnAction(e ->
                this.view.getRoot().setCenter(new BillManagementController(currentUser).getGenerateView().getBillGeneratePage()));

        this.view.getManageEmployeeButton().setOnAction(e ->
                this.view.getRoot().setCenter(new EmployeeManagementController().getEmpListView().getEmployeesPage()));

        this.view.getViewPerformanceButton().setOnAction(e ->
                this.view.getRoot().setCenter(new EmployeePerformanceController(currentUser).getView().getEmployeePerformancePage()));

        this.view.getManageSuppliersButton().setOnAction(e ->
                this.view.getRoot().setCenter(new SuppliersController(currentUser).getView().getSuppliersPage()));

        this.view.getManageInventoryButton().setOnAction(e ->
                this.view.getRoot().setCenter(new InventoryController(currentUser).getView().getInventoryPage()));

        this.view.getProfileButton().setOnAction(e ->
                this.view.getRoot().setCenter(new ProfileController(currentUser).getView().getProfilePage()));

        // Pie chart
        calculatePieChart();
    }

    private void calculatePieChart() {
        LocalDate today = LocalDate.now();

        Map<String, Double> cashierEarnings = new HashMap<>();

        // ✅ avoid static -> instance call (better for tests and consistency)
        List<Bill> allBills = billFileHandler.getBills();

        for (Bill bill : allBills) {
            if (bill.getDateOfSale().isEqual(today)) {
                String cashierName = bill.getUsername();
                double amount = bill.getTotalAmount();

                cashierEarnings.put(cashierName, cashierEarnings.getOrDefault(cashierName, 0.0) + amount);
            }
        }

        for (Map.Entry<String, Double> entry : cashierEarnings.entrySet()) {
            this.view.getPieChart().getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
    }
}
