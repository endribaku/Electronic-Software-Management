package Views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CashierView {


    public void show(Stage primaryStage) {
        StackPane bgRoot = new StackPane();
        bgRoot.setStyle("-fx-background-color: #364958; -fx-padding: 80; -fx-border-radius: 20px;");
        BorderPane Root = new BorderPane();
        Root.setStyle("-fx-border-color: #F3F3E9; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 10px; -fx-background-color: #F3F3E9; -fx-background-radius: 15px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.35), 5, 0.3, 4, 4);");

        // Sidebar
        VBox sidebar = new VBox(15);
        sidebar.setStyle("-fx-background-color: #F3F3E9; -fx-padding: 20;");
        Label homeLabel = new Label("Home");
        homeLabel.setStyle("-fx-text-fill: #364958; -fx-font: 17px Helvetica; -fx-font-weight: bold; -fx-text-decoration: underline;");
        Label billsLabel = new Label("Bills");
        billsLabel.setStyle("-fx-text-fill: #364958; -fx-font: 14px Helvetica; -fx-font-weight: bold;");
        sidebar.getChildren().addAll(homeLabel, billsLabel);
        sidebar.setPrefWidth(150);
        Root.setLeft(sidebar);

        // Header
        HBox header = new HBox(10);
        header.setStyle("-fx-background-color: #F3F3E9; -fx-padding: 10;");
        Label titleLabel = new Label("Store");
        titleLabel.setStyle("-fx-text-fill: #364958; -fx-font: 20px Helvetica; -fx-font-weight: bold;");
        Label profileLabel = new Label("Profile");
        profileLabel.setStyle("-fx-text-fill: #364958; -fx-font: 20px Helvetica; -fx-font-weight: bold;");
        profileLabel.setAlignment(Pos.BOTTOM_RIGHT);
        header.getChildren().addAll(titleLabel, profileLabel);
        Root.setTop(header);


        // Home page
        GridPane homePage = new GridPane();
        homePage.setHgap(10);
        homePage.setVgap(10);
        homePage.setStyle("-fx-background-color: #e0e0ce;");

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
        billsLabel.onMouseClickedProperty().set(e -> new BillManagementView().show(primaryStage) );

        bgRoot.getChildren().add(Root);
        bgRoot.setAlignment(Pos.CENTER);


        // Scene
        Scene scene = new Scene(bgRoot, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard");
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
}
