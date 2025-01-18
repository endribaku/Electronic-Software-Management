package Controllers;

import DAO.UserFileHandler;
import Views.LoginView;
import javafx.stage.Stage;

public class LoginController {
    private LoginView view;
    private UserFileHandler handler;

    public LoginController(Stage stage) {
        view = new LoginView(stage);
        handler = new UserFileHandler();
    }

    public LoginView getView() {
        return view;
    }

    public UserFileHandler getHandler() {
        return handler;
    }
}
