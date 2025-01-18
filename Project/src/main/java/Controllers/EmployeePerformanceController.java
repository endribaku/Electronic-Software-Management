package Controllers;

import DAO.BillFileHandler;
import DAO.ItemFIleHandler;
import DAO.UserFileHandler;
import Views.EmployeePerformanceView;

public class EmployeePerformanceController {
    private EmployeePerformanceView view = new EmployeePerformanceView();
    private UserFileHandler userFileHandler = new UserFileHandler();
    private ItemFIleHandler itemFileHandler = new ItemFIleHandler();
    private BillFileHandler billFileHandler = new BillFileHandler();

    public EmployeePerformanceController() {

    }

    public EmployeePerformanceView getView() {
        return view;
    }

    public UserFileHandler getUserFileHandler() {
        return userFileHandler;
    }

    public ItemFIleHandler getItemFileHandler() {
        return itemFileHandler;
    }

    public BillFileHandler getBillFileHandler() {
        return billFileHandler;
    }
}
