package Controllers;

import DAO.InventoryFileHandler;
import Exceptions.CategoryCreationException;
import Exceptions.ItemCreationException;
import Exceptions.SectorCreationException;
import Misc.StockUpdateResult;
import Models.*;
import Views.InventoryView;
import javafx.collections.ObservableList;

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

            int itemQuantity;
            double itemPPrice;
            double itemSPrice;

            try {
                itemQuantity =
                        Integer.parseInt(
                                inventoryListView.getItemQuantityField().getText()
                        );

                itemPPrice =
                        Double.parseDouble(
                                inventoryListView.getItemPPriceField().getText()
                        );

                itemSPrice =
                        Double.parseDouble(
                                inventoryListView.getItemSPriceField().getText()
                        );
            } catch (NumberFormatException ex) {
                inventoryListView.showError(ERROR, "Invalid numeric input");
                return;
            }

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
                inventoryListView.showInfo(SUCCESS, "Item Added Successfully");
            } else {
                inventoryListView.showError(ERROR, "Invalid Input");
            }
        });

        this.inventoryListView.getAddCategoryButton().setOnAction(e -> {
            try {
                onCategoryAdd();
            } catch (CategoryCreationException ex) {
                inventoryListView.showError(ERROR, ex.getMessage());
            }
        });

        this.inventoryListView.getAddSectorButton().setOnAction(e -> {
            try {
                onSectorAdd();
            } catch (SectorCreationException ex) {
                inventoryListView.showError(ERROR, ex.getMessage());
            }
        });

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
            boolean updated = inventoryFileHandler.updateInventory(inventoryFileHandler.getInventory().get());
            if (updated) {
                inventoryListView.showInfo(SUCCESS, "Inventory Table Updated Successfully");
            } else {
                inventoryListView.showError(ERROR, "Inventory Table Update Error");
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
                inventoryListView.showError(ERROR, "Invalid Input");
            } else {
                inventoryFileHandler.addCategory(sectorName, new Category(categoryName, sectorName));
                inventoryListView.getItemNameField().clear();
                inventoryListView.getCategoryNameField().clear();
                inventoryListView.getItemQuantityField().clear();
                inventoryListView.getItemPPriceField().clear();
                inventoryListView.getItemSPriceField().clear();
                inventoryListView.getItemSupplierListView().setValue(null);
                inventoryListView.getOptionsComboBox().setValue(ADD_CATEGORY);

                inventoryListView.showInfo(SUCCESS, "Category Added Successfully");
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

            inventoryListView.showInfo(SUCCESS, "Sector Added Successfully");
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

        ObservableList<Item> lowStockItems =
                inventoryFileHandler.checkForLowStock();

        if (!canSendLowStockAlert(currentUser, lowStockItems)) {
            return false;
        }

        inventoryListView.showLowStockAlert(lowStockItems);

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
            inventoryListView.showError(ERROR, "No item selected");
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
            inventoryListView.showError(ERROR, "No item selected");
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
            inventoryListView.showError(ERROR, "Invalid numeric input");
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
                inventoryListView.showError(ERROR, "Negative quantity not allowed");
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
                inventoryListView.showError(ERROR, "Item is now out of stock");
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
                inventoryListView.showInfo(SUCCESS, "Item Updated");
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
            inventoryListView.showConfirmation(DELETE_ITEM, "Item deleted successfully");
        } else {
            inventoryListView.showError(DELETE_ITEM, "Error while deleting Item");
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

    public static StockUpdateResult evaluateStockUpdate(int quantity) {
        if (quantity < 0) {
            return StockUpdateResult.INVALID;
        } else if (quantity == 0) {
            return StockUpdateResult.OUT_OF_STOCK;
        } else {
            return StockUpdateResult.VALID;
        }
    }

    public boolean canSendLowStockAlert(
            User user,
            ObservableList<Item> lowStockItems
    ) {

        boolean hasAccess =
                user.getAccessLevel() == Access.Manager
                        || user.getAccessLevel() == Access.Administrator;

        if (!hasAccess) {
            return false;
        }

        return !lowStockItems.isEmpty();
    }
}
