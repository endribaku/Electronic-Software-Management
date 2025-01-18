package Controllers;

import DAO.UserFileHandler;
import Models.User;
import Views.ProfileView;

public class ProfileController {

    private ProfileView view = new ProfileView();
    private UserFileHandler handler = new UserFileHandler();
    private User currentUser;
    public ProfileController() {

    }


    // Controller setting the currentUser as the one who controls
    public ProfileController(User user) {
        this.currentUser = currentUser;
    }

    public ProfileView getView() {
        return view;
    }

    public UserFileHandler getHandler() {
        return handler;
    }
}
