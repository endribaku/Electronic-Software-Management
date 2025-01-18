package Controllers;

import DAO.UserFileHandler;
import Views.ManagerView;

public class ManagerController {
    private ManagerView view = new ManagerView();
    private UserFileHandler handler = new UserFileHandler();

    public ManagerController() {

    }

    public ManagerView getView() {
        return view;
    }

    public UserFileHandler getHandler() {
        return handler;
    }
}
