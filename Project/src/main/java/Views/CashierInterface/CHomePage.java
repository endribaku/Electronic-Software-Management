package Views.CashierInterface;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

public class CHomePage extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        BorderPane home = new BorderPane();

        HBox topbar = new HBox();
        topbar.setAlignment((Pos.CENTER_LEFT));
        topbar.setPadding(new Insets(20));
        topbar.setStyle("-fx-background-color: E0E0CE");

        Image homeImage = new Image(getClass().getResource("/icons8-electronic-chip-100 (1).png").toExternalForm());
        ImageView logoImage = new ImageView(homeImage);
        logoImage.setFitWidth(25);
        logoImage.setFitHeight(25);
        Button logobutton = new Button();
        logobutton.setGraphic(logoImage);
        logobutton.setOnAction(e ->new CHomePage().start(stage));

        topbar.getChildren().add(logobutton);

        VBox sidebar = new VBox();
        sidebar.setAlignment((Pos.CENTER));
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #E0E0CE");

        home.setTop(topbar);
        home.setLeft(sidebar);

        //template to test Login button, will fix later and turn into Cashier Home
        Label welcome = new Label("Hello!");
        GridPane grid = new GridPane();
        grid.setAlignment((Pos.CENTER));
        grid.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        grid.setHgap(5.5);
        grid.setVgap(5.5);
        grid.add(welcome, 0, 0);

        home.setCenter(grid);

        stage.setScene(new Scene(home));
        stage.setTitle("Tech Store");
        stage.show();
    }
}
