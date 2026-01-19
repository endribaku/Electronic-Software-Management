package stubs;

import Interfaces.Views.ISupplierManagementView;
import Models.Item;
import Models.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class SupplierManagementViewStub implements ISupplierManagementView {

    // Inputs you can control from the test
    public String supplierNameInput = "";
    public List<Item> selectedItems = new ArrayList<>();
    public Supplier selectedSupplier = null;
    public String editSupplierName = "";

    // Captured outputs (what controller tells the view to do)
    public boolean addInputsCleared = false;
    public boolean showAddBoxCalled = false;
    public boolean showEditBoxCalled = false;

    public String lastInfoTitle = null;
    public String lastInfoMessage = null;
    public String lastErrorTitle = null;
    public String lastErrorMessage = null;

    // Captured wiring actions (so the test can "click buttons")
    public Runnable onAddSupplierAction;
    public Runnable onDeleteSupplierAction;
    public Runnable onEditSupplierAction;
    public Runnable onCancelUpdateAction;
    public Runnable onUpdateTableAction;

    // Required UI container (not used in tests)
    private final HBox page = new HBox();

    @Override
    public void setSuppliersTableItems(ObservableList<Supplier> suppliers) { }

    @Override
    public void setAvailableItems(ObservableList<Item> items) { }

    @Override
    public String getSupplierNameInput() {
        return supplierNameInput;
    }

    @Override
    public List<Item> getSelectedItemsForSupplier() {
        return selectedItems;
    }

    @Override
    public Supplier getSelectedSupplier() {
        return selectedSupplier;
    }

    @Override
    public void setEditSupplierName(String value) {
        editSupplierName = value;
    }

    @Override
    public String getEditSupplierName() {
        return editSupplierName;
    }

    @Override
    public void showAddSupplierBox() {
        showAddBoxCalled = true;
    }

    @Override
    public void showEditSupplierBox() {
        showEditBoxCalled = true;
    }

    @Override
    public void onAddSupplier(Runnable action) {
        onAddSupplierAction = action;
    }

    @Override
    public void onDeleteSupplier(Runnable action) {
        onDeleteSupplierAction = action;
    }

    @Override
    public void onEditSupplier(Runnable action) {
        onEditSupplierAction = action;
    }

    @Override
    public void onCancelUpdate(Runnable action) {
        onCancelUpdateAction = action;
    }

    @Override
    public void onUpdateTable(Runnable action) {
        onUpdateTableAction = action;
    }

    @Override
    public void showInfo(String title, String message) {
        lastInfoTitle = title;
        lastInfoMessage = message;
    }

    @Override
    public void showError(String title, String message) {
        lastErrorTitle = title;
        lastErrorMessage = message;
    }

    @Override
    public void clearAddSupplierInputs() {
        addInputsCleared = true;
    }

    @Override
    public HBox getSuppliersPage() {
        return page;
    }
}
