package stubs;

import Interfaces.Views.IInventoryView;
import Models.*;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class InventoryViewStub implements IInventoryView {

    public boolean errorShown = false;

    @Override public HBox getInventoryPage() { return null; }

    @Override public BorderPane getCreateBox() { return null; }
    @Override public VBox getAddItemPane() { return null; }
    @Override public VBox getAddCategoryPane() { return null; }
    @Override public VBox getAddSectorPane() { return null; }
    @Override public VBox getEditItemPane() { return null; }
    @Override public VBox getEditCategoryPane() { return null; }
    @Override public VBox getEditSectorPane() { return null; }

    @Override public ComboBox<String> getOptionsComboBox() { return null; }
    @Override public ObservableList<String> getOptionsList() { return null; }

    @Override public TableView<Item> getInventoryTableView() { return null; }

    @Override public TableColumn<Item, String> getItemIDColumn() { return null; }
    @Override public TableColumn<Item, String> getItemNameColumn() { return null; }
    @Override public TableColumn<Item, String> getItemCategoryColumn() { return null; }
    @Override public TableColumn<Item, String> getItemSupplierColumn() { return null; }
    @Override public TableColumn<Item, Integer> getItemQuantityColumn() { return null; }
    @Override public TableColumn<Item, Double> getItemPPriceColumn() { return null; }
    @Override public TableColumn<Item, Double> getItemSPriceColumn() { return null; }

    @Override public Button getUpdateInventoryButton() { return null; }
    @Override public Button getEditItemButton() { return null; }
    @Override public Button getDeleteItemButton() { return null; }

    @Override public TextField getItemNameField() { return null; }
    @Override public TextField getItemQuantityField() { return null; }
    @Override public TextField getItemPPriceField() { return null; }
    @Override public TextField getItemSPriceField() { return null; }

    @Override public ComboBox<Category> getItemCategoryListView() { return null; }
    @Override public ComboBox<Supplier> getItemSupplierListView() { return null; }
    @Override public Button getAddItemButton() { return null; }

    @Override public TextField getCategoryNameField() { return null; }
    @Override public ComboBox<String> getSectorComboBox() { return null; }
    @Override public Button getAddCategoryButton() { return null; }
    @Override public Button getEditCategoryButton() { return null; }

    @Override public TextField getSectorNameField() { return null; }
    @Override public Button getAddSectorButton() { return null; }
    @Override public Button getEditSectorButton() { return null; }

    @Override public TextField getItemEditNameField() { return null; }
    @Override public TextField getItemEditQuantityField() { return null; }
    @Override public TextField getItemEditPPriceField() { return null; }
    @Override public TextField getItemEditSPriceField() { return null; }
    @Override public ComboBox<Category> getEditItemCategoriesBox() { return null; }
    @Override public ComboBox<Supplier> getEditSupplierBox() { return null; }
    @Override public Button getUpdateItemButton() { return null; }
    @Override public Button getCancelUpdateItemButton() { return null; }

    @Override public ComboBox<Category> getEditCategoryListBox() { return null; }
    @Override public TextField getCategoryEditNameField() { return null; }
    @Override public ComboBox<String> getEditCategorySectorsBox() { return null; }
    @Override public Button getUpdateCategoryButton() { return null; }
    @Override public Button getCancelUpdateCategoryButton() { return null; }

    @Override public ComboBox<Sector> getEditSectorListBox() { return null; }
    @Override public TextField getSectorEditNameField() { return null; }
    @Override public Button getUpdateSectorButton() { return null; }
    @Override public Button getCancelUpdateSectorButton() { return null; }

    @Override public ObservableList<Category> getCategories() { return null; }
    @Override public ObservableList<Supplier> getSuppliers() { return null; }
    @Override public ObservableList<Item> getItems() { return null; }
    @Override public ObservableList<String> getSectors() { return null; }


    /* ==================== IMPORTANT PART ==================== */

    @Override
    public void showError(String title, String header) {
        errorShown = true;
    }

    @Override public void showInfo(String t, String h) {}
    @Override public void showConfirmation(String t, String c) {}
    @Override public void showLowStockAlert(ObservableList<Item> items) {}
}
