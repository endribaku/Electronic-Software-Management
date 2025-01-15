package Views;

import Models.Administrator;
import Models.Bill;
import Models.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AdminView {
    private Administrator currentAdmin;

//    public AdminView(Administrator currentAdmin) {
//        this.currentAdmin = currentAdmin;
//    }

    public void show(Stage primaryStage) {

        //MenuBar
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menu");
        MenuItem homeItem = new MenuItem("Home");
        MenuItem inventoryItem = new MenuItem("Inventory");
        MenuItem employeeItem = new MenuItem("Employees");
        MenuItem exitItem = new MenuItem("Exit");
        menu.getItems().addAll(homeItem, exitItem, inventoryItem, employeeItem);
        Menu billMenu = new Menu("Bills");
        MenuItem newBillItem = new MenuItem("Generate Bill");
        MenuItem viewBillItem = new MenuItem("View Bills");
        billMenu.getItems().addAll(newBillItem, viewBillItem);
        Menu profileMenu = new Menu("Profile");
        MenuItem profileItem = new MenuItem("View Profile");
        MenuItem logoutItem = new MenuItem("Logout");
        profileMenu.getItems().addAll(profileItem, logoutItem);
        menuBar.getMenus().addAll(menu, billMenu, profileMenu);

        BorderPane Root = new BorderPane();
        Root.setTop(menuBar);

        // Sidebar
        VBox sidebar = new VBox(15);
        sidebar.setStyle("-fx-background-color: #364958; -fx-padding: 20;");
        Label homeLabel = new Label("Home");
        homeLabel.setStyle("-fx-text-fill: #F3F3E9; -fx-font: 14px Helvetica; -fx-font-weight: bold;");
        Label inventoryLabel = new Label("Inventory");
        inventoryLabel.setStyle("-fx-text-fill: #F3F3E9; -fx-font: 14px Helvetica; -fx-font-weight: bold;");
        Label employeeLabel = new Label("Employees");
        employeeLabel.setStyle("-fx-text-fill: #F3F3E9; -fx-font: 14px Helvetica; -fx-font-weight: bold;");
        sidebar.getChildren().addAll(homeLabel, inventoryLabel, employeeLabel);
        sidebar.setPrefWidth(150);
        Root.setLeft(sidebar);

        // Home page
        GridPane homePage = new GridPane();
        homePage.setHgap(10);
        homePage.setVgap(10);
        homePage.setStyle("-fx-background-color: #e0e0ce;");

        // Sales chart
        LineChart<String, Number> lineChart = createLineChart();
        homePage.add(lineChart, 0, 0);

        // Pie chart
        PieChart pieChart = createPieChart();
        homePage.add(pieChart, 1, 0);

        Root.setCenter(homePage);

        //Inventory page
        GridPane inventoryPage = new GridPane();
        inventoryPage.setHgap(10);
        inventoryPage.setVgap(10);
        inventoryPage.setStyle("-fx-background-color: #e0e0ce;");
        Label inventoryHomeLabel = new Label("Manage Inventory Here");
        inventoryPage.add(inventoryHomeLabel, 0, 0);

        //Employees page
        GridPane employeePage = new GridPane();
        employeePage.setHgap(10);
        employeePage.setVgap(10);
        employeePage.setStyle("-fx-background-color: #e0e0ce;");
        Label employeeHomeLabel = new Label("Manage Employees Here");
        employeePage.add(employeeHomeLabel, 0, 0);

        // Add content to center
        homeLabel.onMouseClickedProperty().set(e -> Root.setCenter(homePage));
        employeeLabel.onMouseClickedProperty().set(e -> Root.setCenter(employeePage));

        // Scene
        Scene scene = new Scene(Root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard");
        primaryStage.show();
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
