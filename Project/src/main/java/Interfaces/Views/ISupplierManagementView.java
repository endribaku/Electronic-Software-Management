package Interfaces.Views;

import Models.Item;
import Models.Supplier;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;

import java.util.List;

public interface ISupplierManagementView {

    // ===== Data binding =====
    void setSuppliersTableItems(ObservableList<Supplier> suppliers);
    void setAvailableItems(ObservableList<Item> items);

    // ===== Inputs =====
    String getSupplierNameInput();
    List<Item> getSelectedItemsForSupplier();

    // ===== Selection =====
    Supplier getSelectedSupplier();

    // ===== Edit box inputs =====
    void setEditSupplierName(String value);
    String getEditSupplierName();

    // ===== UI state switching =====
    void showAddSupplierBox();
    void showEditSupplierBox();

    // ===== Wiring (controller passes actions) =====
    void onAddSupplier(Runnable action);
    void onDeleteSupplier(Runnable action);
    void onEditSupplier(Runnable action);
    void onCancelUpdate(Runnable action);
    void onUpdateTable(Runnable action);

    // ===== Messages =====
    void showInfo(String title, String message);
    void showError(String title, String message);

    // ===== Helpers =====
    void clearAddSupplierInputs();
    HBox getSuppliersPage();
}
