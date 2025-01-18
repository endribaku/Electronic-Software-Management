package Controllers;

import DAO.UserFileHandler;
import Models.User;
import Views.UserView;

public class UserController {
    private UserView view;
    private UserFileHandler adminDAO;

    public UserController(User user) {
        this.view = new UserView();
        this.adminDAO = new UserFileHandler();
    }

    public UserController() {
        this.view = new UserView();
        this.adminDAO = new UserFileHandler();
    }

    public UserView getView() {
        return view;
    }

    public UserFileHandler getAdminDAO() {
        return adminDAO;
    }

    private void onInitialize() {

    }
}
