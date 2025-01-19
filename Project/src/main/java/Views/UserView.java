package Views;

import Controllers.*;
import javafx.geometry.Insets;
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

    HBox homePage = new HBox();

    public UserView() {

        //MenuBar
        MenuBar menuBar = new MenuBar();
        menu.getItems().addAll(homeItem, exitItem, inventoryItem, employeeItem);
        billMenu.getItems().addAll(newBillItem, viewPerformanceItem, viewBillItem);
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

        suppliersLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");

        profileLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");
        sidebar.getChildren().addAll(homeLabel, inventoryLabel, employeeLabel, performanceLabel, billGenerateLabel, suppliersLabel, profileLabel);
        sidebar.setPrefWidth(150);
        Root.setLeft(sidebar);

        // Home page
        homePage.setStyle("-fx-background-color: white;");

        VBox homeBox = new VBox(15);
        homeBox.setPadding(new Insets(20));

        Label homeLabel = new Label("Welcome, " + "admin" + "!");
        homeLabel.setStyle("-fx-text-fill: #364958; -fx-font: 30px Helvetica; -fx-font-weight: bold;");

        GridPane buttonGrid = new GridPane();
        Button generateBillButton = new Button("Generate Bill");
        generateBillButton.setStyle("-fx-background-color: #364958; -fx-text-fill: white; -fx-font: 17px Helvetica; -fx-font-weight: bold;");
        generateBillButton.setMinWidth(300);
        generateBillButton.setMinHeight(150);
        Button manageEmployeeButton = new Button("Manage Employees");
        manageEmployeeButton.setStyle("-fx-background-color: #364958; -fx-text-fill: white; -fx-font: 17px Helvetica; -fx-font-weight: bold;");
        manageEmployeeButton.setMinWidth(300);
        manageEmployeeButton.setMinHeight(150);
        Button viewPerformanceButton = new Button("View Performance");
        viewPerformanceButton.setStyle("-fx-background-color: #364958; -fx-text-fill: white; -fx-font: 17px Helvetica; -fx-font-weight: bold;");
        viewPerformanceButton.setMinWidth(300);
        viewPerformanceButton.setMinHeight(150);
        Button manageInventoryButton = new Button("Manage Inventory");
        manageInventoryButton.setStyle("-fx-background-color: #364958; -fx-text-fill: white; -fx-font: 17px Helvetica; -fx-font-weight: bold;");
        manageInventoryButton.setMinWidth(300);
        manageInventoryButton.setMinHeight(150);
        buttonGrid.add(generateBillButton, 0, 0);
        buttonGrid.add(manageEmployeeButton, 1, 0);
        buttonGrid.add(viewPerformanceButton, 0, 1);
        buttonGrid.add(manageInventoryButton, 1, 1);
        buttonGrid.setHgap(20);
        buttonGrid.setVgap(20);

        homeBox.getChildren().addAll(homeLabel, buttonGrid);
        homePage.getChildren().addAll(homeBox);

        // Sales chart
//        LineChart<String, Number> lineChart = createLineChart();
//        homePage.add(lineChart, 0, 0);
//
//        // Pie chart
//        PieChart pieChart = createPieChart();
//        homePage.add(pieChart, 1, 0);

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

    public HBox getHomePage() {
        return homePage;
    }
}


