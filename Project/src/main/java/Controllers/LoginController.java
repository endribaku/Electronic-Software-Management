package Controllers;

import DAO.UserFileHandler;
import Exceptions.InvalidCredentialsException;
import Interfaces.DAO.IUserFileHandler;
import Interfaces.Views.ILoginView;
import Models.User;
import Views.LoginView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController {

    private ILoginView view;                 // interface
    private IUserFileHandler handler;         // interface
    private Stage stage;                      // keep for navigation

    public LoginController(Stage stage) {
        this(stage, new LoginView(), new UserFileHandler());
    }

    // for tests: inject mock view + fake handler
    public LoginController(Stage stage, ILoginView view, IUserFileHandler handler) {
        this.stage = stage;
        this.view = view;
        this.handler = handler;

        this.view.onLogin(() -> {
            try {
                handleLogin();
            } catch (InvalidCredentialsException credentialsError) {
                this.view.showError("Login Failed", credentialsError.getMessage());
            }
        });
    }

    private void handleLogin() throws InvalidCredentialsException {

        String username = view.getUsernameText().trim();
        String password = view.getPasswordText().trim();

        if (username.isEmpty() || password.isEmpty())
            throw new InvalidCredentialsException("Please enter a username and password");

        User currentUser = handler.authenticateUser(username, password);

        if (currentUser != null) {
            view.showInfo("Login Successful", "Welcome, " + username + "!");
            Scene homeScene = new Scene(new UserController(currentUser).getView().getRoot(), 1500, 700);
            stage.setScene(homeScene);
        } else {
            throw new InvalidCredentialsException("Invalid username or password.");
        }
    }

    public ILoginView getView() {
        return view;
    }

    public IUserFileHandler getHandler() {
        return handler;
    }
}
