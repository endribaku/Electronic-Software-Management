package Views;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

public class LoginView {

    private StackPane bgRoot;
    Scene scene;

    TextField usernameTextField = new TextField();
    TextField passwordTextField = new TextField();
    Button btLogin = new Button("Login");

    public LoginView() {
        bgRoot = new StackPane();
        bgRoot.setStyle("-fx-background-color: #364958; -fx-padding: 200; -fx-border-radius: 20px;");
        bgRoot.setAlignment(Pos.CENTER);
        BorderPane root = new BorderPane();
        root.setStyle("-fx-border-color: #F3F3E9; -fx-border-width: 10px; -fx-border-radius: 15px; -fx-padding: 15px; -fx-background-color: #F3F3E9; -fx-background-radius: 15px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.35), 5, 0.3, 4, 4);");

        VBox loginBox = new VBox();
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setSpacing(50);
        Label titleLabel = new Label("Login");
        titleLabel.setStyle("-fx-text-fill: #364958; -fx-font: 18pt Helvetica; -fx-font-weight: bold;");

        GridPane paneLogin = new GridPane();
        paneLogin.setAlignment((Pos.CENTER));
        paneLogin.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        paneLogin.setHgap(5.5);
        paneLogin.setVgap(5.5);

        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
        paneLogin.add(usernameLabel, 0, 0);
        paneLogin.add(usernameTextField, 1, 0);
        Label passLabel = new Label("Password:");
        passLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
        paneLogin.add(passLabel, 0, 1);
        paneLogin.add(passwordTextField, 1,1);
        btLogin.setStyle("-fx-font: 11pt Helvetica;");
        paneLogin.add(btLogin,1,2);
        GridPane.setHalignment(btLogin, HPos.RIGHT);

        loginBox.getChildren().addAll(titleLabel, paneLogin);
        root.setCenter(loginBox);
        bgRoot.getChildren().add(root);

        scene = new Scene(getRoot(), 700, 700);
        bgRoot.setPrefSize(700, 700);
    }

    public StackPane getRoot() {
        return bgRoot;
    }

    public Scene getApplication() {
        return scene;
    }

    public TextField getUsernameTextField() {
        return usernameTextField;
    }

    public TextField getPasswordTextField() {
        return passwordTextField;
    }

    public Button getBtLogin() {
        return btLogin;
    }
}
