package Views;

import Controllers.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class AdminView extends Pane {

    BorderPane Root = new BorderPane();

    public AdminView() {

        //MenuBar
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menu");
        MenuItem homeItem = new MenuItem("Home");
        homeItem.setOnAction(e -> Root.setCenter(new AdminView().getRoot()));
        MenuItem inventoryItem = new MenuItem("Inventory");
        inventoryItem.setOnAction(e -> Root.setCenter(new InventoryController().getView().getInventoryPage()));
        MenuItem employeeItem = new MenuItem("Employees");
        employeeItem.setOnAction(e -> Root.setCenter(new EmployeeManagementController().getEmpListView().getEmployeesPage()));
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> System.exit(0));
        menu.getItems().addAll(homeItem, exitItem, inventoryItem, employeeItem);
        Menu billMenu = new Menu("Bills");
        MenuItem newBillItem = new MenuItem("Generate Bill");
        newBillItem.setOnAction(e -> Root.setCenter(new BillManagementController().getGenerateView().getBillGeneratePage()));
        MenuItem viewPerformanceItem = new MenuItem("View Performance");
        viewPerformanceItem.setOnAction(e -> Root.setCenter(new EmployeePerformanceController().getView().getEmployeePerformancePage()));
        MenuItem viewBillItem = new MenuItem("View Bills");
        viewBillItem.setOnAction(e -> Root.setCenter(new BillManagementController().getManagementView().getBillManagePage()));
        billMenu.getItems().addAll(newBillItem, viewPerformanceItem, viewBillItem);
        Menu profileMenu = new Menu("Profile");
        MenuItem profileItem = new MenuItem("View Profile");
        profileItem.setOnAction(e -> Root.setCenter(new ProfileController().getView().getProfilePage()));
        MenuItem logoutItem = new MenuItem("Logout");
        logoutItem.setOnAction(e -> System.exit(0));
        profileMenu.getItems().addAll(profileItem, logoutItem);
        menuBar.getMenus().addAll(menu, billMenu, profileMenu);

        Root.setTop(menuBar);

        // Sidebar
        VBox sidebar = new VBox(15);
        sidebar.setStyle("-fx-background-color: #364958; -fx-padding: 20;");
        Label homeLabel = new Label("Home");
        homeLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");
        Label inventoryLabel = new Label("Inventory");
        inventoryLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");
        Label employeeLabel = new Label("Employees");
        employeeLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");
        Label performanceLabel = new Label("Performance");
        performanceLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");
        Label billGenerateLabel = new Label("Generate Bill");
        billGenerateLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");
        Label billManagementLabel = new Label("Manage Bills");
        billManagementLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");
        Label suppliersLabel = new Label("Suppliers");
        suppliersLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");
        Label profileLabel = new Label("Profile");
        profileLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");
        sidebar.getChildren().addAll(homeLabel, inventoryLabel, employeeLabel, performanceLabel, billGenerateLabel, billManagementLabel, suppliersLabel, profileLabel);
        sidebar.setPrefWidth(150);
        Root.setLeft(sidebar);

        // Home page
        GridPane homePage = new GridPane();
        homePage.setHgap(10);
        homePage.setVgap(10);
        homePage.setStyle("-fx-background-color: white;");

        // Sales chart
        LineChart<String, Number> lineChart = createLineChart();
        homePage.add(lineChart, 0, 0);

        // Pie chart
        PieChart pieChart = createPieChart();
        homePage.add(pieChart, 1, 0);

        Root.setCenter(homePage);

        // Add content to center
        homeLabel.onMouseClickedProperty().set(e -> Root.setCenter(homePage));
        inventoryLabel.onMouseClickedProperty().set(e -> Root.setCenter(new InventoryController().getView().getInventoryPage()));
        employeeLabel.onMouseClickedProperty().set(e -> Root.setCenter(new EmployeeManagementController().getEmpListView().getEmployeesPage()));
        performanceLabel.onMouseClickedProperty().set(e -> Root.setCenter(new EmployeePerformanceController().getView().getEmployeePerformancePage()));
        billGenerateLabel.onMouseClickedProperty().set(e -> Root.setCenter(new BillManagementController().getGenerateView().getBillGeneratePage()));
        billManagementLabel.onMouseClickedProperty().set(e -> Root.setCenter(new BillManagementController().getManagementView().getBillManagePage()));
        suppliersLabel.onMouseClickedProperty().set(e -> Root.setCenter(new SuppliersController().getView().getSuppliersPage()));
        profileLabel.onMouseClickedProperty().set(e -> Root.setCenter(new ProfileController().getView().getProfilePage()));
    }

    public BorderPane getRoot() {
        return Root;
    }

    private LineChart<String, Number> createLineChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Sales Over Time");
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Sales");
        series.getData().add(new XYChart.Data<>("Mon", 1000));
        series.getData().add(new XYChart.Data<>("Tue", 2000));
        series.getData().add(new XYChart.Data<>("Wed", 3000));
        series.getData().add(new XYChart.Data<>("Thu", 4000));
        series.getData().add(new XYChart.Data<>("Fri", 3500));
        lineChart.getData().add(series);
        return lineChart;
    }

    private PieChart createPieChart() {
        PieChart pieChart = new PieChart();
        pieChart.setTitle("Sales by City");
        pieChart.getData().add(new PieChart.Data("City A", 40));
        pieChart.getData().add(new PieChart.Data("City B", 30));
        pieChart.getData().add(new PieChart.Data("City C", 20));
        pieChart.getData().add(new PieChart.Data("City D", 10));
        return pieChart;
    }
}


