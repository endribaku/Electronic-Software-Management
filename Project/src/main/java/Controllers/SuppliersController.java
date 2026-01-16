package Controllers;

import DAO.InventoryFileHandler;
import DAO.SuppliersFileHandler;
import Exceptions.SupplierCreationException;
import Interfaces.DAO.IInventoryFileHandler;
import Interfaces.DAO.ISuppliersFileHandler;
import Interfaces.Views.ISupplierManagementView;
import Models.Item;
import Models.Supplier;
import Models.User;
import Views.SupplierManagementView;

import java.util.ArrayList;
import java.util.List;

public class SuppliersController {

    // Constants
    private static final String ALERT_SUCCESS = "Success";
    private static final String ALERT_ERROR = "Error";
    private static final String ALERT_INVALID_INPUT = "Invalid Input";
    private static final String ALERT_SUPPLIER_ADDED = "Supplier Added Successfully";
    private static final String ALERT_TABLE_UPDATED = "Supplier Table Updated Successfully";
    private static final String ALERT_TABLE_UPDATE_FAIL = "Supplier Table Update Error";

    // Fields (✅ keep names unchanged)
    private ISupplierManagementView view = new SupplierManagementView();
    private ISuppliersFileHandler handler = new SuppliersFileHandler();
    private IInventoryFileHandler inventoryHandler = new InventoryFileHandler();
    private User currentUser;

    // Constructor (default)
    public SuppliersController(User user) {
        this.currentUser = user;
        onInitialize();
    }

    // ✅ Constructor for integration testing (inject mocks)
    public SuppliersController(
            User user,
            ISupplierManagementView view,
            ISuppliersFileHandler handler,
            IInventoryFileHandler inventoryHandler
    ) {
        this.currentUser = user;
        this.view = view;
        this.handler = handler;
        this.inventoryHandler = inventoryHandler;
        onInitialize();
    }

    // Getters (keep)
    public ISupplierManagementView getView() { return view; }
    public ISuppliersFileHandler getHandler() { return handler; }
    public IInventoryFileHandler getInventoryHandler() { return inventoryHandler; }

    // ===== Init wiring =====
    private void onInitialize() {

        // Populate table and available items list
        view.setSuppliersTableItems(inventoryHandler.getSuppliersList());
        view.setAvailableItems(inventoryHandler.getSuppliedItems());

        // Wire actions (controller -> view)
        view.onAddSupplier(this::onSupplierAdd);
        view.onDeleteSupplier(this::onSupplierDelete);
        view.onEditSupplier(this::onSupplierEdit);
        view.onCancelUpdate(() -> {
            view.showAddSupplierBox();
        });

        view.onUpdateTable(() -> {
            boolean success = handler.updateAll();
            if (success) {
                view.showInfo(ALERT_SUCCESS, ALERT_TABLE_UPDATED);
            } else {
                view.showError(ALERT_ERROR, ALERT_TABLE_UPDATE_FAIL);
            }
        });
    }

    // ===== Add Supplier =====
    private void onSupplierAdd() {
        String supplierName = safeTrim(view.getSupplierNameInput());
        List<Item> selectedItems = view.getSelectedItemsForSupplier();

        if (supplierName.isEmpty()) {
            view.showError(ALERT_ERROR, ALERT_INVALID_INPUT);
            return;
        }

        ArrayList<Item> itemList = new ArrayList<>(selectedItems);

        try {
            inventoryHandler.addSupplier(new Supplier(supplierName, itemList));

            view.clearAddSupplierInputs();
            view.showInfo(ALERT_SUCCESS, ALERT_SUPPLIER_ADDED);

        } catch (SupplierCreationException supplierCreationError) {
            view.showError(ALERT_ERROR, supplierCreationError.getMessage());
        } catch (Exception unexpectedError) {
            view.showError(ALERT_ERROR, unexpectedError.getMessage());
        }
    }

    // ===== Edit Supplier =====
    private void onSupplierEdit() {
        Supplier selectedSupplier = view.getSelectedSupplier();
        if (selectedSupplier == null) {
            return;
        }

        view.showEditSupplierBox();
        view.setEditSupplierName(selectedSupplier.getName());

        // When update button is clicked, update the selected supplier
        // (We keep it inside edit flow so it uses the currently selected supplier)
        // NOTE: If you prefer, add another view.onUpdateSupplier(...) callback.
        onSupplierUpdate(selectedSupplier);
    }

    private void onSupplierUpdate(Supplier supplier) {
        String newName = safeTrim(view.getEditSupplierName());

        if (newName.isEmpty()) {
            view.showError(ALERT_ERROR, ALERT_INVALID_INPUT);
            return;
        }

        inventoryHandler.updateSupplier(supplier.getSupplierID(), newName);

        view.showAddSupplierBox();
        view.showInfo(ALERT_SUCCESS, "Supplier Updated Successfully");
    }

    // ===== Delete Supplier =====
    private void onSupplierDelete() {
        Supplier selectedSupplier = view.getSelectedSupplier();
        if (selectedSupplier == null) {
            return;
        }

        inventoryHandler.deleteSupplier(selectedSupplier);
        view.showInfo(ALERT_SUCCESS, "Supplier Deleted Successfully");
    }

    private String safeTrim(String value) {
        return value == null ? "" : value.trim();
    }
}
