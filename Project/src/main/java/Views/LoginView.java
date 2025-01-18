package Views;

import Controllers.AdminController;
import Models.User;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class LoginView {

    private StackPane bgRoot;
    Scene scene;

    public LoginView(Stage stage) {
        bgRoot = new StackPane();
        bgRoot.setStyle("-fx-background-color: #364958; -fx-padding: 200; -fx-border-radius: 20px;");
        bgRoot.setAlignment(Pos.CENTER);
        BorderPane Root = new BorderPane();
        Root.setStyle("-fx-border-color: #F3F3E9; -fx-border-width: 10px; -fx-border-radius: 15px; -fx-padding: 15px; -fx-background-color: #F3F3E9; -fx-background-radius: 15px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.35), 5, 0.3, 4, 4);");

        VBox loginBox = new VBox();
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setSpacing(50);
        Label titleLabel = new Label("Login");
        titleLabel.setStyle("-fx-text-fill: #364958; -fx-font: 18pt Helvetica; -fx-font-weight: bold;");
//        titleLabel.setAlignment(Pos.TOP_CENTER);

        GridPane paneLogin = new GridPane();
        paneLogin.setAlignment((Pos.CENTER));
        paneLogin.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        paneLogin.setHgap(5.5);
        paneLogin.setVgap(5.5);

        Label emailLabel = new Label("Email:");
        emailLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
        paneLogin.add(emailLabel, 0, 0);
        TextField emailTextField = new TextField();
        paneLogin.add(emailTextField, 1, 0);
        Label passLabel = new Label("Password:");
        passLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
        paneLogin.add(passLabel, 0, 1);
        TextField passTextField = new TextField();
        paneLogin.add(passTextField, 1,1);
        Button btLogin = new Button("Login");
        btLogin.setStyle("-fx-font: 11pt Helvetica;");
        paneLogin.add(btLogin,1,2);
        GridPane.setHalignment(btLogin, HPos.RIGHT);

        loginBox.getChildren().addAll(titleLabel, paneLogin);
        Root.setCenter(loginBox);
        bgRoot.getChildren().add(Root);
        btLogin.setOnAction(e -> {
            //template, will check matching email & pass based on Users ArrayList
            Label messageLabel = new Label();
            messageLabel.setTextFill(Color.GREEN);
            messageLabel.setText("Login Successful");
            paneLogin.add(messageLabel, 1, 3);

            Scene adminScene = new Scene(new AdminController().getView().getRoot(), 1500, 700);
            stage.setScene(adminScene);
            stage.show();
        });
        scene = new Scene(getRoot(), 700, 700);
        bgRoot.setPrefSize(700, 700);
    }

    public boolean login(String username, String password) throws ClassNotFoundException, IOException {

        ArrayList<User> users = new ArrayList<>();

        try(ObjectInputStream inputStream =
                    new ObjectInputStream(new FileInputStream("Data\\employees.dat"));){
            while (true){
                users.add((User)inputStream.readObject());
            }
        }catch (EOFException e){
            System.out.println("All users loaded successfully.");
        }

        for(User u : users ){
            if (String.valueOf(u.getUsername()).equals(username) && String.valueOf(u.getPassword()).equals(password)){
                System.out.println("User " + String.valueOf(u.getUsername()) + " was found");
                return true;
            }
        }
        System.out.println("User not found");
        return false;
    }

    public StackPane getRoot() {
        return bgRoot;
    }

    public Scene getApplication() {
        return scene;
    }

}
