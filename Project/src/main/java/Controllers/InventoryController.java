package Controllers;

import DAO.CategoryFileHandler;
import DAO.InventoryFileHandler;
import DAO.ItemFileHandler;
import DAO.SuppliersFileHandler;
import Exceptions.CategoryCreationException;
import Exceptions.ItemCreationException;
import Exceptions.ItemStockException;
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
    private final InventoryFileHandler inventoryFileHandler = new InventoryFileHandler();
    private User currentUser;


    // Controller setting the currentUser as the one who controls
    public InventoryController(User currentUser) {
        this.currentUser = currentUser;

        this.inventoryListView.getAddItemButton().setOnAction(e -> onItemAdd());
        this.inventoryListView.getAddCategoryButton().setOnAction(e -> onCategoryAdd());
        this.inventoryListView.getAddSectorButton().setOnAction(e -> onSectorAdd());

        this.inventoryListView.getEditItemButton().setOnAction(e -> onItemEdit());
        this.inventoryListView.getEditCategoryButton().setOnAction(e -> onCategoryEdit());
        this.inventoryListView.getEditSectorButton().setOnAction(e -> onSectorEdit());

        this.inventoryListView.getDeleteItemButton().setOnAction(e -> onItemDelete());

        this.inventoryListView.getCancelUpdateItemButton().setOnAction(e -> {
            this.inventoryListView.getCreateBox().setTop(this.inventoryListView.getOptionsComboBox());
            this.inventoryListView.getCreateBox().setCenter(this.inventoryListView.getAddItemPane());
        });
        this.inventoryListView.getCancelUpdateCategoryButton().setOnAction(e -> {
            this.inventoryListView.getCreateBox().setTop(this.inventoryListView.getOptionsComboBox());
            this.inventoryListView.getCreateBox().setCenter(this.inventoryListView.getAddCategoryPane());
        });
        this.inventoryListView.getCancelUpdateSectorButton().setOnAction(e -> {
            this.inventoryListView.getCreateBox().setTop(this.inventoryListView.getOptionsComboBox());
            this.inventoryListView.getCreateBox().setCenter(this.inventoryListView.getAddSectorPane());
        });

        this.inventoryListView.getInventoryTableView().setItems(inventoryFileHandler.getItemsOfUser(currentUser));

        this.inventoryListView.getItemCategoryListView().setItems(inventoryFileHandler.getCategoriesOfUser(currentUser));
        this.inventoryListView.getEditItemCategoriesBox().setItems(inventoryFileHandler.getCategoriesOfUser(currentUser));
        this.inventoryListView.getEditCategoryListBox().setItems(inventoryFileHandler.getCategoriesOfUser(currentUser));

        this.inventoryListView.getItemSupplierListView().setItems(inventoryFileHandler.getSuppliersList());
        this.inventoryListView.getEditSupplierBox().setItems(inventoryFileHandler.getSuppliersList());

        this.inventoryListView.getSectorComboBox().setItems(getSectorNamesOfUser(currentUser));
        this.inventoryListView.getEditCategorySectorsBox().setItems(getSectorNamesOfUser(currentUser));

        this.inventoryListView.getEditSectorListBox().setItems(inventoryFileHandler.getSectorsOfUser(currentUser));

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
            if(itemName.isEmpty() || itemCategory.isEmpty() || itemQuantity <= 0 || itemPPrice == 0 || itemSPrice == 0 || itemSupplier.isEmpty()){
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

        try{
            if(categoryName.isEmpty() || sectorName.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Input");
                alert.show();
            } else {
                inventoryFileHandler.addCategory(sectorName, new Category(categoryName, sectorName));
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

        if (!(sectorName.isEmpty())) {
            inventoryFileHandler.addSector(new Sector(sectorName));
            inventoryListView.getSectorNameField().clear();
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

    private void onItemEdit() {
        this.inventoryListView.getCreateBox().setCenter(this.inventoryListView.getEditItemPane());
        this.inventoryListView.getCreateBox().getChildren().remove(this.inventoryListView.getOptionsComboBox());

        Item selectedItem = this.inventoryListView.getInventoryTableView().getSelectionModel().getSelectedItem();

        this.inventoryListView.getItemEditNameField().setText(selectedItem.getName());
        this.inventoryListView.getItemEditQuantityField().setText(selectedItem.getQuantity() + "");
        this.inventoryListView.getItemEditPPriceField().setText(selectedItem.getPurchasePrice() + "");
        this.inventoryListView.getItemEditSPriceField().setText(selectedItem.getSellingPrice() + "");

        this.inventoryListView.getUpdateItemButton().setOnAction(e -> {
            onItemUpdate(selectedItem);
        });
    }

    private void onItemUpdate(Item item) {

        String itemName = this.inventoryListView.getItemEditNameField().getText();
        String category = this.inventoryListView.getEditItemCategoriesBox().getValue().toString();
        String supplier = this.inventoryListView.getEditSupplierBox().getValue().toString();
        double purchasePrice = Double.parseDouble(this.inventoryListView.getItemEditPPriceField().getText());
        double sellingPrice = Double.parseDouble(this.inventoryListView.getItemEditSPriceField().getText());
        int quantity = Integer.parseInt(this.inventoryListView.getItemEditQuantityField().getText());

        if(quantity < 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid stock update");
            alert.setHeaderText("Negative quantity not allowed");
            alert.show();
        } else if(quantity == 0)
        {
            inventoryFileHandler.updateItem(item.getItemID(), itemName, category, supplier, item.getPurchaseDate(), purchasePrice, sellingPrice, quantity);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Item is now out of stock");
            alert.setHeaderText("Item is now out of stock");
            alert.show();

        } else
        {
            inventoryFileHandler.updateItem(item.getItemID(), itemName, category, supplier, item.getPurchaseDate(), purchasePrice, sellingPrice, quantity);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Item Updated");
            alert.setHeaderText("Item is Updated");
            alert.show();
        }




        this.inventoryListView.getCreateBox().setTop(this.inventoryListView.getOptionsComboBox());
        this.inventoryListView.getCreateBox().setCenter(this.inventoryListView.getAddItemPane());

    }

    private void onCategoryEdit() {
        this.inventoryListView.getCreateBox().setCenter(this.inventoryListView.getEditCategoryPane());
        this.inventoryListView.getCreateBox().setTop(this.inventoryListView.getEditCategoryListBox());

        this.inventoryListView.getEditCategoryListBox().setOnAction(e -> {
            onCategoryEditSelect();
        });
    }

    private void onCategoryEditSelect() {

        Category selectedCategory = this.inventoryListView.getEditCategoryListBox().getValue();
        this.inventoryListView.getCategoryEditNameField().setText(selectedCategory.getName());

        this.inventoryListView.getUpdateCategoryButton().setOnAction(e -> onCategoryUpdate(selectedCategory));
    }

    private void onCategoryUpdate(Category selectedCategory) {

        String name = this.inventoryListView.getCategoryEditNameField().getText();
        String sector = this.inventoryListView.getEditCategorySectorsBox().getSelectionModel().getSelectedItem();

        inventoryFileHandler.updateCategory(selectedCategory, name, sector);

        this.inventoryListView.getCreateBox().setTop(this.inventoryListView.getOptionsComboBox());
        this.inventoryListView.getCreateBox().setCenter(this.inventoryListView.getAddCategoryPane());
    }

    private void onSectorEdit() {
        this.inventoryListView.getCreateBox().setCenter(this.inventoryListView.getEditSectorPane());
        this.inventoryListView.getCreateBox().setTop(this.inventoryListView.getEditSectorListBox());

        this.inventoryListView.getEditSectorListBox().setOnAction(e -> {
            onSectorEditSelect();
        });
    }

    private void onSectorEditSelect() {
        Sector selectedSector = this.inventoryListView.getEditSectorListBox().getValue();
        this.inventoryListView.getSectorEditNameField().setText(selectedSector.getSectorName());

        this.inventoryListView.getUpdateSectorButton().setOnAction(e -> onSectorUpdate(selectedSector));
    }

    private void onSectorUpdate(Sector sector) {
        String name = this.inventoryListView.getSectorEditNameField().getText();

        inventoryFileHandler.updateSector(sector, name);

        this.inventoryListView.getCreateBox().setTop(this.inventoryListView.getOptionsComboBox());
        this.inventoryListView.getCreateBox().setCenter(this.inventoryListView.getAddSectorPane());
    }

    private void onItemDelete() {
        Item selectedItem = this.inventoryListView.getInventoryTableView().getSelectionModel().getSelectedItem();

        if(inventoryFileHandler.deleteItem(selectedItem))
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Item");
            alert.setHeaderText("Delete Item");
            alert.setContentText("Item deleted successfully");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Item");
            alert.setHeaderText("Delete Item");

            alert.setContentText("Error while deleting Item");
            alert.show();
        }
    }
}
