package Views;

import Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class InventoryView {

    HBox inventoryPage = new HBox();

    ObservableList<Category> categories = FXCollections.observableArrayList();
    ComboBox<Category> itemCategoryListView = new ComboBox<Category>(categories);
    ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
    ComboBox<Supplier> itemSupplierListView = new ComboBox<Supplier>(suppliers);
    ObservableList<String> optionsList = FXCollections.observableArrayList("Add Item", "Add Category", "Add Sector");
    ComboBox<String> optionsComboBox = new ComboBox<>(optionsList);

    ObservableList<Item> items = FXCollections.observableArrayList();

    ObservableList<String> sectors = FXCollections.observableArrayList();
    ComboBox<String> sectorComboBox = new ComboBox<>(sectors);
    ComboBox<Sector> editSectorListBox = new ComboBox<>();

    ComboBox<Category> editItemCategoriesBox = new ComboBox<>(categories);
    ComboBox<Category> editCategoryListBox = new ComboBox<>(categories);

    ComboBox<Supplier> editSupplierBox = new ComboBox<>(suppliers);

    ObservableList<String> editCategorySectors = FXCollections.observableArrayList();
    ComboBox<String> editCategorySectorsBox = new ComboBox<>(sectors);

    BorderPane createBox = new BorderPane();
    VBox addItemPane = new VBox();
    VBox addCategoryPane = new VBox();
    VBox addSectorPane = new VBox();
    VBox editItemPane = new VBox();
    VBox editCategoryPane = new VBox();
    VBox editSectorPane = new VBox();

    TextField categoryNameField = new TextField();
    Button addCategoryButton = new Button("Create Category");
    Button editCategoryButton = new Button("Edit Category");

    TextField sectorNameField = new TextField();
    Button addSectorButton = new Button("Create Sector");
    Button editSectorButton = new Button("Edit Sector");

    TextField itemNameField = new TextField();
    TextField itemQuantityField = new TextField();
    TextField itemPPriceField = new TextField();;
    TextField itemSPriceField = new TextField();
    Button addItemButton = new Button("Create Item");

    TextField itemEditNameField = new TextField();
    TextField itemEditQuantityField = new TextField();
    TextField itemEditPPriceField = new TextField();
    TextField itemEditSPriceField = new TextField();
    Button updateItemButton = new Button("Update Item");
    Button cancelUpdateItemButton = new Button("Cancel");

    TextField categoryEditNameField = new TextField();
    Button updateCategoryButton = new Button("Update Category");
    Button cancelUpdateCategoryButton = new Button("Cancel");

    TextField sectorEditNameField = new TextField();
    Button updateSectorButton = new Button("Update Sector");
    Button cancelUpdateSectorButton = new Button("Cancel");

    HBox updateInventoryButtonsList = new HBox();
    Button editItemButton = new Button("Edit Item");
    Button deleteItemButton = new Button("Delete Item");
    Button updateInventoryButton = new Button("Update Inventory");

    TableView<Item> inventoryTableView = new TableView<>();
    TableColumn<Item, String> itemIDColumn = new TableColumn<>("ID");
    TableColumn<Item, String> itemNameColumn = new TableColumn<>("Name");
    TableColumn<Item, String> itemCategoryColumn = new TableColumn<>("Category");
    TableColumn<Item, String> itemSupplierColumn = new TableColumn<>("Supplier");
    TableColumn<Item, Integer> itemQuantityColumn = new TableColumn<>("Quantity");
    TableColumn<Item, Double> itemPPriceColumn = new TableColumn<>("Purchase Price");
    TableColumn<Item, Double> itemSPriceColumn = new TableColumn<>("Selling Price");


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
        editCategoryButton.setStyle("-fx-font: 11pt Helvetica;");
        addCategoryHeader.getChildren().addAll(addCategoryLabel, editCategoryButton);
        GridPane addCategoryGrid = new GridPane();
        addCategoryGrid.setHgap(110);
        addCategoryGrid.setVgap(10);
        Label categoryNameLabel = new Label("Category Name:");
        categoryNameLabel.setStyle("-fx-font: 11pt Helvetica;");
        addCategoryGrid.add(categoryNameLabel, 0, 0);
        addCategoryGrid.add(categoryNameField, 1, 0);
        Label sectorCategoryLabel = new Label("Sector:");
        sectorCategoryLabel.setStyle("-fx-font: 11pt Helvetica;");
        addCategoryGrid.add(sectorCategoryLabel, 0, 1);
        sectorComboBox.setStyle("-fx-font: 11pt Helvetica;");
        addCategoryGrid.add(sectorComboBox, 1, 1);
        addCategoryButton.setStyle("-fx-font: 11pt Helvetica;");
        GridPane.setHalignment(addCategoryButton, HPos.RIGHT);
        addCategoryGrid.add(addCategoryButton, 1, 2);
        addCategoryPane.getChildren().addAll(addCategoryHeader, addCategoryGrid);
        addCategoryPane.setSpacing(10);
        addCategoryPane.setPadding(new Insets(10));

        //Create Sector Pane
        HBox addSectorHeader = new HBox();
        Label addSectorLabel = new Label("Add Sector");
        addSectorLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");
        editSectorButton.setStyle("-fx-font: 11pt Helvetica;");
        addSectorHeader.setSpacing(160);
        addSectorHeader.getChildren().addAll(addSectorLabel, editSectorButton);
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

        //Edit Item Pane
        HBox editItemHeader = new HBox();
        Label editItemLabel = new Label("Edit Item");
        editItemLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");
        editItemHeader.setSpacing(165);
        editItemHeader.getChildren().addAll(editItemLabel);
        GridPane editItemGrid = new GridPane();
        editItemGrid.setHgap(105);
        editItemGrid.setVgap(10);
        Label itemEditNameLabel = new Label("Item Name:");
        itemEditNameLabel.setStyle("-fx-font: 11pt Helvetica;");
        editItemGrid.add(itemEditNameLabel, 0, 0);
        editItemGrid.add(itemEditNameField, 1, 0);
        Label itemEditCategoryLabel = new Label("Item Category:");
        itemEditCategoryLabel.setStyle("-fx-font: 11pt Helvetica;");
        editItemGrid.add(itemEditCategoryLabel, 0, 1);
        editItemCategoriesBox.setStyle("-fx-font: 11pt Helvetica;");
        editItemGrid.add(editItemCategoriesBox, 1,1);
        Label itemEditQuantityLabel = new Label("Item Quantity:");
        itemEditQuantityLabel.setStyle("-fx-font: 11pt Helvetica;");
        editItemGrid.add(itemEditQuantityLabel, 0, 2);
        itemEditQuantityField.setStyle("-fx-font: 11pt Helvetica;");
        editItemGrid.add(itemEditQuantityField, 1, 2);
        Label itemEditPPriceLabel = new Label("Item Purchase Price:");
        itemEditPPriceLabel.setStyle("-fx-font: 11pt Helvetica;");
        editItemGrid.add(itemEditPPriceLabel, 0, 3);
        itemEditPPriceField.setStyle("-fx-font: 11pt Helvetica;");
        editItemGrid.add(itemEditPPriceField, 1, 3);
        Label itemEditSPriceLabel = new Label("Item Selling Price:");
        itemEditSPriceLabel.setStyle("-fx-font: 11pt Helvetica;");
        editItemGrid.add(itemEditSPriceLabel, 0, 4);
        itemEditSPriceField.setStyle("-fx-font: 11pt Helvetica;");
        editItemGrid.add(itemEditSPriceField, 1, 4);
        Label itemEditSupplierLabel = new Label("Item Supplier:");
        itemEditSupplierLabel.setStyle("-fx-font: 11pt Helvetica;");
        editItemGrid.add(itemEditSupplierLabel, 0, 5);
        editSupplierBox.setStyle("-fx-font: 11pt Helvetica;");
        editItemGrid.add(editSupplierBox, 1, 5);
        updateItemButton.setStyle("-fx-font: 11pt Helvetica;");
        GridPane.setHalignment(updateItemButton, HPos.RIGHT);
        editItemGrid.add(updateItemButton, 1, 6);
        cancelUpdateItemButton.setStyle("-fx-font: 11pt Helvetica;");
        GridPane.setHalignment(cancelUpdateItemButton, HPos.RIGHT);
        editItemGrid.add(cancelUpdateItemButton, 1, 7);
        editItemPane.getChildren().addAll(editItemHeader, editItemGrid);
        editItemPane.setSpacing(10);
        editItemPane.setPadding(new Insets(10));

        //Edit Category Pane
        HBox editCategoryHeader = new HBox();
        Label editCategoryLabel = new Label("Edit Category");
        editCategoryLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");
        editCategoryHeader.setSpacing(160);
        editCategoryHeader.getChildren().addAll(editCategoryLabel);
        GridPane editCategoryGrid = new GridPane();
        editCategoryGrid.setHgap(110);
        editCategoryGrid.setVgap(10);
        Label categoryEditNameLabel = new Label("Category Name:");
        categoryEditNameLabel.setStyle("-fx-font: 11pt Helvetica;");
        editCategoryGrid.add(categoryEditNameLabel, 0, 0);
        editCategoryGrid.add(categoryEditNameField, 1, 0);
        Label editSectorCategoryLabel = new Label("Sector:");
        editSectorCategoryLabel.setStyle("-fx-font: 11pt Helvetica;");
        editCategoryGrid.add(editSectorCategoryLabel, 0, 1);
        editCategorySectorsBox.setStyle("-fx-font: 11pt Helvetica;");
        editCategoryGrid.add(editCategorySectorsBox, 1, 1);
        updateCategoryButton.setStyle("-fx-font: 11pt Helvetica;");
        GridPane.setHalignment(updateCategoryButton, HPos.RIGHT);
        editCategoryGrid.add(updateCategoryButton, 1, 2);
        cancelUpdateCategoryButton.setStyle("-fx-font: 11pt Helvetica;");
        GridPane.setHalignment(cancelUpdateCategoryButton, HPos.RIGHT);
        editCategoryGrid.add(cancelUpdateCategoryButton, 1, 3);
        editCategoryPane.getChildren().addAll(editCategoryHeader, editCategoryGrid);
        editCategoryPane.setSpacing(10);
        editCategoryPane.setPadding(new Insets(10));

        //Edit Sector Pane
        HBox editSectorHeader = new HBox();
        Label editSectorLabel = new Label("Edit Sector");
        editSectorLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");
        editSectorHeader.setSpacing(160);
        editSectorHeader.getChildren().addAll(editSectorLabel);
        GridPane editSectorGrid = new GridPane();
        editSectorGrid.setHgap(110);
        editSectorGrid.setVgap(10);
        Label sectorEditNameLabel = new Label("Sector Name:");
        sectorEditNameLabel.setStyle("-fx-font: 11pt Helvetica;");
        editSectorGrid.add(sectorEditNameLabel, 0, 0);
        sectorEditNameField.setStyle("-fx-font: 11pt Helvetica;");
        editSectorGrid.add(sectorEditNameField, 1, 0);
        updateSectorButton.setStyle("-fx-font: 11pt Helvetica;");
        GridPane.setHalignment(updateSectorButton, HPos.RIGHT);
        editSectorGrid.add(updateSectorButton, 1, 1);
        cancelUpdateSectorButton.setStyle("-fx-font: 11pt Helvetica;");
        GridPane.setHalignment(cancelUpdateSectorButton, HPos.RIGHT);
        editSectorGrid.add(cancelUpdateSectorButton, 1, 2);
        editSectorPane.getChildren().addAll(editSectorHeader, editSectorGrid);
        editSectorPane.setSpacing(10);
        editSectorPane.setPadding(new Insets(10));


        //Display Inventory List
        VBox inventoryListBox = new VBox();
        Label inventoryListLabel = new Label("Inventory List");
        inventoryListLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");
        inventoryListBox.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 20px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");
        inventoryListBox.setSpacing(10);

        inventoryTableView.setEditable(true);
        inventoryTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        inventoryTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        itemIDColumn.setPrefWidth(100);
        itemIDColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("itemID"));
        itemIDColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        itemIDColumn.setOnEditCommit(e -> e.getRowValue().setItemID(e.getNewValue()));
        itemNameColumn.setPrefWidth(100);
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        itemNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        itemNameColumn.setOnEditCommit(e -> e.getRowValue().setName(e.getNewValue()));
        itemCategoryColumn.setPrefWidth(150);
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("category"));
        itemCategoryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        itemCategoryColumn.setOnEditCommit(e -> e.getRowValue().setCategory(e.getNewValue()));
        itemSupplierColumn.setPrefWidth(100);
        itemSupplierColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("supplier"));
        itemSupplierColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        itemSupplierColumn.setOnEditCommit(e -> e.getRowValue().setSupplier(e.getNewValue()));
        itemQuantityColumn.setPrefWidth(100);
        itemQuantityColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("quantity"));
        itemQuantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        itemQuantityColumn.setOnEditCommit(e -> e.getRowValue().setQuantity(e.getNewValue().intValue()));
        itemPPriceColumn.setPrefWidth(100);
        itemPPriceColumn.setCellValueFactory(new PropertyValueFactory<Item, Double>("purchasePrice"));
        itemPPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        itemPPriceColumn.setOnEditCommit(e -> e.getRowValue().setPurchasePrice(e.getNewValue().doubleValue()));
        itemSPriceColumn.setPrefWidth(100);
        itemSPriceColumn.setCellValueFactory(new PropertyValueFactory<Item, Double>("sellingPrice"));
        itemSPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        itemSPriceColumn.setOnEditCommit(e -> e.getRowValue().setSellingPrice(e.getNewValue().doubleValue()));
        inventoryTableView.getColumns().addAll(itemIDColumn, itemNameColumn, itemCategoryColumn, itemSupplierColumn, itemQuantityColumn, itemPPriceColumn, itemSPriceColumn);
        inventoryTableView.setMaxWidth(1000);
        updateInventoryButton.setStyle("-fx-font: 11pt Helvetica;");
        editItemButton.setStyle("-fx-font: 11pt Helvetica");
        deleteItemButton.setStyle("-fx-font: 11pt Helvetica");
        updateInventoryButtonsList.setSpacing(225);
        updateInventoryButtonsList.getChildren().addAll(updateInventoryButton, editItemButton, deleteItemButton);
        inventoryListBox.getChildren().addAll(inventoryListLabel, inventoryTableView, updateInventoryButtonsList);

        inventoryPage.getChildren().addAll(createBox, inventoryListBox);
        inventoryPage.setSpacing(10);
    }

    public TableColumn<Item, String> getItemIDColumn() {
        return itemIDColumn;
    }

    public TableColumn<Item, String> getItemNameColumn() {
        return itemNameColumn;
    }

    public TableColumn<Item, String> getItemCategoryColumn() {
        return itemCategoryColumn;
    }

    public TableColumn<Item, String> getItemSupplierColumn() {
        return itemSupplierColumn;
    }

    public TableColumn<Item, Integer> getItemQuantityColumn() {
        return itemQuantityColumn;
    }

    public TableColumn<Item, Double> getItemPPriceColumn() {
        return itemPPriceColumn;
    }

    public TableColumn<Item, Double> getItemSPriceColumn() {
        return itemSPriceColumn;
    }

    public HBox getInventoryPage() {
        return inventoryPage;
    }

    public ObservableList<Category> getCategories() {
        return categories;
    }

    public ComboBox<Category> getItemCategoryListView() {
        return itemCategoryListView;
    }

    public ObservableList<Supplier> getSuppliers() {
        return suppliers;
    }

    public ComboBox<Supplier> getItemSupplierListView() {
        return itemSupplierListView;
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

    public void setCategories(ObservableList<Category> categories) {
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

    public ObservableList<Item> getItems() {
        return items;
    }

    public void setItems(ObservableList<Item> items) {
        this.items = items;
    }

    public void setSuppliers(ObservableList<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public void setItemSupplierListView(ComboBox<Supplier> itemSupplierListView) {
        this.itemSupplierListView = itemSupplierListView;
    }

    public void setOptionsList(ObservableList<String> optionsList) {
        this.optionsList = optionsList;
    }

    public void setOptionsComboBox(ComboBox<String> optionsComboBox) {
        this.optionsComboBox = optionsComboBox;
    }

    public void setItemCategoryListView(ComboBox<Category> itemCategoryListView) {
        this.itemCategoryListView = itemCategoryListView;
    }

    public TextField getSectorNameField() {
        return sectorNameField;
    }

    public ObservableList<String> getSectors() {
        return sectors;
    }

    public void setSectors(ObservableList<String> sectors) {
        this.sectors = sectors;
    }

    public ComboBox<String> getSectorComboBox() {
        return sectorComboBox;
    }

    public void setSectorComboBox(ComboBox<String> sectorComboBox) {
        this.sectorComboBox = sectorComboBox;
    }

    public HBox getUpdateInventoryButtonsList() {
        return updateInventoryButtonsList;
    }

    public Button getEditItemButton() {
        return editItemButton;
    }

    public Button getDeleteItemButton() {
        return deleteItemButton;
    }

    public ComboBox<Category> getEditItemCategoriesBox() {
        return editItemCategoriesBox;
    }

    public ComboBox<Supplier> getEditSupplierBox() {
        return editSupplierBox;
    }

    public ObservableList<String> getEditCategorySectors() {
        return editCategorySectors;
    }

    public ComboBox<String> getEditCategorySectorsBox() {
        return editCategorySectorsBox;
    }

    public VBox getEditItemPane() {
        return editItemPane;
    }

    public VBox getEditCategoryPane() {
        return editCategoryPane;
    }

    public VBox getEditSectorPane() {
        return editSectorPane;
    }

    public Button getEditCategoryButton() {
        return editCategoryButton;
    }

    public Button getEditSectorButton() {
        return editSectorButton;
    }

    public TextField getItemEditNameField() {
        return itemEditNameField;
    }

    public TextField getItemEditQuantityField() {
        return itemEditQuantityField;
    }

    public TextField getItemEditPPriceField() {
        return itemEditPPriceField;
    }

    public TextField getItemEditSPriceField() {
        return itemEditSPriceField;
    }

    public Button getUpdateItemButton() {
        return updateItemButton;
    }

    public Button getCancelUpdateItemButton() {
        return cancelUpdateItemButton;
    }

    public TextField getCategoryEditNameField() {
        return categoryEditNameField;
    }

    public Button getUpdateCategoryButton() {
        return updateCategoryButton;
    }

    public Button getCancelUpdateCategoryButton() {
        return cancelUpdateCategoryButton;
    }

    public TextField getSectorEditNameField() {
        return sectorEditNameField;
    }

    public Button getUpdateSectorButton() {
        return updateSectorButton;
    }

    public Button getGetCancelUpdateSectorButton() {
        return cancelUpdateSectorButton;
    }

    public ComboBox<Sector> getEditSectorListBox() {
        return editSectorListBox;
    }

    public ComboBox<Category> getEditCategoryListBox() {
        return editCategoryListBox;
    }

    public Button getCancelUpdateSectorButton() {
        return cancelUpdateSectorButton;
    }
}
