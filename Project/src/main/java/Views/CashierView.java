package Views;

import Controllers.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CashierView {

    BorderPane Root = new BorderPane();

    public CashierView() {

        //MenuBar
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menu");
        MenuItem homeItem = new MenuItem("Home");
        MenuItem exitItem = new MenuItem("Exit");
        menu.getItems().addAll(homeItem, exitItem);
        Menu billMenu = new Menu("Bills");
        MenuItem newBillItem = new MenuItem("Generate Bill");
        MenuItem viewBillItem = new MenuItem("View Bills");
        billMenu.getItems().addAll(newBillItem, viewBillItem);
        Menu profileMenu = new Menu("Profile");
        MenuItem profileItem = new MenuItem("View Profile");
        MenuItem logoutItem = new MenuItem("Logout");
        profileMenu.getItems().addAll(profileItem, logoutItem);
        menuBar.getMenus().addAll(menu, billMenu, profileMenu);

        Root.setTop(menuBar);

        // Sidebar
        VBox sidebar = new VBox(15);
        sidebar.setStyle("-fx-background-color: #364958; -fx-padding: 20;");
        Label homeLabel = new Label("Home");
        homeLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");
        Label performanceLabel = new Label("Performance");
        performanceLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");
        Label billGenerateLabel = new Label("Generate Bill");
        billGenerateLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");
        Label profileLabel = new Label("Profile");
        profileLabel.setStyle("-fx-text-fill: white; -fx-font: 14px Helvetica; -fx-font-weight: bold;");
        sidebar.getChildren().addAll(homeLabel, performanceLabel, billGenerateLabel, profileLabel);
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
        performanceLabel.onMouseClickedProperty().set(e -> Root.setCenter(new EmployeePerformanceController().getView().getEmployeePerformancePage()));
        billGenerateLabel.onMouseClickedProperty().set(e -> Root.setCenter(new BillManagementController().getGenerateView().getBillGeneratePage()));
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
