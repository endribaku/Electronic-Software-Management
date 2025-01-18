package Controllers;

import DAO.UserFileHandler;
import Models.User;
import Views.LoginView;
import Views.UserView;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class LoginController {
    private LoginView view;
    private UserFileHandler handler = new UserFileHandler();

    public LoginController(Stage stage) {
        view = new LoginView(stage);

        this.view.getBtLogin().setOnAction(e -> {
            //handleLogin();
            Scene homeScene = new Scene(new UserController(new User()).getView().getRoot(), 1500, 700);
            Stage primaryStage = (Stage) view.getApplication().getWindow();
            primaryStage.setScene(homeScene);
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
            User currentUser = handler.authenticateUser(username, password);

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
