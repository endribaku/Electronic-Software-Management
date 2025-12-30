package Controllers;

import DAO.InventoryFileHandler;
import DAO.SuppliersFileHandler;
import Exceptions.SupplierCreationException;
import Models.Item;
import Models.Supplier;
import Models.User;
import Views.SupplierManagementView;
import javafx.scene.control.Alert;

import java.util.ArrayList;

public class SuppliersController {

    // Constants
    private static final String ALERT_SUCCESS = "Success";
    private static final String ALERT_ERROR = "Error";
    private static final String ALERT_INVALID_INPUT = "Invalid Input";
    private static final String ALERT_SUPPLIER_ADDED = "Supplier Added Successfully";
    private static final String ALERT_TABLE_UPDATED = "Supplier Table Updated Successfully";
    private static final String ALERT_TABLE_UPDATE_FAIL = "Supplier Table Update Error";

    // Fields
    private final SupplierManagementView view = new SupplierManagementView();
    private final SuppliersFileHandler handler = new SuppliersFileHandler();
    private final InventoryFileHandler inventoryHandler = new InventoryFileHandler();
    private final User currentUser; // Can be used for permissions later

    // Constructor
    public SuppliersController(User user) {
        this.currentUser = user;

        // Add supplier action
        this.view.getAddSupplierButton().setOnAction(e -> onSupplierAdd());

        // Populate table and list
        this.view.getSuppliersTableView().setItems(inventoryHandler.getSuppliersList());
        this.view.getItemBoxList().setItems(inventoryHandler.getSuppliedItems());

        // Delete supplier action
        this.view.getDeleteSupplierListButton().setOnAction(e -> onSupplierDelete());

        // Edit supplier action
        this.view.getEditSupplierListButton().setOnAction(e -> onSupplierEdit());

        // Cancel update action
        this.view.getCancelUpdateButton().setOnAction(e -> {
            this.view.getManageSupplierBox().getChildren().remove(this.view.getEditSupplierBox());
            this.view.getManageSupplierBox().getChildren().add(this.view.getAddSupplierBox());
        });

        // Enable inline table editing
        setEditRows();
    }

    // Getters
    public SupplierManagementView getView() { return view; }
    public SuppliersFileHandler getHandler() { return handler; }
    public InventoryFileHandler getInventoryHandler() { return inventoryHandler; }

    // Add Supplier
    private void onSupplierAdd() {
        String supplierName = view.getSuppliersNameField().getText();
        ArrayList<Item> itemList = new ArrayList<>(view.getItemBoxList().getSelectionModel().getSelectedItems());

        try {
            if (supplierName.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(ALERT_ERROR);
                alert.setHeaderText(ALERT_INVALID_INPUT);
                alert.show();
            } else {
                inventoryHandler.addSupplier(new Supplier(supplierName, itemList));

                view.getSuppliersNameField().clear();
                view.getItemBoxList().getSelectionModel().clearSelection();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(ALERT_SUCCESS);
                alert.setHeaderText(ALERT_SUPPLIER_ADDED);
                alert.show();
            }
        } catch (SupplierCreationException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ALERT_ERROR);
            alert.setHeaderText("Supplier Creation Failed");
            alert.setContentText(e.getMessage());
            alert.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ALERT_ERROR);
            alert.setHeaderText("Unexpected Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    // Table Editing
    private void setEditRows() {
        view.getSupplierIDColumn().setOnEditCommit(e ->
                handler.getSuppliers().get(e.getTablePosition().getRow()).setSupplierID(e.getNewValue())
        );

        view.getSupplierNameColumn().setOnEditCommit(e ->
                handler.getSuppliers().get(e.getTablePosition().getRow()).setName(e.getNewValue())
        );

        view.getUpdateSupplierListButton().setOnAction(e -> {
            boolean success = handler.updateAll();
            Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            alert.setTitle(success ? ALERT_SUCCESS : ALERT_ERROR);
            alert.setHeaderText(success ? ALERT_TABLE_UPDATED : ALERT_TABLE_UPDATE_FAIL);
            alert.show();
        });
    }

    // Edit Supplier
    private void onSupplierEdit() {
        view.getManageSupplierBox().getChildren().remove(view.getAddSupplierBox());
        view.getManageSupplierBox().getChildren().add(view.getEditSupplierBox());

        Supplier selectedSupplier = view.getSuppliersTableView().getSelectionModel().getSelectedItem();
        if (selectedSupplier == null) return;

        view.getSuppliersEditNameField().setText(selectedSupplier.getName());

        view.getUpdateSupplierButton().setOnAction(e -> onSupplierUpdate(selectedSupplier));
    }

    private void onSupplierUpdate(Supplier supplier) {
        inventoryHandler.updateSupplier(supplier.getSupplierID(), view.getSuppliersEditNameField().getText());

        view.getManageSupplierBox().getChildren().remove(view.getEditSupplierBox());
        view.getManageSupplierBox().getChildren().add(view.getAddSupplierBox());
    }

    // Delete Supplier
    private void onSupplierDelete() {
        Supplier selectedSupplier = view.getSuppliersTableView().getSelectionModel().getSelectedItem();
        if (selectedSupplier == null) return;

        inventoryHandler.deleteSupplier(selectedSupplier);
    }
}