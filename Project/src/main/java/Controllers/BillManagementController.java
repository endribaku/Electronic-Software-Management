package Controllers;

import DAO.BillFileHandler;
import Views.BillGenerateView;
import Views.BillManagementView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import Models.*;
import javafx.scene.control.Alert;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BillManagementController {
    private BillManagementView managementView = new BillManagementView();
    private BillGenerateView generateView = new BillGenerateView();
    private BillFileHandler billFileHandler = new BillFileHandler();
    private User currentUser;

    public BillManagementController(User user) {

        this.generateView.getAddToBillButton().setOnAction(e -> onAddToBill(e));
        this.generateView.getCreateBillButton().setOnAction(e -> onGenerateBill(e));
        this.currentUser = user;
    }

    public BillManagementController() {
        this.generateView.getAddToBillButton().setOnAction(e -> onAddToBill(e));
        this.generateView.getCreateBillButton().setOnAction(e -> onGenerateBill(e));

    }

    public BillManagementView getManagementView() {
        return managementView;
    }

    public BillGenerateView getGenerateView() {
        return generateView;
    }

    public BillFileHandler getBillFileHandler() {
        return billFileHandler;
    }

    private void onAddToBill(ActionEvent actionEvent) {
        Item billItem = this.generateView.getItemListView().getSelectionModel().getSelectedItem();

        if (billItem == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No item selected");
            alert.show();
            return;
        }

        if (billItem.getQuantity() <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Item quantity must be greater than 0");
            alert.show();
            return;
        }

        ObservableList<Bill_Item> billItems = this.generateView.getBillList();

        for (Bill_Item item : billItems) {
            if (billItem.equals(item.getItem())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Item already added to bill");
                alert.show();
                return;
            }
        }

        this.generateView.getBillList().add(new Bill_Item(billItem));
        this.generateView.getItemListView().getSelectionModel().clearSelection();
    }

    private void onGenerateBill(ActionEvent actionEvent) {
       ObservableList<Bill_Item> billItems = this.generateView.getBillList();
       if (billItems.isEmpty()) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText("No items added to bill, Add items to generate bill.");
           alert.show();
           return;
       }

        //Generate bill with its info
       ArrayList<Bill_Item> billItemsList = new ArrayList<>(billItems);
       Bill newBill = new Bill();

       newBill.setDateOfSale(java.time.LocalDate.now());
       newBill.setCashier(currentUser);
       newBill.setSector(null);
       newBill.setItemsSold(billItemsList);
       newBill.setTotalAmountfromItemsSold();

       billFileHandler.saveBillToFile(newBill);


    }
}
