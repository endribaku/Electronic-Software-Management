package Controllers;

import DAO.BillFileHandler;
import DAO.InventoryFileHandler;
import Exceptions.BillCreationException;
import Exceptions.ItemStockException;
import Interfaces.DAO.IBillFileHandler;
import Interfaces.DAO.IInventoryFileHandler;
import Interfaces.Views.IBillGenerateView;
import Models.Bill;
import Models.Bill_Item;
import Models.Item;
import Models.User;
import Views.BillGenerateView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BillManagementController {


    private IBillGenerateView generateView = new BillGenerateView();
    private IBillFileHandler billFileHandler = new BillFileHandler();
    private IInventoryFileHandler inventoryFileHandler = new InventoryFileHandler();
    private User currentUser;

    // ✅ same style as EmployeeManagementController: set everything up inside constructor
    public BillManagementController(User user) {
        this.currentUser = user;
        initialize();
    }

    // ✅ test constructor (inject mocks)
    public BillManagementController(
            User user,
            IBillGenerateView generateView,
            IBillFileHandler billFileHandler,
            IInventoryFileHandler inventoryFileHandler
    ) {
        this.currentUser = user;
        this.generateView = generateView;
        this.billFileHandler = billFileHandler;
        this.inventoryFileHandler = inventoryFileHandler;
        initialize();
    }

    public BillManagementController(){}

    public IBillGenerateView getGenerateView() {
        return generateView;
    }

    private void initialize() {
        // Load available items
        generateView.setAvailableItems(
                inventoryFileHandler.getItemsOfUser(currentUser)
        );

        // Hook actions
        generateView.onAddToBill(this::onAddToBill);

        generateView.onCreateBill(() -> {
            try {
                onGenerateBill();
            } catch (BillCreationException billCreationError) {
                generateView.showError("Error", billCreationError.getMessage());
            }
        });
    }

    private void onAddToBill() {
        try {
            int quantity = parseQuantity(generateView.getQuantityText());
            Item item = generateView.getSelectedItem();

            Bill_Item billItem = validateAndCreateBillItem(item, quantity);

            if (generateView.getBillItems().contains(billItem)) {
                generateView.showError("Error", "Item already added to bill");
                return;
            }

            generateView.addBillItem(billItem);
            generateView.clearSelectedItem();
            generateView.clearQuantityInput();

        } catch (NumberFormatException numberFormatError) {
            generateView.showError("Error", "Quantity must be a valid number.");
        } catch (BillCreationException | ItemStockException validationError) {
            generateView.showError("Error", validationError.getMessage());
        }
    }

    private int parseQuantity(String quantityText) {
        if (quantityText == null) {
            throw new NumberFormatException("Quantity is null");
        }
        return Integer.parseInt(quantityText.trim());
    }

    public void onGenerateBill() throws BillCreationException {
        ObservableList<Bill_Item> billItems = generateView.getBillItems();

        Bill newBill = generateBill(billItems, currentUser);

        billFileHandler.insertBill(newBill);
        billFileHandler.saveBillToFile(newBill);

        onBillGenerateQuantities(billItems);

        generateView.clearBillItems();
        generateView.showInfo("Success", "Bill Generated Successfully");
    }

    public static Bill_Item validateAndCreateBillItem(Item item, int quantity)
            throws BillCreationException, ItemStockException {

        if (item == null || quantity <= 0) {
            throw new BillCreationException("Please select an item and add its quantity!");
        }

        // ✅ allow equal quantity; invalid only if requested > stock
        if (quantity > item.getQuantity()) {
            throw new ItemStockException(
                    "Item quantity must be less than or equal to " + item.getQuantity()
            );
        }

        return new Bill_Item(item, quantity);
    }

    public static Bill generateBill(ObservableList<Bill_Item> billItemsList, User currentUser)
            throws BillCreationException {

        if (billItemsList == null || billItemsList.isEmpty()) {
            throw new BillCreationException("No items added to bill, Add items to generate bill.");
        }

        if (currentUser == null) {
            throw new BillCreationException("User must be logged in to generate bill.");
        }

        Bill newBill = new Bill();
        newBill.setUser(currentUser);
        newBill.setItemsSold(FXCollections.observableArrayList(billItemsList));
        newBill.setTotalAmountfromItemsSold();

        return newBill;
    }

    private void onBillGenerateQuantities(ObservableList<Bill_Item> billItemsList) {
        for (Bill_Item billItem : billItemsList) {
            Item item = billItem.getItem();
            int soldQuantity = billItem.getQuantity();
            int newQuantity = item.getQuantity() - soldQuantity;

            if (newQuantity < 0) {
                continue;
            }

            item.setQuantity(newQuantity);

            inventoryFileHandler.updateItem(
                    item.getItemID(),
                    item.getName(),
                    item.getCategory(),
                    item.getSupplier(),
                    item.getPurchaseDate(),
                    item.getPurchasePrice(),
                    item.getSellingPrice(),
                    newQuantity
            );
        }
    }

    public boolean canAddToBill(Item item, int quantity) {
        return item != null && quantity > 0 && quantity <= item.getQuantity();
    }

    public static boolean canGenerateBill(ObservableList<Bill_Item> billItems) {
        return billItems != null && !billItems.isEmpty();
    }
}
