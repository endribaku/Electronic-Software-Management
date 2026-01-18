package stubs;

import Interfaces.Views.IBillGenerateView;
import Models.Bill_Item;
import Models.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;

public class BillGenerateViewStub implements IBillGenerateView {

    private final ObservableList<Bill_Item> billItems = FXCollections.observableArrayList();
    private final ObservableList<Item> availableItems = FXCollections.observableArrayList();
    private String quantityText = "";
    private Item selectedItem = null;
    public boolean errorShown = false;
    public boolean infoShown = false;
    public String lastErrorTitle = "";
    public String lastErrorMessage = "";
    public String lastInfoTitle = "";
    public String lastInfoMessage = "";
    private Runnable onAddToBillAction;
    private Runnable onCreateBillAction;

    @Override
    public HBox getBillGeneratePage() {
        return null;
    }

    @Override
    public String getQuantityText() {
        return quantityText;
    }

    public void setQuantityText(String text) {
        this.quantityText = text;
    }

    @Override
    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item item) {
        this.selectedItem = item;
    }

    @Override
    public void setAvailableItems(ObservableList<Item> items) {
        availableItems.clear();
        availableItems.addAll(items);
    }

    @Override
    public ObservableList<Bill_Item> getBillItems() {
        return billItems;
    }

    @Override
    public void addBillItem(Bill_Item item) {
        billItems.add(item);
    }

    @Override
    public void clearBillItems() {
        billItems.clear();
    }

    @Override
    public void clearSelectedItem() {
        selectedItem = null;
    }

    @Override
    public void clearQuantityInput() {
        quantityText = "";
    }

    @Override
    public void showError(String title, String message) {
        errorShown = true;
        lastErrorTitle = title;
        lastErrorMessage = message;
    }

    @Override
    public void showInfo(String title, String message) {
        infoShown = true;
        lastInfoTitle = title;
        lastInfoMessage = message;
    }

    @Override
    public void onAddToBill(Runnable action) {
        this.onAddToBillAction = action;
    }

    @Override
    public void onCreateBill(Runnable action) {
        this.onCreateBillAction = action;
    }

    public void resetFlags() {
        errorShown = false;
        infoShown = false;
        lastErrorTitle = "";
        lastErrorMessage = "";
        lastInfoTitle = "";
        lastInfoMessage = "";
    }

    public ObservableList<Item> getAvailableItems() {
        return availableItems;
    }
}