package Controllers;

import DAO.UserFileHandler;
import Exceptions.InvalidCredentialsException;
import Models.User;
import Views.LoginView;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;



public class LoginController {
    private LoginView view;
    private UserFileHandler handler = new UserFileHandler();

    public LoginController(Stage stage) {
        view = new LoginView();

        this.view.getBtLogin().setOnAction(e -> handleLogin());
    }

    private void handleLogin() throws InvalidCredentialsException {

        String username = view.getUsernameTextField().getText().trim();
        String password = view.getPasswordTextField().getText().trim();

        System.out.println(username + " " + password);

        if (username.isEmpty() || password.isEmpty())
            throw new InvalidCredentialsException("Please enter a username and password");

      User currentUser = handler.authenticateUser(username, password);

        if ((currentUser != null)) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + username + "!");
            Scene homeScene = new Scene(new UserController(currentUser).getView().getRoot(), 1500, 700);
            Stage primaryStage = (Stage) view.getApplication().getWindow();
            primaryStage.setScene(homeScene);
        } else
            throw new InvalidCredentialsException("Invalid username or password.");
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
