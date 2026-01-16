package Interfaces.Views;

import Models.Bill_Item;
import Models.Item;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;

public interface IBillGenerateView {

    // root node used by UserController
    HBox getBillGeneratePage();

    // inputs
    String getQuantityText();
    Item getSelectedItem();

    // lists
    void setAvailableItems(ObservableList<Item> items);
    ObservableList<Bill_Item> getBillItems();
    void addBillItem(Bill_Item item);
    void clearBillItems();

    // ui cleanup
    void clearSelectedItem();
    void clearQuantityInput();

    // feedback
    void showError(String title, String message);
    void showInfo(String title, String message);

    // hooks
    void onAddToBill(Runnable action);
    void onCreateBill(Runnable action);
}
