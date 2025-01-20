package Controllers;

import DAO.InventoryFileHandler;
import DAO.SuppliersFileHandler;
import Models.Item;
import Models.Supplier;
import Models.User;
import Views.SupplierManagementView;
import javafx.scene.control.Alert;

import java.util.ArrayList;

public class SuppliersController {
    private SupplierManagementView view = new SupplierManagementView();
    private SuppliersFileHandler handler = new SuppliersFileHandler();
    private InventoryFileHandler inventoryHandler = new InventoryFileHandler();
    private User currentUser;

    // Controller setting the currentUser as the one who controls
    public SuppliersController(User user) {
        this.currentUser = user;
        this.view.getAddSupplierButton().setOnAction(e -> onSupplierAdd());
        this.view.getSuppliersTableView().setItems(inventoryHandler.getSuppliersList());
        this.view.getItemBoxList().setItems(inventoryHandler.getSuppliedItems());
        setEditRows();
    }

    public SupplierManagementView getView() {
        return view;
    }

    public SuppliersFileHandler getHandler() {
        return handler;
    }

    public InventoryFileHandler getInventoryHandler() {
        return inventoryHandler;
    }

    private void onSupplierAdd() {
        String supplierName = view.getSuppliersNameField().getText();
        ArrayList<Item> itemList = new ArrayList<>(view.getItemBoxList().getSelectionModel().getSelectedItems());

        try {
            if (supplierName.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Input");
                alert.show();
            }
            else {
                handler.insertSupplier(new Supplier(supplierName, itemList));

                view.getSuppliersNameField().clear();
                view.getItemBoxList().getSelectionModel().clearSelection();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Supplier Added Successfully");
                alert.show();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void setEditRows() {
        this.view.getSupplierIDColumn().setOnEditCommit(e -> {
            handler.getSuppliers().get(e.getTablePosition().getRow()).setSupplierID(e.getNewValue().toString());
        });
        this.view.getSupplierNameColumn().setOnEditCommit(e -> {
            handler.getSuppliers().get(e.getTablePosition().getRow()).setName(e.getNewValue());
        });

        this.view.getUpdateSupplierListButton().setOnAction(e -> {
            if (handler.updateAll()) {
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Success");
                success.setHeaderText("Supplier Table Updated Successfully");
                success.show();
            } else {
                Alert fail = new Alert(Alert.AlertType.ERROR);
                fail.setTitle("Success");
                fail.setHeaderText("Supplier Table Update Error");
                fail.show();
            }
        });
    }
}
