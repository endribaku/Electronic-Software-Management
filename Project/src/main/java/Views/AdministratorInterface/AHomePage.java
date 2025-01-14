package Views.AdministratorInterface;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AHomePage {

    public void show(Stage primaryStage) {
        //Background + Menu
        StackPane bgRoot = new StackPane();
        bgRoot.setStyle("-fx-background-color: #364958; -fx-padding: 80; -fx-border-radius: 20px;");
        BorderPane Root = new BorderPane();
        Root.setStyle("-fx-border-color: #F3F3E9; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 10px; -fx-background-color: #F3F3E9; -fx-background-radius: 15px;" + "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.35), 5, 0.3, 4, 4);");

        // Sidebar
        VBox sidebar = new VBox(15);
        sidebar.setStyle("-fx-background-color: #F3F3E9; -fx-padding: 20;");
        Label homeLabel = new Label("Home");
        homeLabel.setStyle("-fx-text-fill: #364958; -fx-font: 14px Helvetica; -fx-font-weight: bold;");
        Label inventoryLabel = new Label("Inventory");
        inventoryLabel.setStyle("-fx-text-fill: #364958; -fx-font: 14px Helvetica; -fx-font-weight: bold;");
        Label employeeLabel = new Label("Employees");
        employeeLabel.setStyle("-fx-text-fill: #364958; -fx-font: 14px Helvetica; -fx-font-weight: bold;");
        sidebar.getChildren().addAll(homeLabel, inventoryLabel, employeeLabel);
        sidebar.setPrefWidth(150);
        Root.setLeft(sidebar);

        // Header
        HBox header = new HBox(10);
        header.setStyle("-fx-background-color: #F3F3E9; -fx-padding: 10;");
        Label titleLabel = new Label("Store");
        titleLabel.setStyle("-fx-text-fill: #364958; -fx-font: 20px Helvetica; -fx-font-weight: bold;");
        header.getChildren().addAll(titleLabel);
        Root.setTop(header);

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
        Root.setCenter(homePage);
        homeLabel.onMouseClickedProperty().set(e -> Root.setCenter(homePage));
        employeeLabel.onMouseClickedProperty().set(e -> Root.setCenter(employeePage));

        bgRoot.getChildren().add(Root);
        bgRoot.setAlignment(Pos.CENTER);


        // Scene
        Scene scene = new Scene(bgRoot, 1200, 800);
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
