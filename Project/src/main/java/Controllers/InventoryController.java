package Controllers;

import DAO.CategoryFileHandler;
import DAO.InventoryFileHandler;
import DAO.ItemFIleHandler;
import DAO.SuppliersFileHandler;
import Models.Category;
import Models.Item;
import Models.Supplier;
import Models.User;
import Views.InventoryView;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;

public class InventoryController {

    private final InventoryView inventoryListView = new InventoryView();
    private final ItemFIleHandler itemFIleHandler = new ItemFIleHandler();
    private final CategoryFileHandler categoryFileHandler = new CategoryFileHandler();
    private final SuppliersFileHandler suppliersFileHandler = new SuppliersFileHandler();
    private final InventoryFileHandler inventoryFileHandler = new InventoryFileHandler();
    private User currentUser;


    public InventoryController() {
        this.inventoryListView.getInventoryTableView().setItems(itemFIleHandler.getAllItems());
        this.inventoryListView.getItemCategoryColumn().setCellValueFactory(new PropertyValueFactory<Item,Category>(categoryFileHandler.getAllCategories().toString()));
        this.inventoryListView.getItemSupplierColumn().setCellValueFactory(new PropertyValueFactory<Item, Supplier>(suppliersFileHandler.getAllSuppliers().toString()));
        setEditListeners();
    }

    // Controller setting the currentUser as the one who controls
    public InventoryController(User user) {
        this.currentUser = user;
        this.inventoryListView.getInventoryTableView().setItems(itemFIleHandler.getAllItems());
        this.inventoryListView.getItemCategoryColumn().setCellValueFactory(new PropertyValueFactory<Item,Category>(categoryFileHandler.getAllCategories().toString()));
        this.inventoryListView.getItemSupplierColumn().setCellValueFactory(new PropertyValueFactory<Item, Supplier>(suppliersFileHandler.getAllSuppliers().toString()));
        setEditListeners();

        this.inventoryListView.getOptionsComboBox().setOnAction(e -> {
            if(this.inventoryListView.getOptionsComboBox().getValue().equals("Add Item")){
                this.inventoryListView.getCreateBox().setCenter(this.inventoryListView.getAddItemPane());
            } else if(this.inventoryListView.getOptionsComboBox().getValue().equals("Add Category")){
                this.inventoryListView.getCreateBox().setCenter(this.inventoryListView.getAddCategoryPane());
            } else if(this.inventoryListView.getOptionsComboBox().getValue().equals("Add Sector")){
                this.inventoryListView.getCreateBox().setCenter(this.inventoryListView.getAddSectorPane());
            }
        });
    }

    public InventoryView getView() {
        return inventoryListView;
    }

    public InventoryFileHandler getInventoryDAO() {
        return inventoryFileHandler;
    }

    public void setEditListeners() {
        this.inventoryListView.getItemIDColumn().setOnEditCommit(e -> {
            itemFIleHandler.getAllItems().get(e.getTablePosition().getRow()).setName(e.getNewValue().toString());
        });

        this.inventoryListView.getItemNameColumn().setOnEditCommit(e -> {
            itemFIleHandler.getAllItems().get(e.getTablePosition().getRow()).setName(e.getNewValue());
        });

        this.inventoryListView.getItemCategoryColumn().setOnEditCommit(e -> {
            categoryFileHandler.getAllCategories().get(e.getTablePosition().getRow()).setName(e.getNewValue().toString());
        });

        this.inventoryListView.getItemSupplierColumn().setOnEditCommit(e -> {
            suppliersFileHandler.getAllSuppliers().get(e.getTablePosition().getRow()).setName(e.getNewValue().toString());
        });

        this.inventoryListView.getItemQuantityColumn().setOnEditCommit(e -> {
            itemFIleHandler.getAllItems().get(e.getTablePosition().getRow()).setName(e.getNewValue().toString());
        });

        this.inventoryListView.getItemPPriceColumn().setOnEditCommit(e -> {
            itemFIleHandler.getAllItems().get(e.getTablePosition().getRow()).setName(e.getNewValue().toString());
        });

        this.inventoryListView.getItemSPriceColumn().setOnEditCommit(e -> {
            itemFIleHandler.getAllItems().get(e.getTablePosition().getRow()).setName(e.getNewValue().toString());
        });

//        this.inventoryListView.getUpdateInventoryButton().setOnAction(e -> {
//            if (this.inventoryFileHandler.updateAll()) {
//                Alert success = new Alert(Alert.AlertType.INFORMATION);
//                success.setTitle("Success");
//                success.setHeaderText("Employee Table Updated Successfully");
//                success.show();
//            } else {
//                Alert fail = new Alert(Alert.AlertType.ERROR);
//                fail.setTitle("Success");
//                fail.setHeaderText("Employee Table Update Error");
//                fail.show();
//            }
//        });
    }

    private void onItemAdd(){
        String itemName = inventoryListView.getItemNameField().getText();
        String itemCategory = inventoryListView.getCategoryNameField().getText();
        String itemQuantity = inventoryListView.getItemQuantityField().getText();
        String itemPPrice = inventoryListView.getItemPPriceField().getText();
        String itemSPrice = inventoryListView.getItemSPriceField().getText();
        String itemSupplier =  inventoryListView.getItemSupplierListView().getValue();

        try{
            if(itemName.isEmpty() || itemCategory.isEmpty() || itemQuantity.isEmpty() || itemPPrice.isEmpty() || itemSPrice.isEmpty() || itemSupplier.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Input");
                alert.show();
            } else {
                //insert later
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void onCategoryAdd(){
        Category CategoryName = inventoryListView.getItemCategoryListView().getValue();

    }
}
