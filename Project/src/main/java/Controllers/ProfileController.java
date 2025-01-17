package Controllers;

import DAO.UserFileHandler;
import Views.ProfileView;

public class ProfileController {

    private ProfileView view = new ProfileView();
    private UserFileHandler handler = new UserFileHandler();

    public ProfileController() {

    }

    public ProfileView getView() {
        return view;
    }

    public UserFileHandler getHandler() {
        return handler;
    }
}
