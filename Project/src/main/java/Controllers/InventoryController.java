package Controllers;

import DAO.InventoryFileHandler;
import Views.InventoryView;

public class InventoryController {
    private final InventoryView view = new InventoryView();
    private final InventoryFileHandler inventoryDAO = new InventoryFileHandler();

    public InventoryController() {

    }

    public InventoryView getView() {
        return view;
    }

    public InventoryFileHandler getInventoryDAO() {
        return inventoryDAO;
    }
}
