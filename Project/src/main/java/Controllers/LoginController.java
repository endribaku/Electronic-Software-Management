package Controllers;

import DAO.UserFileHandler;
import Models.*;
import Models.User;
import Views.LoginView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.EnumSet;

public class LoginController {
    private LoginView view;
    private UserFileHandler handler = new UserFileHandler();

    public LoginController(Stage stage) {
        view = new LoginView(stage);

        this.view.getBtLogin().setOnAction(e -> {
            handleLogin();
        });
    }

    private void handleLogin() {

        String username = view.getUsernameTextField().getText().trim();
        String password = view.getPasswordTextField().getText().trim();

        System.out.println(username + " " + password);

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter both username and password.");
            return;
        }

        try {

            ObservableList<String> permissions = FXCollections.observableArrayList();
            permissions.add("INVENTORY_ACCESS");
            ObservableList<String> sectors = FXCollections.observableArrayList();
            sectors.add("Electronics");
            User currentUser = handler.authenticateUser(username, password);
//            User currentUser = new User("endri", "123456",
//                    "Endri Baku", LocalDate.of(1999, 12,
//                    12), "050505", "endri.bakuak", 5000, Access.Administrator, permissions, sectors );

            if ((currentUser != null)) {
                showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + username + "!");
                Scene homeScene = new Scene(new UserController(currentUser).getView().getRoot(), 1500, 700);
                Stage primaryStage = (Stage) view.getApplication().getWindow();
                primaryStage.setScene(homeScene);
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while authenticating.");
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public LoginView getView() {
        return view;
    }

    public UserFileHandler getHandler() {
        return handler;
    }
}
