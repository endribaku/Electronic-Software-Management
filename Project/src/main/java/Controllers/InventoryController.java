package Controllers;

import DAO.CategoryFileHandler;
import DAO.InventoryFileHandler;
import DAO.ItemFileHandler;
import DAO.SuppliersFileHandler;
import Exceptions.CategoryCreationException;
import Exceptions.ItemCreationException;
import Exceptions.SectorCreationException;
import Models.*;
import Views.InventoryView;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

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


    // Controller setting the currentUser as the one who controls
    public InventoryController(User currentUser) {
        this.currentUser = currentUser;
        this.inventoryListView.getAddItemButton().setOnAction(e -> onItemAdd());
        this.inventoryListView.getAddCategoryButton().setOnAction(e -> onCategoryAdd());
        this.inventoryListView.getAddSectorButton().setOnAction(e -> {onSectorAdd();});
        this.inventoryListView.getInventoryTableView().setItems(inventoryFileHandler.getItemsOfUser(currentUser));
        this.inventoryListView.getItemCategoryListView().setItems(inventoryFileHandler.getCategoriesOfUser(currentUser));
        this.inventoryListView.getItemSupplierListView().setItems(inventoryFileHandler.getSuppliersList());
        this.inventoryListView.getItems().addAll(inventoryFileHandler.getItemsList());
        this.inventoryListView.getSectorComboBox().setItems(getSectorNamesOfUser(currentUser));

        this.inventoryListView.getOptionsComboBox().setOnAction(e -> {
            if(this.inventoryListView.getOptionsComboBox().getValue().equals("Add Item")){
                this.inventoryListView.getCreateBox().setCenter(this.inventoryListView.getAddItemPane());
            } else if(this.inventoryListView.getOptionsComboBox().getValue().equals("Add Category")){
                this.inventoryListView.getCreateBox().setCenter(this.inventoryListView.getAddCategoryPane());
            } else if(this.inventoryListView.getOptionsComboBox().getValue().equals("Add Sector")){
                this.inventoryListView.getCreateBox().setCenter(this.inventoryListView.getAddSectorPane());
            }
        });
        setEditRows();
        sendAlertforLowStock();
    }

    public InventoryView getView() {
        return inventoryListView;
    }

    public InventoryFileHandler getInventoryDAO() {
        return inventoryFileHandler;
    }

    public void setEditRows() throws ItemCreationException {
        this.inventoryListView.getItemIDColumn().setOnEditCommit(e -> {
            inventoryFileHandler.getItemsList().get(e.getTablePosition().getRow()).setItemID(e.getNewValue().toString());
            inventoryFileHandler.updateInventoryFile();
        });

        this.inventoryListView.getItemNameColumn().setOnEditCommit(e -> {
            inventoryFileHandler.getItemsList().get(e.getTablePosition().getRow()).setName(e.getNewValue());
            inventoryFileHandler.updateInventoryFile();
        });

        this.inventoryListView.getItemCategoryColumn().setOnEditCommit(e -> {
            inventoryFileHandler.getItemsList().get(e.getTablePosition().getRow()).setCategory(e.getNewValue().toString());
            inventoryFileHandler.updateInventoryFile();
        });

        this.inventoryListView.getItemSupplierColumn().setOnEditCommit(e -> {
            inventoryFileHandler.getItemsList().get(e.getTablePosition().getRow()).setSupplier(e.getNewValue().toString());
            inventoryFileHandler.updateInventoryFile();
        });

        this.inventoryListView.getItemQuantityColumn().setOnEditCommit(e -> {
            inventoryFileHandler.getItemsList().get(e.getTablePosition().getRow()).setQuantity(e.getNewValue());
            inventoryFileHandler.updateInventoryFile();
        });

        this.inventoryListView.getItemPPriceColumn().setOnEditCommit(e -> {
            inventoryFileHandler.getItemsList().get(e.getTablePosition().getRow()).setPurchasePrice(e.getNewValue());
            inventoryFileHandler.updateInventoryFile();
        });

        this.inventoryListView.getItemSPriceColumn().setOnEditCommit(e -> {
            inventoryFileHandler.getItemsList().get(e.getTablePosition().getRow()).setSellingPrice(e.getNewValue());
            inventoryFileHandler.updateInventoryFile();
        });

        this.inventoryListView.getUpdateInventoryButton().setOnAction(e -> {
            boolean updated = this.inventoryFileHandler.updateInventory(inventoryFileHandler.getInventory().get());
            if (updated) {
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Success");
                success.setHeaderText("Inventory Table Updated Successfully");
                success.show();
            } else {
                Alert fail = new Alert(Alert.AlertType.ERROR);
                fail.setTitle("Success");
                fail.setHeaderText("Inventory Table Update Error");
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
        String itemSupplier =  inventoryListView.getItemSupplierListView().getSelectionModel().getSelectedItem().toString();

        Category selectedCategory = inventoryListView.getItemCategoryListView().getSelectionModel().getSelectedItem();
        Supplier selectedSupplier = inventoryListView.getItemSupplierListView().getSelectionModel().getSelectedItem();

        try{
            if(itemName.isEmpty() || itemCategory.isEmpty() || itemQuantity == 0 || itemPPrice == 0 || itemSPrice == 0 || itemSupplier.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Input");
                alert.show();
            } else {

                inventoryFileHandler.addItem(selectedCategory, selectedSupplier, new Item(itemName, itemCategory, itemSupplier, LocalDate.now(), itemPPrice, itemSPrice, itemQuantity));
                inventoryListView.getInventoryTableView().setItems(inventoryFileHandler.getItemsList());

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

    private void onCategoryAdd() throws CategoryCreationException {
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
                inventoryFileHandler.addCategory(sectorName, new Category(categoryName, itemList, sectorName));
                //inventoryListView.getItemCategoryListView().setItems(categoryFileHandler.getAllCategories());
                inventoryListView.getItemNameField().clear();
                inventoryListView.getCategoryNameField().clear();
                inventoryListView.getItemQuantityField().clear();
                inventoryListView.getItemPPriceField().clear();
                inventoryListView.getItemSPriceField().clear();
                inventoryListView.getItemSupplierListView().setValue(null);
                inventoryListView.getOptionsComboBox().setValue("Add Category");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Category Added Successfully");
                alert.show();
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void onSectorAdd() throws SectorCreationException {
        String sectorName = inventoryListView.getSectorNameField().getText();
        ArrayList<Category> categoriesList = new ArrayList<>(inventoryListView.getSectorCategoryListView().getSelectionModel().getSelectedItems());

        if (!(sectorName.isEmpty())) {
            inventoryFileHandler.addSector(new Sector(sectorName, categoriesList));
            inventoryListView.getSectorNameField().clear();
            inventoryListView.getSectorCategoryListView().getSelectionModel().clearSelection();
            inventoryListView.getOptionsComboBox().setValue("Add Sector");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Sector Added Successfully");
            alert.show();
        }
        else
            throw new SectorCreationException("Please write Sector name!");
    }

    private ObservableList<String> getSectorNamesOfUser(User user)
    {
        ObservableList<String> sectorNames = inventoryListView.getSectors();
        ObservableList<Sector> userSectors = inventoryFileHandler.getSectorsOfUser(user);

        for(Sector s: userSectors)
        {
            sectorNames.add(s.toString());
        }

        return sectorNames;
    }

    private void sendAlertforLowStock() {
        if(currentUser.getAccessLevel() == Access.Manager || currentUser.getAccessLevel() == Access.Administrator) {
            ObservableList<Item> lowStockItems = InventoryFileHandler.checkForLowStock();
            if(lowStockItems.isEmpty()) return;
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Item Stock is Low!");
                alert.setHeaderText("Item Stock is low! Please restock these items:" + lowStockItems.toString());
                alert.show();
            }
        }
    }
}
