package Controllers;

import DAO.InventoryFileHandler;
import Exceptions.CategoryCreationException;
import Exceptions.ItemCreationException;
import Exceptions.SectorCreationException;
import Misc.StockUpdateResult;
import Models.*;
import Views.InventoryView;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.time.LocalDate;

public class InventoryController {
    // Endri = from now on use only inventoryfilehandler for any operation (categories, sectors, items)
    private final InventoryView inventoryListView = new InventoryView();
    private final InventoryFileHandler inventoryFileHandler = new InventoryFileHandler();
    private User currentUser;
    private Item editingItem;

    //Strings for easier maintainability
    private static final String ADD_ITEM = "Add Item";
    private static final String ADD_CATEGORY = "Add Category";
    private static final String ADD_SECTOR = "Add Sector";

    private static final String SUCCESS = "Success";
    private static final String ERROR = "Error";
    private static final String DELETE_ITEM = "Delete Item";



    // Controller setting the currentUser as the one who controls
    public InventoryController(User user) {
        this.currentUser = user;

        this.inventoryListView.getAddItemButton().setOnAction(e -> {

            String itemName =
                    inventoryListView.getItemNameField().getText();

            Category selectedCategory =
                    inventoryListView.getItemCategoryListView()
                            .getSelectionModel()
                            .getSelectedItem();

            Supplier selectedSupplier =
                    inventoryListView.getItemSupplierListView()
                            .getSelectionModel()
                            .getSelectedItem();

            int itemQuantity =
                    Integer.parseInt(
                            inventoryListView.getItemQuantityField().getText()
                    );

            double itemPPrice =
                    Double.parseDouble(
                            inventoryListView.getItemPPriceField().getText()
                    );

            double itemSPrice =
                    Double.parseDouble(
                            inventoryListView.getItemSPriceField().getText()
                    );

            boolean success = onItemAdd(
                    itemName,
                    selectedCategory,
                    selectedSupplier,
                    itemQuantity,
                    itemPPrice,
                    itemSPrice
            );

            if (success) {
                clearItemInputs();
                showSuccess("Item Added Successfully");
            } else {
                showError("Invalid Input");
            }
        });

        this.inventoryListView.getAddCategoryButton().setOnAction(e -> onCategoryAdd());
        this.inventoryListView.getAddSectorButton().setOnAction(e -> onSectorAdd());

        this.inventoryListView.getEditItemButton().setOnAction(e -> onItemEdit());
        this.inventoryListView.getUpdateItemButton().setOnAction(e -> handleUpdateItem());

        this.inventoryListView.getEditCategoryButton().setOnAction(e -> onCategoryEdit());
        this.inventoryListView.getEditSectorButton().setOnAction(e -> onSectorEdit());

        this.inventoryListView.getDeleteItemButton().setOnAction(e -> onItemDelete());

        inventoryListView.getCancelUpdateItemButton()
                .setOnAction(e -> cancelEdit());
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
            if(this.inventoryListView.getOptionsComboBox().getValue().equals(ADD_ITEM)){
                this.inventoryListView.getCreateBox().setCenter(this.inventoryListView.getAddItemPane());
            } else if(this.inventoryListView.getOptionsComboBox().getValue().equals(ADD_CATEGORY)){
                this.inventoryListView.getCreateBox().setCenter(this.inventoryListView.getAddCategoryPane());
            } else if(this.inventoryListView.getOptionsComboBox().getValue().equals(ADD_SECTOR)){
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
            inventoryFileHandler.getItemsList().get(e.getTablePosition().getRow()).setItemID(e.getNewValue());
            inventoryFileHandler.updateInventoryFile();
        });

        this.inventoryListView.getItemNameColumn().setOnEditCommit(e -> {
            inventoryFileHandler.getItemsList().get(e.getTablePosition().getRow()).setName(e.getNewValue());
            inventoryFileHandler.updateInventoryFile();
        });

        this.inventoryListView.getItemCategoryColumn().setOnEditCommit(e -> {
            inventoryFileHandler.getItemsList().get(e.getTablePosition().getRow()).setCategory(e.getNewValue());
            inventoryFileHandler.updateInventoryFile();
        });

