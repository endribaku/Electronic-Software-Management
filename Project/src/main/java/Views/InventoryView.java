package Views;

import Models.Category;
import Models.Item;
import Models.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class InventoryView {

    HBox inventoryPage = new HBox();
    ObservableList<String> categories = FXCollections.observableArrayList(
            "Major Domestic Appliances", "Climate & Air", "Small Domestic Appliances", "Consumer Electronics",
            "TV", "IT & Accessories", "Phones & Accessories", "Gaming & Accessories", "Kitchen Utensils",
            "Electrical Accessories", "Fitness Accessories");
    ComboBox<Category> itemCategoryListView = new ComboBox<Category>();
    ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
    ComboBox<String> itemSupplierListView = new ComboBox<String>();
    ObservableList<String> optionsList = FXCollections.observableArrayList("Add Item", "Add Category", "Add Sector");
    ComboBox<String> optionsComboBox = new ComboBox<>(optionsList);

    ObservableList<Item> items;
    TableView<Item> inventoryTableView = new TableView<>(items);

    BorderPane createBox = new BorderPane();
    VBox addItemPane = new VBox();
    VBox addCategoryPane = new VBox();
    VBox addSectorPane = new VBox();

    TextField categoryNameField = new TextField();
    Button addCategoryButton = new Button("Create Category");

    TextField sectorNameField = new TextField();
    Button addSectorButton = new Button("Create Sector");

    TextField itemNameField = new TextField();
    TextField itemQuantityField = new TextField();
    TextField itemPPriceField = new TextField();;
    TextField itemSPriceField = new TextField();
    Button addItemButton = new Button("Create Item");

    Button updateInventoryButton = new Button("Update Inventory");

    TableColumn<Item, Number> itemIDColumn = new TableColumn<>("ID");
    //employeeFullNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFullName()); => needs to change fields to SimpleProperty's
    TableColumn<Item, String> itemNameColumn = new TableColumn<>("Name");
    TableColumn<Item, Category> itemCategoryColumn = new TableColumn<>("Category");
    TableColumn<Item, Supplier> itemSupplierColumn = new TableColumn<>("Supplier");
    TableColumn<Item, Number> itemQuantityColumn = new TableColumn<>("Quantity");
    TableColumn<Item, Number> itemPPriceColumn = new TableColumn<>("Purchase Price");
    TableColumn<Item, Number> itemSPriceColumn = new TableColumn<>("Selling Price");


    public InventoryView() {

        inventoryPage.setStyle("-fx-background-color: white; -fx-padding: 10;");
        createBox.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 10px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");
        createBox.setMinWidth(400);

        //Options ComboBox
        optionsComboBox.setStyle("-fx-font: 11pt Helvetica;");
        optionsComboBox.getSelectionModel().select(0);
        optionsComboBox.setMinWidth(370);
        optionsComboBox.setMaxHeight(12);
        createBox.setTop(optionsComboBox);

        //Create Item Pane
        HBox addItemHeader = new HBox();
        Label addItemLabel = new Label("Add Item");
        addItemLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");
        addItemHeader.setSpacing(165);
        addItemHeader.getChildren().addAll(addItemLabel);
        GridPane addItemGrid = new GridPane();
        addItemGrid.setHgap(105);
        addItemGrid.setVgap(10);
        Label itemNameLabel = new Label("Item Name:");
        itemNameLabel.setStyle("-fx-font: 11pt Helvetica;");
        addItemGrid.add(itemNameLabel, 0, 0);
        itemNameField = new TextField();
        addItemGrid.add(itemNameField, 1, 0);
        Label itemCategoryLabel = new Label("Item Category:");
        itemCategoryLabel.setStyle("-fx-font: 11pt Helvetica;");
        addItemGrid.add(itemCategoryLabel, 0, 1);
        itemCategoryListView.setStyle("-fx-font: 11pt Helvetica;");
        addItemGrid.add(itemCategoryListView, 1,1);
        Label itemQuantityLabel = new Label("Item Quantity:");
        itemQuantityLabel.setStyle("-fx-font: 11pt Helvetica;");
        addItemGrid.add(itemQuantityLabel, 0, 2);
        itemQuantityField = new TextField();
        addItemGrid.add(itemQuantityField, 1, 2);
        Label itemPPriceLabel = new Label("Item Purchase Price:");
        itemPPriceLabel.setStyle("-fx-font: 11pt Helvetica;");
        addItemGrid.add(itemPPriceLabel, 0, 3);
        itemPPriceField = new TextField();
        addItemGrid.add(itemPPriceField, 1, 3);
        Label itemSPriceLabel = new Label("Item Selling Price:");
        itemSPriceLabel.setStyle("-fx-font: 11pt Helvetica;");
        addItemGrid.add(itemSPriceLabel, 0, 4);
        itemSPriceField = new TextField();
        addItemGrid.add(itemSPriceField, 1, 4);
        Label itemSupplierLabel = new Label("Item Supplier:");
        itemSupplierLabel.setStyle("-fx-font: 11pt Helvetica;");
        addItemGrid.add(itemSupplierLabel, 0, 5);
        itemSupplierListView.setStyle("-fx-font: 11pt Helvetica;");
        addItemGrid.add(itemSupplierListView, 1, 5);
        addItemButton.setStyle("-fx-font: 11pt Helvetica;");
        GridPane.setHalignment(addItemButton, HPos.RIGHT);
        addItemGrid.add(addItemButton, 1, 6);
        addItemPane.getChildren().addAll(addItemHeader, addItemGrid);
        addItemPane.setSpacing(10);
        addItemPane.setPadding(new Insets(10));
        createBox.setCenter(addItemPane);


        //Create Category Pane
        HBox addCategoryHeader = new HBox();
        Label addCategoryLabel = new Label("Add Category");
        addCategoryLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");
        addCategoryHeader.setSpacing(160);
        addCategoryHeader.getChildren().addAll(addCategoryLabel);
        GridPane addCategoryGrid = new GridPane();
        addCategoryGrid.setHgap(110);
        addCategoryGrid.setVgap(10);
        Label categoryNameLabel = new Label("Category Name:");
        categoryNameLabel.setStyle("-fx-font: 11pt Helvetica;");
        addCategoryGrid.add(categoryNameLabel, 0, 0);
        addCategoryGrid.add(categoryNameField, 1, 0);
        addCategoryButton.setStyle("-fx-font: 11pt Helvetica;");
        GridPane.setHalignment(addCategoryButton, HPos.RIGHT);
        addCategoryGrid.add(addCategoryButton, 1, 1);
        addCategoryPane.getChildren().addAll(addCategoryHeader, addCategoryGrid);
        addCategoryPane.setSpacing(10);
        addCategoryPane.setPadding(new Insets(10));

        //Create Supplier Pane
        HBox addSectorHeader = new HBox();
        Label addSectorLabel = new Label("Add Sector");
        addSectorLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");
        addSectorHeader.setSpacing(160);
        addSectorHeader.getChildren().addAll(addSectorLabel);
        GridPane addSectorGrid = new GridPane();
        addSectorGrid.setHgap(110);
        addSectorGrid.setVgap(10);
        Label sectorNameLabel = new Label("Sector Name:");
        sectorNameLabel.setStyle("-fx-font: 11pt Helvetica;");
        addSectorGrid.add(sectorNameLabel, 0, 0);
        addSectorGrid.add(sectorNameField, 1, 0);
        addSectorButton.setStyle("-fx-font: 11pt Helvetica;");
        GridPane.setHalignment(addSectorButton, HPos.RIGHT);
        addSectorGrid.add(addSectorButton, 1, 1);
        addSectorPane.getChildren().addAll(addSectorHeader, addSectorGrid);
        addSectorPane.setSpacing(10);
        addSectorPane.setPadding(new Insets(10));

        //Display Employee's List
        VBox inventoryListBox = new VBox();
        Label inventoryListLabel = new Label("Inventory List");
        inventoryListLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");
        inventoryListBox.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 20px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");
        inventoryListBox.setSpacing(10);

        inventoryTableView.getColumns().addAll(itemIDColumn, itemNameColumn, itemCategoryColumn, itemSupplierColumn, itemQuantityColumn, itemPPriceColumn, itemSPriceColumn);
        inventoryTableView.setPrefWidth(1000);
        updateInventoryButton.setStyle("-fx-font: 11pt Helvetica;");
        inventoryListBox.getChildren().addAll(inventoryListLabel, inventoryTableView, updateInventoryButton);

        inventoryPage.getChildren().addAll(createBox, inventoryListBox);
        inventoryPage.setSpacing(10);
    }

    public TableColumn<Item, Number> getItemIDColumn() {
        return itemIDColumn;
    }

    public TableColumn<Item, String> getItemNameColumn() {
        return itemNameColumn;
    }

    public TableColumn<Item, Category> getItemCategoryColumn() {
        return itemCategoryColumn;
    }

    public TableColumn<Item, Supplier> getItemSupplierColumn() {
        return itemSupplierColumn;
    }

    public TableColumn<Item, Number> getItemQuantityColumn() {
        return itemQuantityColumn;
    }

    public TableColumn<Item, Number> getItemPPriceColumn() {
        return itemPPriceColumn;
    }

    public TableColumn<Item, Number> getItemSPriceColumn() {
        return itemSPriceColumn;
    }

    public HBox getInventoryPage() {
        return inventoryPage;
    }

    public ObservableList<String> getCategories() {
        return categories;
    }

    public ComboBox<Category> getItemCategoryListView() {
        return itemCategoryListView;
    }

    public ObservableList<Supplier> getSuppliers() {
        return suppliers;
    }

    public ComboBox<String> getItemSupplierListView() {
        return itemSupplierListView;
    }

    public ObservableList<Item> getItems() {
        return items;
    }

    public TableView<Item> getInventoryTableView() {
        return inventoryTableView;
    }

    public TextField getCategoryNameField() {
        return categoryNameField;
    }

    public TextField getItemNameField() {
        return itemNameField;
    }

    public TextField getItemQuantityField() {
        return itemQuantityField;
    }

    public TextField getItemPPriceField() {
        return itemPPriceField;
    }

    public TextField getItemSPriceField() {
        return itemSPriceField;
    }

    public Button getAddItemButton() {
        return addItemButton;
    }

    public Button getUpdateInventoryButton() {
        return updateInventoryButton;
    }

    public void setInventoryPage(HBox inventoryPage) {
        this.inventoryPage = inventoryPage;
    }

    public void setCategories(ObservableList<String> categories) {
        this.categories = categories;
    }

    public ObservableList<String> getOptionsList() {
        return optionsList;
    }

    public ComboBox<String> getOptionsComboBox() {
        return optionsComboBox;
    }

    public BorderPane getCreateBox() {
        return createBox;
    }

    public VBox getAddItemPane() {
        return addItemPane;
    }

    public VBox getAddCategoryPane() {
        return addCategoryPane;
    }

    public VBox getAddSectorPane() {
        return addSectorPane;
    }

    public Button getAddCategoryButton() {
        return addCategoryButton;
    }

    public Button getAddSectorButton() {
        return addSectorButton;
    }
}
