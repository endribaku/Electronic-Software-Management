package Views;

import Controllers.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class UserView extends Pane {

    BorderPane Root = new BorderPane();



    VBox sidebar = new VBox(15);
    Label homeLabel = new Label("Home");
    Label inventoryLabel = new Label("Inventory");
    Label employeeLabel = new Label("Employees");
    Label performanceLabel = new Label("Performance");
    Label billGenerateLabel = new Label("Generate Bill");
    Label billManagementLabel = new Label("Manage Bills");
    Label suppliersLabel = new Label("Suppliers");
    Label profileLabel = new Label("Profile");


    Menu menu = new Menu("Menu");
    MenuItem homeItem = new MenuItem("Home");
    MenuItem inventoryItem = new MenuItem("Inventory");
    MenuItem employeeItem = new MenuItem("Employees");
    MenuItem exitItem = new MenuItem("Exit");
    Menu billMenu = new Menu("Bills");



    MenuItem newBillItem = new MenuItem("Generate Bill");
    MenuItem viewPerformanceItem = new MenuItem("View Performance");
    MenuItem viewBillItem = new MenuItem("View Bills");
    Menu profileMenu = new Menu("Profile");
    MenuItem profileItem = new MenuItem("View Profile");
    MenuItem logoutItem = new MenuItem("Logout");




    GridPane homePage = new GridPane();

    public UserView() {

        //MenuBar
        MenuBar menuBar = new MenuBar();


//        homeItem.setOnAction(e -> Root.setCenter(new UserView().getRoot()));

//        inventoryItem.setOnAction(e -> Root.setCenter(new InventoryController().getView().getInventoryPage()));

//        employeeItem.setOnAction(e -> Root.setCenter(new EmployeeManagementController().getEmpListView().getEmployeesPage()));

//        exitItem.setOnAction(e -> System.exit(0));
        menu.getItems().addAll(homeItem, exitItem, inventoryItem, employeeItem);


//        newBillItem.setOnAction(e -> Root.setCenter(new BillManagementController().getGenerateView().getBillGeneratePage()));

//        viewPerformanceItem.setOnAction(e -> Root.setCenter(new EmployeePerformanceController().getView().getEmployeePerformancePage()));

//        viewBillItem.setOnAction(e -> Root.setCenter(new BillManagementController().getManagementView().getBillManagePage()));
        billMenu.getItems().addAll(newBillItem, viewPerformanceItem, viewBillItem);

//        profileItem.setOnAction(e -> Root.setCenter(new ProfileController().getView().getProfilePage()));

        logoutItem.setOnAction(e -> System.exit(0));
        profileMenu.getItems().addAll(profileItem, logoutItem);
        menuBar.getMenus().addAll(menu, billMenu, profileMenu);

        Root.setTop(menuBar);

        // Sidebar

        sidebar.setStyle("-fx-background-color: #364958; -fx-padding: 20;");

        homeLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");

        inventoryLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");

        employeeLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");

        performanceLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");

        billGenerateLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");

        billManagementLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");

        suppliersLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");

        profileLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");
        sidebar.getChildren().addAll(homeLabel, inventoryLabel, employeeLabel, performanceLabel, billGenerateLabel, billManagementLabel, suppliersLabel, profileLabel);
        sidebar.setPrefWidth(150);
        Root.setLeft(sidebar);

        // Home page

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
//        homeLabel.onMouseClickedProperty().set(e -> Root.setCenter(homePage));
//        inventoryLabel.onMouseClickedProperty().set(e -> Root.setCenter(new InventoryController().getView().getInventoryPage()));
//        employeeLabel.onMouseClickedProperty().set(e -> Root.setCenter(new EmployeeManagementController().getEmpListView().getEmployeesPage()));
//        performanceLabel.onMouseClickedProperty().set(e -> Root.setCenter(new EmployeePerformanceController().getView().getEmployeePerformancePage()));
//        billGenerateLabel.onMouseClickedProperty().set(e -> Root.setCenter(new BillManagementController().getGenerateView().getBillGeneratePage()));
//        billManagementLabel.onMouseClickedProperty().set(e -> Root.setCenter(new BillManagementController().getManagementView().getBillManagePage()));
//        suppliersLabel.onMouseClickedProperty().set(e -> Root.setCenter(new SuppliersController().getView().getSuppliersPage()));
//        profileLabel.onMouseClickedProperty().set(e -> Root.setCenter(new ProfileController().getView().getProfilePage()));
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


    public Label getProfileLabel() {
        return profileLabel;
    }

    public Label getSuppliersLabel() {
        return suppliersLabel;
    }

    public Label getBillManagementLabel() {
        return billManagementLabel;
    }

    public Label getBillGenerateLabel() {
        return billGenerateLabel;
    }

    public Label getPerformanceLabel() {
        return performanceLabel;
    }

    public Label getEmployeeLabel() {
        return employeeLabel;
    }

    public Label getInventoryLabel() {
        return inventoryLabel;
    }

    public Label getHomeLabel() {
        return homeLabel;
    }

    public VBox getSidebar() {
        return sidebar;
    }

    public Menu getMenu() {
        return menu;
    }

    public MenuItem getHomeItem() {
        return homeItem;
    }

    public MenuItem getInventoryItem() {
        return inventoryItem;
    }

    public MenuItem getEmployeeItem() {
        return employeeItem;
    }

    public MenuItem getExitItem() {
        return exitItem;
    }

    public Menu getBillMenu() {
        return billMenu;
    }

    public MenuItem getNewBillItem() {
        return newBillItem;
    }

    public MenuItem getViewPerformanceItem() {
        return viewPerformanceItem;
    }

    public MenuItem getViewBillItem() {
        return viewBillItem;
    }

    public Menu getProfileMenu() {
        return profileMenu;
    }

    public MenuItem getProfileItem() {
        return profileItem;
    }

    public MenuItem getLogoutItem() {
        return logoutItem;
    }

    public GridPane getHomePage() {
        return homePage;
    }
}


