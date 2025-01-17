package Controllers;

import DAO.SuppliersFileHandler;
import Views.SupplierManagementView;

public class SuppliersController {
    private SupplierManagementView view = new SupplierManagementView();
    private SuppliersFileHandler handler = new SuppliersFileHandler();

    public SuppliersController() {

    }

    public SupplierManagementView getView() {
        return view;
    }

    public SuppliersFileHandler getHandler() {
        return handler;
    }
}
