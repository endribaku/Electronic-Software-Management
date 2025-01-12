package Views;

import Models.User;
import Views.CashierInterface.CHomePage;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class LoginPage extends Application {

    private String email;
    private String password;

    @Override
    public void start(Stage stage) throws IOException {
        HBox parent = new HBox();

        //template, will add photo and better design sa ti msoj cik m mir n left side, dhe better labels on right panelogin side
        GridPane paneLogin = new GridPane();
        paneLogin.setAlignment((Pos.CENTER));
        paneLogin.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        paneLogin.setHgap(5.5);
        paneLogin.setVgap(5.5);

        Label emailLabel = new Label("Email:");
        emailLabel.setStyle("-fx-font: 11pt Helvetica;");
        paneLogin.add(emailLabel, 0, 0);
        TextField emailTextField = new TextField();
        paneLogin.add(emailTextField, 1, 0);
        Label passLabel = new Label("Password:");
        passLabel.setStyle("-fx-font: 11pt Helvetica;");
        paneLogin.add(passLabel, 0, 1);
        TextField passTextField = new TextField();
        paneLogin.add(passTextField, 1,1);
        Button btLogin = new Button("Login");
        btLogin.setStyle("-fx-font: 11pt Helvetica;");
        paneLogin.add(btLogin,1,2);
        GridPane.setHalignment(btLogin, HPos.RIGHT);

        //after login checks for matching email and password
        //if none returns exception with warning label "User not found (template)" and has user try again
        //if found but incorrect password, returns exception with warning label "Incorrect password" and has user try again
        //if found & matching email & password, loads the right Home instance based on the User type (Admin, Manager, Cashier)

        btLogin.setOnAction(e -> {
            //template, will check matching email & pass based on Users ArrayList
            email = emailTextField.getText();
            password =  passTextField.getText();

            Label messageLabel = new Label();
            messageLabel.setTextFill(Color.GREEN);
            messageLabel.setText("Login Successful");
            paneLogin.add(messageLabel, 1, 3);

            //template HomePage, will write an if-check based on the loaded user type to load the correct instance
            new CHomePage().start(stage);
        });

        VBox leftSide = new VBox();
        leftSide.setAlignment((Pos.CENTER));
        leftSide.setPadding(new Insets(20));
        leftSide.setStyle("-fx-background-color: cornflowerblue");

        // Replace with the actual path to your image
        ImageView logoView = new ImageView("/icons8-electronic-chip-100 (1).png");
        logoView.setFitWidth(100);
        logoView.setPreserveRatio(true);
        leftSide.getChildren().add(logoView);

        Label logoSlogan = new Label("Welcome to the store!");
        logoSlogan.setStyle("-fx-text-fill: white; -fx-font: 16px Helvetica");
        leftSide.getChildren().add(logoSlogan);

        parent.getChildren().add(leftSide);
        parent.getChildren().add(paneLogin);

        stage.setScene(new Scene(parent));
        stage.setTitle("Tech Store");
        stage.show();
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

}
