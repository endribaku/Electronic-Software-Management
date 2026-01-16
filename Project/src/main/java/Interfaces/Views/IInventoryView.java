package Interfaces.Views;

import Models.Category;
import Models.Item;
import Models.Supplier;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public interface IInventoryView {

    // Page
    HBox getInventoryPage();

    // Root containers
    BorderPane getCreateBox();
    VBox getAddItemPane();
    VBox getAddCategoryPane();
    VBox getAddSectorPane();
    VBox getEditItemPane();
    VBox getEditCategoryPane();
    VBox getEditSectorPane();

    // Top option selector
    ComboBox<String> getOptionsComboBox();
    ObservableList<String> getOptionsList();

    // Main table
    TableView<Item> getInventoryTableView();
    TableColumn<Item, String> getItemIDColumn();
    TableColumn<Item, String> getItemNameColumn();
    TableColumn<Item, String> getItemCategoryColumn();
    TableColumn<Item, String> getItemSupplierColumn();
    TableColumn<Item, Integer> getItemQuantityColumn();
    TableColumn<Item, Double> getItemPPriceColumn();
    TableColumn<Item, Double> getItemSPriceColumn();

    // Buttons under table
    Button getUpdateInventoryButton();
    Button getEditItemButton();
    Button getDeleteItemButton();

    // Add Item inputs
    TextField getItemNameField();
    TextField getItemQuantityField();
    TextField getItemPPriceField();
    TextField getItemSPriceField();
    ComboBox<Category> getItemCategoryListView();
    ComboBox<Supplier> getItemSupplierListView();
    Button getAddItemButton();

    // Add Category inputs
    TextField getCategoryNameField();
    ComboBox<String> getSectorComboBox();
    Button getAddCategoryButton();
    Button getEditCategoryButton();

    // Add Sector inputs
    TextField getSectorNameField();
    Button getAddSectorButton();
    Button getEditSectorButton();

    // Edit Item inputs
    TextField getItemEditNameField();
    TextField getItemEditQuantityField();
    TextField getItemEditPPriceField();
    TextField getItemEditSPriceField();
    ComboBox<Category> getEditItemCategoriesBox();
    ComboBox<Supplier> getEditSupplierBox();
    Button getUpdateItemButton();
    Button getCancelUpdateItemButton();

    // Edit Category inputs
    ComboBox<Category> getEditCategoryListBox();
    TextField getCategoryEditNameField();
    ComboBox<String> getEditCategorySectorsBox();
    Button getUpdateCategoryButton();
    Button getCancelUpdateCategoryButton();

    // Edit Sector inputs
    ComboBox<Models.Sector> getEditSectorListBox();
    TextField getSectorEditNameField();
    Button getUpdateSectorButton();
    Button getCancelUpdateSectorButton();

    // Data lists (used by controller helpers)
    ObservableList<Category> getCategories();
    ObservableList<Supplier> getSuppliers();
    ObservableList<Item> getItems();
    ObservableList<String> getSectors();

    // Alerts (moved from controller into view)
    void showInfo(String title, String header);
    void showError(String title, String header);
    void showConfirmation(String title, String content);
    void showLowStockAlert(ObservableList<Item> lowStockItems);
}

