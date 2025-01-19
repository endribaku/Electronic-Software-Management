package Controllers;

import DAO.CategoryFileHandler;
import DAO.InventoryFileHandler;
import DAO.ItemFileHandler;
import DAO.SuppliersFileHandler;
import Models.*;
import Views.InventoryView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.EOFException;
import java.time.LocalDate;
import java.util.ArrayList;

public class InventoryController {
    // Endri = from now on use only inventoryfilehandler for any operation (categories, sectors, items)
    private final InventoryView inventoryListView = new InventoryView();
    private final ItemFileHandler itemFileHandler = new ItemFileHandler();
    private final CategoryFileHandler categoryFileHandler = new CategoryFileHandler();
    private final SuppliersFileHandler suppliersFileHandler = new SuppliersFileHandler();
    private final InventoryFileHandler inventoryFileHandler = new InventoryFileHandler();
    private User currentUser;


    public InventoryController() {
        this.inventoryListView.getInventoryTableView().setItems(itemFileHandler.getAllItems());
        setEditListeners();
    }

    // Controller setting the currentUser as the one who controls
    public InventoryController(User user) {
        this.currentUser = user;
        this.inventoryListView.getAddItemButton().setOnAction(e -> onItemAdd());
        this.inventoryListView.getAddCategoryButton().setOnAction(e -> onCategoryAdd());
        this.inventoryListView.getInventoryTableView().setItems(InventoryFileHandler.getItemsList());
//        ObservableList<Category> categoriesList = FXCollections.observableArrayList(categoryFileHandler.getAllCategories());
//        ObservableList<String> categoriesNamesList = FXCollections.observableArrayList();
//        categoriesList.forEach(category -> categoriesNamesList.add(category.getName()));
        this.inventoryListView.getItemCategoryListView().setItems(InventoryFileHandler.getCategoriesList());
        this.inventoryListView.getItems().addAll(itemFileHandler.getAllItems());

        this.inventoryListView.getOptionsComboBox().setOnAction(e -> {
            if(this.inventoryListView.getOptionsComboBox().getValue().equals("Add Item")){
                this.inventoryListView.getCreateBox().setCenter(this.inventoryListView.getAddItemPane());
            } else if(this.inventoryListView.getOptionsComboBox().getValue().equals("Add Category")){
                this.inventoryListView.getCreateBox().setCenter(this.inventoryListView.getAddCategoryPane());
            } else if(this.inventoryListView.getOptionsComboBox().getValue().equals("Add Sector")){
                this.inventoryListView.getCreateBox().setCenter(this.inventoryListView.getAddSectorPane());
            }
        });
        setEditListeners();
    }

    public InventoryView getView() {
        return inventoryListView;
    }

    public InventoryFileHandler getInventoryDAO() {
        return inventoryFileHandler;
    }

    public void setEditListeners() {
        this.inventoryListView.getItemIDColumn().setOnEditCommit(e -> {
            InventoryFileHandler.getItemsList().get(e.getTablePosition().getRow()).setItemID(e.getNewValue().toString());
        });

        this.inventoryListView.getItemNameColumn().setOnEditCommit(e -> {
            InventoryFileHandler.getItemsList().get(e.getTablePosition().getRow()).setName(e.getNewValue());
        });

        this.inventoryListView.getItemCategoryColumn().setOnEditCommit(e -> {
            InventoryFileHandler.getItemsList().get(e.getTablePosition().getRow()).setCategory(e.getNewValue().toString());
        });

        this.inventoryListView.getItemSupplierColumn().setOnEditCommit(e -> {
            InventoryFileHandler.getItemsList().get(e.getTablePosition().getRow()).setSupplier(e.getNewValue().toString());
        });

        this.inventoryListView.getItemQuantityColumn().setOnEditCommit(e -> {
            InventoryFileHandler.getItemsList().get(e.getTablePosition().getRow()).setQuantity((int) e.getNewValue());
        });

        this.inventoryListView.getItemPPriceColumn().setOnEditCommit(e -> {
            itemFileHandler.getAllItems().get(e.getTablePosition().getRow()).setPurchasePrice((double) e.getNewValue());
        });

        this.inventoryListView.getItemSPriceColumn().setOnEditCommit(e -> {
            itemFileHandler.getAllItems().get(e.getTablePosition().getRow()).setSellingPrice((double) e.getNewValue());
        });

        this.inventoryListView.getUpdateInventoryButton().setOnAction(e -> {
            if (this.inventoryFileHandler.updateAll()) {
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Success");
                success.setHeaderText("Employee Table Updated Successfully");
                success.show();
            } else {
                Alert fail = new Alert(Alert.AlertType.ERROR);
                fail.setTitle("Success");
                fail.setHeaderText("Employee Table Update Error");
                fail.show();
            }
        });
    }

    private void onItemAdd(){
        String itemName = inventoryListView.getItemNameField().getText();
        String itemCategory = inventoryListView.getItemCategoryListView().getValue().toString();
        int itemQuantity = Integer.parseInt(inventoryListView.getItemQuantityField().getText());
        double itemPPrice = Double.parseDouble(inventoryListView.getItemPPriceField().getText());
        double itemSPrice = Double.parseDouble(inventoryListView.getItemSPriceField().getText());
        String itemSupplier =  inventoryListView.getItemSupplierListView().getValue().toString();

        Category selectedCategory = inventoryListView.getItemCategoryListView().getSelectionModel().getSelectedItem();

        try{
            if(itemName.isEmpty() || itemCategory.isEmpty() || itemQuantity == 0 || itemPPrice == 0 || itemSPrice == 0 || itemSupplier.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Input");
                alert.show();
            } else {

                InventoryFileHandler.addItem(selectedCategory, new Item(itemName, itemCategory, itemSupplier, LocalDate.now(), itemPPrice, itemSPrice, itemQuantity));
                inventoryListView.getInventoryTableView().setItems(InventoryFileHandler.getItemsList());

                inventoryListView.getItemNameField().clear();
                inventoryListView.getCategoryNameField().clear();
                inventoryListView.getItemQuantityField().clear();
                inventoryListView.getItemPPriceField().clear();
                inventoryListView.getItemSPriceField().clear();
                inventoryListView.getItemSupplierListView().setValue(null);
                inventoryListView.getOptionsComboBox().setValue("Add Item");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Item Added Successfully");
                alert.show();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void onCategoryAdd(){
        String categoryName = inventoryListView.getCategoryNameField().getText();
        String sectorName = inventoryListView.getSectorComboBox().getValue().toString();
        ArrayList<Item> itemList = new ArrayList<>(inventoryListView.getItemListBox().getSelectionModel().getSelectedItems());


        try{
            if(categoryName.isEmpty() || sectorName.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Input");
                alert.show();
            } else {
                InventoryFileHandler.addCategory(sectorName, new Category(categoryName, itemList, sectorName));
                //inventoryListView.getItemCategoryListView().setItems(categoryFileHandler.getAllCategories());
                inventoryListView.getItemNameField().clear();
                inventoryListView.getCategoryNameField().clear();
                inventoryListView.getItemQuantityField().clear();
                inventoryListView.getItemPPriceField().clear();
                inventoryListView.getItemSPriceField().clear();
                inventoryListView.getItemSupplierListView().setValue(null);
                inventoryListView.getOptionsComboBox().setValue("Add Item");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Item Added Successfully");
                alert.show();
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
