package Views;

import Interfaces.Views.IUserView;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
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

public class UserView extends Pane implements IUserView {

    private static final String EMPLOYEES = "Employees";
    private static final String SUPPLIERS = "Suppliers";
    private static final String INVENTORY = "Inventory";
    private static final String MANAGE = "Manage";
    private static final String PROFILE = "Profile";
    private static final String LABEL_STYLE = "-fx-text-fill: white; -fx-font: 25px Helvetica; -fx-font-weight: bold;";
    private static final String BUTTON_STYLE = "-fx-background-color: #364958; -fx-font-weight: bold;";
    private static final String SIDEBAR_LABEL_STYLE = "-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;";

    BorderPane root = new BorderPane();

    VBox sidebar = new VBox(15);
    Label homeLabel = new Label("Home");
    Label inventoryLabel = new Label(INVENTORY);
    Label employeeLabel = new Label(EMPLOYEES);
    Label performanceLabel = new Label("Performance");
    Label billGenerateLabel = new Label("Generate Bill");
    Label suppliersLabel = new Label(SUPPLIERS);
    Label profileLabel = new Label(PROFILE);

    Menu menu = new Menu("Menu");
    MenuItem homeItem = new MenuItem("Home");
    MenuItem inventoryItem = new MenuItem(INVENTORY);
    MenuItem employeeItem = new MenuItem(EMPLOYEES);
    MenuItem exitItem = new MenuItem("Exit");

    Menu billMenu = new Menu("Bills");
    MenuItem newBillItem = new MenuItem("Generate Bill");
    MenuItem viewPerformanceItem = new MenuItem("View Performance");
    MenuItem viewBillItem = new MenuItem("View Bills");

    Menu profileMenu = new Menu(PROFILE);
    MenuItem profileItem = new MenuItem("View Profile");
    MenuItem logoutItem = new MenuItem("Logout");

    Label homeWelcomeLabel = new Label("Welcome, " + "admin" + "!");
    FlowPane buttonGrid = new FlowPane(Orientation.HORIZONTAL, 20, 20);

    PieChart pieChart = new PieChart();

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

        root.setTop(menuBar);

        // Sidebar

        sidebar.setStyle("-fx-background-color: #364958; -fx-padding: 20;");

        homeLabel.setStyle(SIDEBAR_LABEL_STYLE);

        inventoryLabel.setStyle(SIDEBAR_LABEL_STYLE);

        this.employeeLabel.setStyle(SIDEBAR_LABEL_STYLE);

        performanceLabel.setStyle(SIDEBAR_LABEL_STYLE);

        billGenerateLabel.setStyle(SIDEBAR_LABEL_STYLE);

        suppliersLabel.setStyle(SIDEBAR_LABEL_STYLE);

        this.profileLabel.setStyle(SIDEBAR_LABEL_STYLE);
        sidebar.getChildren().addAll(homeLabel, inventoryLabel, this.employeeLabel, performanceLabel, billGenerateLabel, suppliersLabel, this.profileLabel);
        sidebar.setPrefWidth(150);
        root.setLeft(sidebar);

        // Home page
        homePage.setStyle("-fx-background-color: white;");

        VBox homeBox = new VBox();
        homeBox.setPadding(new Insets(10));

        homeWelcomeLabel.setStyle("-fx-text-fill: #364958; -fx-font: 40px Helvetica; -fx-font-weight: bold;");

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(1);
        colorAdjust.setSaturation(-1);

        //Generate Bill Button
        generateBillButton.setStyle(BUTTON_STYLE);
        HBox generateBillButtonPane = new HBox();
        Image generateBillImage = new Image("/invoice.png");
        ImageView generateBillImageView = new ImageView(generateBillImage);
        generateBillImageView.setEffect(colorAdjust);
        generateBillImageView.setFitHeight(90);
        generateBillImageView.setFitWidth(90);
        VBox generateBillLabelPane = new VBox();
        Label generateLabel = new Label("Generate");
        Label billLabel = new Label("Bill");
        generateLabel.setStyle(LABEL_STYLE);
        billLabel.setStyle(LABEL_STYLE);
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
        manageEmployeeButton.setStyle(BUTTON_STYLE);
        HBox manageEmployeeButtonPane = new HBox();
        Image manageEmployeeImage = new Image("/teamwork.png");
        ImageView manageEmployeeImageView = new ImageView(manageEmployeeImage);
        manageEmployeeImageView.setEffect(colorAdjust);
        manageEmployeeImageView.setFitHeight(90);
        manageEmployeeImageView.setFitWidth(90);
        VBox manageEmployeeLabelPane = new VBox();
        Label manageLabel = new Label(MANAGE);
        Label employeeButtonLabel = new Label(EMPLOYEES);
        manageLabel.setStyle(LABEL_STYLE);
        employeeButtonLabel.setStyle(LABEL_STYLE);
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
        viewPerformanceButton.setStyle(BUTTON_STYLE);
        HBox viewPerformanceButtonPane = new HBox();
        Image viewPerformanceImage = new Image("/line-chart.png");
        ImageView viewPerformanceImageView = new ImageView(viewPerformanceImage);
        viewPerformanceImageView.setEffect(colorAdjust);
        viewPerformanceImageView.setFitHeight(90);
        viewPerformanceImageView.setFitWidth(90);
        VBox viewPerformanceLabelPane = new VBox();
        Label viewLabel = new Label("View");
        Label performanceHomeLabel = new Label("Performance");
        viewLabel.setStyle(LABEL_STYLE);
        performanceHomeLabel.setStyle(LABEL_STYLE);
        viewPerformanceLabelPane.getChildren().addAll(viewLabel, performanceHomeLabel);
        viewPerformanceLabelPane.setAlignment(Pos.CENTER_RIGHT);
        viewPerformanceButtonPane.getChildren().addAll(viewPerformanceImageView, viewPerformanceLabelPane);
        viewPerformanceButtonPane.setSpacing(30);
        viewPerformanceButtonPane.setPadding(new Insets(5));
        viewPerformanceButtonPane.setAlignment(Pos.CENTER);
        viewPerformanceButton.setGraphic(viewPerformanceButtonPane);
        viewPerformanceButton.setMinWidth(300);
        viewPerformanceButton.setMinHeight(150);

