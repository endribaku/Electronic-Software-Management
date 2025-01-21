package Controllers;

import DAO.BillFileHandler;
import DAO.InventoryFileHandler;
import Exceptions.BillCreationException;
import Views.BillGenerateView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import Models.*;
import javafx.scene.control.Alert;

import java.util.ArrayList;

public class BillManagementController {
    private BillGenerateView generateView = new BillGenerateView();
    private BillFileHandler billFileHandler = new BillFileHandler();
    private InventoryFileHandler InventoryFileHandler = new InventoryFileHandler();
    private User currentUser;

    public BillManagementController(User user) {
        this.currentUser = user;
        this.generateView.getItemListView().setItems(InventoryFileHandler.getItemsOfUser(currentUser));
        this.generateView.getAddToBillButton().setOnAction(e -> onAddToBill(e));
        this.generateView.getCreateBillButton().setOnAction(e -> onGenerateBill(e));
    }

    public BillManagementController() {
        this.generateView.getAddToBillButton().setOnAction(e -> onAddToBill(e));
        this.generateView.getCreateBillButton().setOnAction(e -> onGenerateBill(e));

    }

    public BillGenerateView getGenerateView() {
        return generateView;
    }

    public BillFileHandler getBillFileHandler() {
        return billFileHandler;
    }

    private void onAddToBill(ActionEvent actionEvent) {
        int quantity = Integer.parseInt(this.generateView.getQuantityTextField().getText());
        Item item = this.generateView.getItemListView().getSelectionModel().getSelectedItem();

        try {
            if (item == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No item selected");
                alert.show();
                return;
            }

            if (quantity <= 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Item quantity must be greater than 0");
                alert.show();
                return;
            }
            if (item.getQuantity() < quantity) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Item quantity must be less than or equal to " + item.getQuantity());
            }

            Bill_Item billItem = new Bill_Item(item, quantity);
            ObservableList<Bill_Item> billItems = FXCollections.observableArrayList();

            for (Bill_Item i : billItems) {
                if (billItem.equals(i)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Item already added to bill");
                    alert.show();
                    return;
                }
            }

            this.generateView.getBillList().add(billItem);
            this.generateView.getItemListView().getSelectionModel().clearSelection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void onGenerateBill(ActionEvent actionEvent) throws BillCreationException {
       ObservableList<Bill_Item> billItems = this.generateView.getBillList();
       if (billItems.isEmpty()) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText("No items added to bill, Add items to generate bill.");
           alert.show();
           return;
       }

        //Generate bill with its info
       ObservableList<Bill_Item> billItemsList = FXCollections.observableArrayList(billItems);
       Bill newBill = new Bill();
       newBill.setUser(currentUser);
       newBill.setItemsSold(billItemsList);
       newBill.setTotalAmountfromItemsSold();

       BillFileHandler.insertBill(newBill);
       billFileHandler.saveBillToFile(newBill);

       onBillGenerateQuantities(billItemsList);
       //InventoryFileHandler.updateInventoryFile();

       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle("Success");
       alert.setHeaderText("Bill Generated Successfully");
       alert.show();
    }

    private void onBillGenerateQuantities(ObservableList<Bill_Item> billItemsList) {
        ObservableList<Item> itemsInBill = FXCollections.observableArrayList();
        for(Bill_Item item: billItemsList) {
            itemsInBill.add(item.getItem());
        }
        int[] quantitySold = new int[billItemsList.size()];
        for(int i = 0; i < billItemsList.size(); i++) {
            quantitySold[i] = billItemsList.get(i).getQuantity();
        }
        for(int i = 0; i < itemsInBill.size(); i++) {
            itemsInBill.get(i).setQuantity(itemsInBill.get(i).getQuantity() - quantitySold[i]);
        }
    }
}
