package Controllers;

import DAO.BillFileHandler;
import Views.BillGenerateView;
import Views.BillManagementView;

public class BillManagementController {
    private BillManagementView managementView = new BillManagementView();
    private BillGenerateView generateView = new BillGenerateView();
    private BillFileHandler billFileHandler = new BillFileHandler();

    public BillManagementController() {

    }

    public BillManagementView getManagementView() {
        return managementView;
    }

    public BillGenerateView getGenerateView() {
        return generateView;
    }

    public BillFileHandler getBillFileHandler() {
        return billFileHandler;
    }
}
