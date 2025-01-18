package Controllers;

import DAO.UserFileHandler;
import Views.CashierView;

public class CashierController {
    private CashierView view = new CashierView();
    private UserFileHandler handler = new UserFileHandler();

    public CashierController() {

    }

    public CashierView getView() {
        return view;
    }

    public UserFileHandler getHandler() {
        return handler;
    }
}
