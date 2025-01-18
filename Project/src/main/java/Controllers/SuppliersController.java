package Controllers;

import DAO.SuppliersFileHandler;
import Models.User;
import Views.SupplierManagementView;

public class SuppliersController {
    private SupplierManagementView view = new SupplierManagementView();
    private SuppliersFileHandler handler = new SuppliersFileHandler();
    private User currentUser;

    public SuppliersController() {

    }


    // Controller setting the currentUser as the one who controls
    public SuppliersController(User user) {
        this.currentUser = user;
    }

    public SupplierManagementView getView() {
        return view;
    }

    public SuppliersFileHandler getHandler() {
        return handler;
    }
}