        this.inventoryListView.getItemSupplierColumn().setOnEditCommit(e -> {
            inventoryFileHandler.getItemsList().get(e.getTablePosition().getRow()).setSupplier(e.getNewValue());
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
            boolean updated = InventoryFileHandler.updateInventory(inventoryFileHandler.getInventory().get());
            if (updated) {
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle(SUCCESS);
                success.setHeaderText("Inventory Table Updated Successfully");
                success.show();
            } else {
                Alert fail = new Alert(Alert.AlertType.ERROR);
                fail.setTitle(SUCCESS);
                fail.setHeaderText("Inventory Table Update Error");
                fail.show();
            }
        });
    }

    public boolean onItemAdd(
            String itemName,
            Category category,
            Supplier supplier,
            int quantity,
            double purchasePrice,
            double sellingPrice
    ) {
        if (itemName == null || itemName.isEmpty()
                || category == null
                || supplier == null
                || !validateItemNumericFields(quantity, purchasePrice, sellingPrice)) {

            return false;
        }

        inventoryFileHandler.addItem(
                category,
                supplier,
                new Item(
                        itemName,
                        category.getName(),
                        supplier.getName(),
                        LocalDate.now(),
                        purchasePrice,
                        sellingPrice,
                        quantity
                )
        );

        return true;
    }

    private void onCategoryAdd() throws CategoryCreationException {
        String categoryName = inventoryListView.getCategoryNameField().getText();
        String sectorName = inventoryListView.getSectorComboBox().getValue();

        try{
            if(categoryName.isEmpty() || sectorName.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(ERROR);
                alert.setHeaderText("Invalid Input");
                alert.show();
            } else {
                inventoryFileHandler.addCategory(sectorName, new Category(categoryName, sectorName));
                inventoryListView.getItemNameField().clear();
                inventoryListView.getCategoryNameField().clear();
                inventoryListView.getItemQuantityField().clear();
                inventoryListView.getItemPPriceField().clear();
                inventoryListView.getItemSPriceField().clear();
                inventoryListView.getItemSupplierListView().setValue(null);
                inventoryListView.getOptionsComboBox().setValue(ADD_CATEGORY);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(SUCCESS);
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
            inventoryListView.getOptionsComboBox().setValue(ADD_SECTOR);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(SUCCESS);
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

    private boolean sendAlertforLowStock() {

        boolean hasAccess =
                currentUser.getAccessLevel() == Access.Manager
                        || currentUser.getAccessLevel() == Access.Administrator;

        if (!hasAccess) {
            return false;
        }

        ObservableList<Item> lowStockItems =
                InventoryFileHandler.checkForLowStock();

        if (lowStockItems.isEmpty()) {
            return false;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Item Stock is Low!");
        alert.setHeaderText(
                "Item Stock is low! Please restock these items:"
                        + lowStockItems.toString()
        );
        alert.show();

        return true;
    }


    private void onItemEdit() {

        inventoryListView.getCreateBox()
                .setCenter(inventoryListView.getEditItemPane());
        inventoryListView.getCreateBox()
                .getChildren()
                .remove(inventoryListView.getOptionsComboBox());

        Item selectedItem =
                inventoryListView.getInventoryTableView()
                        .getSelectionModel()
                        .getSelectedItem();

        if (selectedItem == null) {
            showError("No item selected");
            return;
        }

        inventoryListView.getItemEditNameField()
                .setText(selectedItem.getName());

        inventoryListView.getItemEditQuantityField()
                .setText(String.valueOf(selectedItem.getQuantity()));

        inventoryListView.getItemEditPPriceField()
                .setText(String.valueOf(selectedItem.getPurchasePrice()));

        inventoryListView.getItemEditSPriceField()
                .setText(String.valueOf(selectedItem.getSellingPrice()));
    }

    private void handleUpdateItem() {

        Item selectedItem =
                inventoryListView.getInventoryTableView()
                        .getSelectionModel()
                        .getSelectedItem();

        if (selectedItem == null) {
            showError("No item selected");
            return;
        }

        try {
            String itemName =
                    inventoryListView.getItemEditNameField().getText();

            Category selectedCategory =
                    inventoryListView.getEditItemCategoriesBox()
                            .getSelectionModel()
                            .getSelectedItem();

            Supplier selectedSupplier =
                    inventoryListView.getEditSupplierBox()
                            .getSelectionModel()
                            .getSelectedItem();

            double purchasePrice =
                    Double.parseDouble(
                            inventoryListView.getItemEditPPriceField().getText()
                    );

            double sellingPrice =
                    Double.parseDouble(
                            inventoryListView.getItemEditSPriceField().getText()
                    );

            int quantity =
                    Integer.parseInt(
                            inventoryListView.getItemEditQuantityField().getText()
                    );

            onItemUpdate(
                    selectedItem,
                    itemName,
                    selectedCategory,
                    selectedSupplier,
                    purchasePrice,
                    sellingPrice,
                    quantity
            );

        } catch (NumberFormatException e) {
            showError("Invalid numeric input");
        }
    }

    private void cancelEdit() {
        editingItem = null;

        inventoryListView.getCreateBox()
                .setTop(inventoryListView.getOptionsComboBox());
        inventoryListView.getCreateBox()
                .setCenter(inventoryListView.getAddItemPane());
    }


    private void onItemUpdate(
            Item item,
            String itemName,
            Category selectedCategory,
            Supplier selectedSupplier,
            double purchasePrice,
            double sellingPrice,
            int quantity
    ) {

        StockUpdateResult result = evaluateStockUpdate(quantity);

        switch (result) {

            case INVALID -> {
                showError("Negative quantity not allowed");
            }

            case OUT_OF_STOCK -> {
                inventoryFileHandler.updateItem(
                        item.getItemID(),
                        itemName,
                        selectedCategory.getName(),
                        selectedSupplier.getName(),
                        item.getPurchaseDate(),
                        purchasePrice,
                        sellingPrice,
                        quantity
                );
                showError("Item is now out of stock");
            }

            case VALID -> {
                inventoryFileHandler.updateItem(
                        item.getItemID(),
                        itemName,
                        selectedCategory.getName(),
                        selectedSupplier.getName(),
                        item.getPurchaseDate(),
                        purchasePrice,
                        sellingPrice,
                        quantity
                );
                showSuccess("Item Updated");
            }
        }

        inventoryListView.getCreateBox()
                .setTop(inventoryListView.getOptionsComboBox());
        inventoryListView.getCreateBox()
                .setCenter(inventoryListView.getAddItemPane());
    }


    private void onCategoryEdit() {
        this.inventoryListView.getCreateBox().setCenter(this.inventoryListView.getEditCategoryPane());
        this.inventoryListView.getCreateBox().setTop(this.inventoryListView.getEditCategoryListBox());

        this.inventoryListView.getEditCategoryListBox().setOnAction(e -> onCategoryEditSelect());
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

        this.inventoryListView.getEditSectorListBox().setOnAction(e -> onSectorEditSelect());
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
            alert.setTitle(DELETE_ITEM);
            alert.setHeaderText(DELETE_ITEM);
            alert.setContentText("Item deleted successfully");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(DELETE_ITEM);
            alert.setHeaderText(DELETE_ITEM);

            alert.setContentText("Error while deleting Item");
            alert.show();
        }
    }


    //helper method
    public boolean validateItemNumericFields(int quantity, double purchasePrice, double sellingPrice) {
        return quantity > 0 && purchasePrice > 0 && sellingPrice > 0;
    }

    private void clearItemInputs() {
        inventoryListView.getItemNameField().clear();
        inventoryListView.getCategoryNameField().clear();
        inventoryListView.getItemQuantityField().clear();
        inventoryListView.getItemPPriceField().clear();
        inventoryListView.getItemSPriceField().clear();
        inventoryListView.getItemSupplierListView().setValue(null);
        inventoryListView.getOptionsComboBox().setValue(ADD_ITEM);
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(SUCCESS);
        alert.setHeaderText(message);
        alert.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR);
        alert.setHeaderText(message);
        alert.show();
    }

    public StockUpdateResult evaluateStockUpdate(int quantity) {
        if (quantity < 0) {
            return StockUpdateResult.INVALID;
        } else if (quantity == 0) {
            return StockUpdateResult.OUT_OF_STOCK;
        } else {
            return StockUpdateResult.VALID;
        }
    }
}
