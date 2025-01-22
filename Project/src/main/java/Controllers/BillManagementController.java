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
    private InventoryFileHandler InventoryFileHandler = new InventoryFileHandler();
    private User currentUser;

    public BillManagementController(User user) {
        this.currentUser = user;
        this.generateView.getItemListView().setItems(InventoryFileHandler.getItemsOfUser(currentUser));
        this.generateView.getAddToBillButton().setOnAction(e -> onAddToBill());
        this.generateView.getCreateBillButton().setOnAction(e -> onGenerateBill());
    }

    public BillGenerateView getGenerateView() {
        return generateView;
    }

    public BillFileHandler getBillFileHandler() {
        return billFileHandler;
    }

    private void onAddToBill() throws BillCreationException, ItemStockException {
        int quantity = Integer.parseInt(this.generateView.getQuantityTextField().getText());
        Item item = this.generateView.getItemListView().getSelectionModel().getSelectedItem();


        if (item != null && quantity > 0) {
            if (item.getQuantity() > quantity) {
                Bill_Item billItem = new Bill_Item(item, quantity);
                ObservableList<Bill_Item> billItems = FXCollections.observableArrayList();

                for (Bill_Item i : billItems) {
                    if (billItem.equals(i)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Item already added to bill");
                        alert.show();
                    }
                }

                this.generateView.getBillList().add(billItem);
                this.generateView.getItemListView().getSelectionModel().clearSelection();
                this.generateView.getQuantityTextField().clear();
            }
            else throw new ItemStockException("Item quantity must be less than or equal to " + item.getQuantity());
        }
        else throw new BillCreationException("Please select an item and add its quantity!");
    }

    private void onGenerateBill() throws BillCreationException {
       ObservableList<Bill_Item> billItems = this.generateView.getBillList();
       if (!(billItems.isEmpty())) {

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

           this.generateView.getBillList().clear();

           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Success");
           alert.setHeaderText("Bill Generated Successfully");
           alert.show();

       }
       else throw new BillCreationException("No items added to bill, Add items to generate bill.");
    }

    //to fix bill tomorrow
    private void onBillGenerateQuantities(ObservableList<Bill_Item> billItemsList) {
        System.out.println("Size of billItemsList: " + billItemsList.size());
        ObservableList<Item> itemsInBill = FXCollections.observableArrayList();
        for(Bill_Item item: billItemsList) {
            itemsInBill.add(item.getItem());
        }

        if (billItemsList.size() != itemsInBill.size()) {
            System.err.println("Mismatch between billItemsList and itemsInBill sizes.");
            return;
        }

        int[] quantitySold = new int[billItemsList.size()];
        for(int i = 0; i < billItemsList.size(); i++) {
            quantitySold[i] = billItemsList.get(i).getQuantity();
        }
        for(int i = 0; i < itemsInBill.size(); i++) {
            itemsInBill.get(i).setQuantity(itemsInBill.get(i).getQuantity() - quantitySold[i]);
        }

        //Update item with new quantity in inventory
        for(int i = 0; i < itemsInBill.size(); i++) {
            Item item = itemsInBill.get(i);
            new InventoryFileHandler().updateItem(item.getItemID(), item.getName(),
                    item.getCategory(), item.getSupplier(), item.getPurchaseDate(),
                    item.getPurchasePrice(), item.getSellingPrice(), item.getQuantity());
        }
    }
}