        //Manage Inventory Button
        manageInventoryButton.setStyle(BUTTON_STYLE);
        HBox manageInventoryButtonPane = new HBox();
        Image manageInventoryImage = new Image("/shipping.png");
        ImageView manageInventoryImageView = new ImageView(manageInventoryImage);
        manageInventoryImageView.setEffect(colorAdjust);
        manageInventoryImageView.setFitHeight(90);
        manageInventoryImageView.setFitWidth(90);
        VBox manageInventoryLabelPane = new VBox();
        Label manageInvLabel = new Label(MANAGE);
        Label inventoryHomeLabel = new Label(INVENTORY);
        manageInvLabel.setStyle(LABEL_STYLE);
        inventoryHomeLabel.setStyle(LABEL_STYLE);
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
        profileButton.setStyle(BUTTON_STYLE);
        HBox profileButtonPane = new HBox();
        Image profileImage = new Image("/profile-user.png");
        ImageView profileImageView = new ImageView(profileImage);
        profileImageView.setEffect(colorAdjust);
        profileImageView.setFitHeight(90);
        profileImageView.setFitWidth(90);
        VBox profileLabelPane = new VBox();
        profileLabel.setStyle(LABEL_STYLE);
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
        manageSuppliersButton.setStyle(BUTTON_STYLE);
        HBox manageSuppliersButtonPane = new HBox();
        Image manageSuppliersImage = new Image("/parcel.png");
        ImageView manageSuppliersImageView = new ImageView(manageSuppliersImage);
        manageSuppliersImageView.setEffect(colorAdjust);
        manageSuppliersImageView.setFitHeight(90);
        manageSuppliersImageView.setFitWidth(90);
        VBox manageSuppliersLabelPane = new VBox();
        Label manageSuppLabel = new Label(MANAGE);
        Label suppliersHomeLabel = new Label(SUPPLIERS);
        manageSuppLabel.setStyle(LABEL_STYLE);
        suppliersHomeLabel.setStyle(LABEL_STYLE);
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
        buttonGrid.setPrefWrapLength(700);
        buttonGrid.setMaxWidth(1000);
        buttonGrid.getChildren().addAll(generateBillButton, manageEmployeeButton, viewPerformanceButton, manageInventoryButton, profileButton, manageSuppliersButton);

        //Graph Box
        VBox graphBox = new VBox();
        graphBox.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 20px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");

        Label graphBoxLabel = new Label("Today's Performance");
        graphBoxLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");

        graphBox.setPadding(new Insets(20, 20, 20, 20));

        graphBox.getChildren().addAll(graphBoxLabel, pieChart);

        homeBox.getChildren().addAll(homeWelcomeLabel, buttonGrid);
        homeBox.setSpacing(20);
        homePage.getChildren().addAll(homeBox, graphBox);
        homePage.setSpacing(50);
        homePage.setPadding(new Insets(20));


        root.setCenter(homePage);
    }

    public BorderPane getRoot() {
        return root;
    }

    public Label getHomeWelcomeLabel() {
        return homeWelcomeLabel;
    }

    public Button getGenerateBillButton() {
        return generateBillButton;
    }

    public Button getManageEmployeeButton() {
        return manageEmployeeButton;
    }

    public Button getViewPerformanceButton() {
        return viewPerformanceButton;
    }

    public Button getManageInventoryButton() {
        return manageInventoryButton;
    }

    public Button getProfileButton() {
        return profileButton;
    }

    public Button getManageSuppliersButton() {
        return manageSuppliersButton;
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

    public FlowPane getButtonGrid() {
        return buttonGrid;
    }

    public PieChart getPieChart() {
        return pieChart;
    }
}


