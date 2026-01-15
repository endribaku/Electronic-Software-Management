package Controllers;

import DAO.BillFileHandler;
import DAO.InventoryFileHandler;
import Exceptions.BillCreationException;
import Exceptions.ItemStockException;
import Views.BillGenerateView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Models.*;
import javafx.scene.control.Alert;

public class BillManagementController {
    private BillGenerateView generateView = new BillGenerateView();
    private BillFileHandler billFileHandler = new BillFileHandler();
    private InventoryFileHandler inventoryFileHandler = new InventoryFileHandler();
    private User currentUser;

    public BillManagementController(User user) {
        this.currentUser = user;
        this.generateView.getItemListView().setItems(inventoryFileHandler.getItemsOfUser(currentUser));
        this.generateView.getAddToBillButton().setOnAction(e -> onAddToBill());
        this.generateView.getCreateBillButton().setOnAction(e -> onGenerateBill());
    }

    public BillManagementController() {}

    public BillGenerateView getGenerateView() {
        return generateView;
    }



    private void onAddToBill() {

        int quantity =
                Integer.parseInt(
                        generateView.getQuantityTextField().getText()
                );

        Item item =
                generateView.getItemListView()
                        .getSelectionModel()
                        .getSelectedItem();

        Bill_Item billItem = validateAndCreateBillItem(item, quantity);

        ObservableList<Bill_Item> billItems = FXCollections.observableArrayList();

        for (Bill_Item i : billItems) {
            if (billItem.equals(i)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Item already added to bill");
                alert.show();
            }
        }

        generateView.getBillList().add(billItem);
        generateView.getItemListView().getSelectionModel().clearSelection();
        generateView.getQuantityTextField().clear();
    }

    public static Bill_Item validateAndCreateBillItem(Item item, int quantity)
            throws BillCreationException, ItemStockException {

        if (item == null || quantity <= 0) {
            throw new BillCreationException(
                    "Please select an item and add its quantity!"
            );
        }

        if (item.getQuantity() <= quantity) {
            throw new ItemStockException(
                    "Item quantity must be less than or equal to " + item.getQuantity()
            );
        }

        return new Bill_Item(item, quantity);
    }


    public void onGenerateBill() throws BillCreationException {
        ObservableList<Bill_Item> billItems = this.generateView.getBillList();

        Bill newBill = generateBill(billItems, currentUser);

        BillFileHandler.insertBill(newBill);
        billFileHandler.saveBillToFile(newBill);
        onBillGenerateQuantities(billItems);

        this.generateView.getBillList().clear();
        showSuccessAlert();
    }

    public static Bill generateBill(ObservableList<Bill_Item> billItemsList, User currentUser)
            throws BillCreationException {

        if (billItemsList == null || billItemsList.isEmpty()) {
            throw new BillCreationException(
                    "No items added to bill, Add items to generate bill."
            );
        }
        if (currentUser == null) {
            throw new BillCreationException(
                    "User must be logged in to generate bill."
            );
        }

        Bill newBill = new Bill();
        newBill.setUser(currentUser);
        newBill.setItemsSold(FXCollections.observableArrayList(billItemsList));
        newBill.setTotalAmountfromItemsSold();

        return newBill;
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Bill Generated Successfully");
        alert.show();
    }

    private void onBillGenerateQuantities(ObservableList<Bill_Item> billItemsList) {
        for (Bill_Item billItem : billItemsList) {
            Item item = billItem.getItem();
            int soldQuantity = billItem.getQuantity();
            int newQuantity = item.getQuantity() - soldQuantity;
            if (newQuantity < 0) {
                System.err.println("Error: Stock quantity cannot be negative. Item: " + item.getName());
                continue;
            }
            item.setQuantity(newQuantity);

            inventoryFileHandler.updateItem(item.getItemID(), item.getName(),
                    item.getCategory(), item.getSupplier(), item.getPurchaseDate(),
                    item.getPurchasePrice(), item.getSellingPrice(), newQuantity);
        }
    }

    public boolean canAddToBill(Item item, int quantity) {
        return item != null && quantity > 0 && item.getQuantity() > quantity;
    }

    public static boolean canGenerateBill(ObservableList<Bill_Item> billItems) {
        return billItems != null && !billItems.isEmpty();
    }
}
