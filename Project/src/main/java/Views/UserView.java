package Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    Button generateBillButton = new Button();
    Button manageEmployeeButton = new Button();
    Button viewPerformanceButton = new Button();
    Button manageInventoryButton = new Button();
    Button profileButton = new Button();
    Button manageSuppliersButton = new Button();

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

        this.employeeLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");

        performanceLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");

        billGenerateLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");

        suppliersLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");

        this.profileLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");
        sidebar.getChildren().addAll(homeLabel, inventoryLabel, this.employeeLabel, performanceLabel, billGenerateLabel, suppliersLabel, this.profileLabel);
        sidebar.setPrefWidth(150);
        Root.setLeft(sidebar);

        // Home page
        homePage.setStyle("-fx-background-color: white;");

        VBox homeBox = new VBox();
        homeBox.setPadding(new Insets(10));

        Label homeLabel = new Label("Welcome, " + "admin" + "!");
        homeLabel.setStyle("-fx-text-fill: #364958; -fx-font: 40px Helvetica; -fx-font-weight: bold;");

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(1);
        colorAdjust.setSaturation(-1);

        GridPane buttonGrid = new GridPane();

        //Generate Bill Button
        generateBillButton.setStyle("-fx-background-color: #364958; -fx-font-weight: bold;");
        HBox generateBillButtonPane = new HBox();
        Image generateBillImage = new Image("/invoice.png");
        ImageView generateBillImageView = new ImageView(generateBillImage);
        generateBillImageView.setEffect(colorAdjust);
        generateBillImageView.setFitHeight(90);
        generateBillImageView.setFitWidth(90);
        VBox generateBillLabelPane = new VBox();
        Label generateLabel = new Label("Generate");
        Label billLabel = new Label("Bill");
        generateLabel.setStyle("-fx-text-fill: white; -fx-font: 25px Helvetica; -fx-font-weight: bold;");
        billLabel.setStyle("-fx-text-fill: white; -fx-font: 25px Helvetica; -fx-font-weight: bold;");
        generateBillLabelPane.getChildren().addAll(generateLabel, billLabel);
        generateBillLabelPane.setAlignment(Pos.CENTER_RIGHT);
        generateBillButtonPane.getChildren().addAll(generateBillImageView, generateBillLabelPane);
        generateBillButtonPane.setSpacing(50);
        generateBillButtonPane.setPadding(new Insets(5));
        generateBillButtonPane.setAlignment(Pos.CENTER);
        generateBillButton.setGraphic(generateBillButtonPane);
        generateBillButton.setMinWidth(300);
        generateBillButton.setMinHeight(150);

        //Manage Employee Button
        manageEmployeeButton.setStyle("-fx-background-color: #364958; -fx-font-weight: bold;");
        HBox manageEmployeeButtonPane = new HBox();
        Image manageEmployeeImage = new Image("/teamwork.png");
        ImageView manageEmployeeImageView = new ImageView(manageEmployeeImage);
        manageEmployeeImageView.setEffect(colorAdjust);
        manageEmployeeImageView.setFitHeight(90);
        manageEmployeeImageView.setFitWidth(90);
        VBox manageEmployeeLabelPane = new VBox();
        Label manageLabel = new Label("Manage");
        Label employeeButtonLabel = new Label("Employees");
        manageLabel.setStyle("-fx-text-fill: white; -fx-font: 25px Helvetica; -fx-font-weight: bold;");
        employeeButtonLabel.setStyle("-fx-text-fill: white; -fx-font: 25px Helvetica; -fx-font-weight: bold;");
        manageEmployeeLabelPane.getChildren().addAll(manageLabel, employeeButtonLabel);
        manageEmployeeLabelPane.setAlignment(Pos.CENTER_RIGHT);
        manageEmployeeButtonPane.getChildren().addAll(manageEmployeeImageView, manageEmployeeLabelPane);
        manageEmployeeButtonPane.setSpacing(50);
        manageEmployeeButtonPane.setPadding(new Insets(5));
        manageEmployeeButtonPane.setAlignment(Pos.CENTER);
        manageEmployeeButton.setGraphic(manageEmployeeButtonPane);
        manageEmployeeButton.setMinWidth(300);
        manageEmployeeButton.setMinHeight(150);

        //View Performance Button
        viewPerformanceButton.setStyle("-fx-background-color: #364958; -fx-font-weight: bold;");
        HBox viewPerformanceButtonPane = new HBox();
        Image viewPerformanceImage = new Image("/line-chart.png");
        ImageView viewPerformanceImageView = new ImageView(viewPerformanceImage);
        viewPerformanceImageView.setEffect(colorAdjust);
        viewPerformanceImageView.setFitHeight(90);
        viewPerformanceImageView.setFitWidth(90);
        VBox viewPerformanceLabelPane = new VBox();
        Label viewLabel = new Label("Manage");
        Label performanceHomeLabel = new Label("Employees");
        viewLabel.setStyle("-fx-text-fill: white; -fx-font: 25px Helvetica; -fx-font-weight: bold;");
        performanceHomeLabel.setStyle("-fx-text-fill: white; -fx-font: 25px Helvetica; -fx-font-weight: bold;");
        viewPerformanceLabelPane.getChildren().addAll(viewLabel, performanceHomeLabel);
        viewPerformanceLabelPane.setAlignment(Pos.CENTER_RIGHT);
        viewPerformanceButtonPane.getChildren().addAll(viewPerformanceImageView, viewPerformanceLabelPane);
        viewPerformanceButtonPane.setSpacing(50);
        viewPerformanceButtonPane.setPadding(new Insets(5));
        viewPerformanceButtonPane.setAlignment(Pos.CENTER);
        viewPerformanceButton.setGraphic(viewPerformanceButtonPane);
        viewPerformanceButton.setMinWidth(300);
        viewPerformanceButton.setMinHeight(150);

        //Manage Inventory Button
        manageInventoryButton.setStyle("-fx-background-color: #364958; -fx-font-weight: bold;");
        HBox manageInventoryButtonPane = new HBox();
        Image manageInventoryImage = new Image("/shipping.png");
        ImageView manageInventoryImageView = new ImageView(manageInventoryImage);
        manageInventoryImageView.setEffect(colorAdjust);
        manageInventoryImageView.setFitHeight(90);
        manageInventoryImageView.setFitWidth(90);
        VBox manageInventoryLabelPane = new VBox();
        Label manageInvLabel = new Label("Manage");
        Label inventoryHomeLabel = new Label("Inventory");
        manageInvLabel.setStyle("-fx-text-fill: white; -fx-font: 25px Helvetica; -fx-font-weight: bold;");
        inventoryHomeLabel.setStyle("-fx-text-fill: white; -fx-font: 25px Helvetica; -fx-font-weight: bold;");
        manageInventoryLabelPane.getChildren().addAll(manageInvLabel, inventoryHomeLabel);
        manageInventoryLabelPane.setAlignment(Pos.CENTER_RIGHT);
        manageInventoryButtonPane.getChildren().addAll(manageInventoryImageView, manageInventoryLabelPane);
        manageInventoryButtonPane.setSpacing(50);
        manageInventoryButtonPane.setPadding(new Insets(5));
        manageInventoryButtonPane.setAlignment(Pos.CENTER);
        manageInventoryButton.setGraphic(manageInventoryButtonPane);
        manageInventoryButton.setMinWidth(300);
        manageInventoryButton.setMinHeight(150);

        //Manage Inventory Button
        profileButton.setStyle("-fx-background-color: #364958; -fx-font-weight: bold;");
        HBox profileButtonPane = new HBox();
        Image profileImage = new Image("/profile-user.png");
        ImageView profileImageView = new ImageView(profileImage);
        profileImageView.setEffect(colorAdjust);
        profileImageView.setFitHeight(90);
        profileImageView.setFitWidth(90);
        VBox profileLabelPane = new VBox();
        Label profileLabel = new Label("Profile");
        profileLabel.setStyle("-fx-text-fill: white; -fx-font: 25px Helvetica; -fx-font-weight: bold;");
        profileLabelPane.getChildren().addAll(profileLabel);
        profileLabelPane.setAlignment(Pos.CENTER_RIGHT);
        profileButtonPane.getChildren().addAll(profileImageView, profileLabelPane);
        profileButtonPane.setSpacing(50);
        profileButtonPane.setPadding(new Insets(5));
        profileButtonPane.setAlignment(Pos.CENTER);
        profileButton.setGraphic(profileButtonPane);
        profileButton.setMinWidth(300);
        profileButton.setMinHeight(150);

        //Manage Inventory Button
        manageSuppliersButton.setStyle("-fx-background-color: #364958; -fx-font-weight: bold;");
        HBox manageSuppliersButtonPane = new HBox();
        Image manageSuppliersImage = new Image("/parcel.png");
        ImageView manageSuppliersImageView = new ImageView(manageSuppliersImage);
        manageSuppliersImageView.setEffect(colorAdjust);
        manageSuppliersImageView.setFitHeight(90);
        manageSuppliersImageView.setFitWidth(90);
        VBox manageSuppliersLabelPane = new VBox();
        Label manageSuppLabel = new Label("Manage");
        Label suppliersHomeLabel = new Label("Suppliers");
        manageSuppLabel.setStyle("-fx-text-fill: white; -fx-font: 25px Helvetica; -fx-font-weight: bold;");
        suppliersHomeLabel.setStyle("-fx-text-fill: white; -fx-font: 25px Helvetica; -fx-font-weight: bold;");
        manageSuppliersLabelPane.getChildren().addAll(manageSuppLabel, suppliersHomeLabel);
        manageSuppliersLabelPane.setAlignment(Pos.CENTER_RIGHT);
        manageSuppliersButtonPane.getChildren().addAll(manageSuppliersImageView, manageSuppliersLabelPane);
        manageSuppliersButtonPane.setSpacing(50);
        manageSuppliersButtonPane.setPadding(new Insets(5));
        manageSuppliersButtonPane.setAlignment(Pos.CENTER);
        manageSuppliersButton.setGraphic(manageSuppliersButtonPane);
        manageSuppliersButton.setMinWidth(300);
        manageSuppliersButton.setMinHeight(150);

        //Grid Modification
        buttonGrid.add(generateBillButton, 0, 0);
        buttonGrid.add(manageEmployeeButton, 1, 0);
        buttonGrid.add(viewPerformanceButton, 0, 1);
        buttonGrid.add(manageInventoryButton, 1, 1);
        buttonGrid.add(profileButton, 0, 2);
        buttonGrid.add(manageSuppliersButton, 1, 2);
        buttonGrid.setHgap(20);
        buttonGrid.setVgap(20);

        //Graph Box
        VBox graphBox = new VBox();
        graphBox.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 20px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");

        Label graphBoxLabel = new Label("Today's Performance");
        graphBoxLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");

        graphBox.setPadding(new Insets(20, 20, 20, 20));

        // Sales chart
        LineChart<String, Number> lineChart = createLineChart();

        // Pie chart
        PieChart pieChart = createPieChart();
        graphBox.getChildren().addAll(graphBoxLabel, lineChart, pieChart);


        homeBox.getChildren().addAll(homeLabel, buttonGrid);
        homeBox.setSpacing(20);
        homePage.getChildren().addAll(homeBox, graphBox);
        homePage.setSpacing(50);
        homePage.setPadding(new Insets(20));



        Root.setCenter(homePage);

        // Add content to center
//        homeLabel.onMouseClickedProperty().set(e -> Root.setCenter(homePage));
//        inventoryLabel.onMouseClickedProperty().set(e -> Root.setCenter(new InventoryController().getView().getInventoryPage()));
//        performanceHomeLabel.onMouseClickedProperty().set(e -> Root.setCenter(new EmployeeManagementController().getEmpListView().getEmployeesPage()));
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


