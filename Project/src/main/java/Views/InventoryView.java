package Views;

import DAO.InventoryFileHandler;
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
import javafx.util.converter.NumberStringConverter;

public class InventoryView {

    HBox inventoryPage = new HBox();
//    ObservableList<String> categories = FXCollections.observableArrayList();
    ObservableList<Category> categories = FXCollections.observableArrayList();
    ComboBox<Category> itemCategoryListView = new ComboBox<Category>(categories);
    ListView<Category> sectorCategoryListView = new ListView<>(categories);
    ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
    ComboBox<Supplier> itemSupplierListView = new ComboBox<Supplier>(suppliers);
    ObservableList<String> optionsList = FXCollections.observableArrayList("Add Item", "Add Category", "Add Sector");
    ComboBox<String> optionsComboBox = new ComboBox<>(optionsList);

    ObservableList<Item> items = FXCollections.observableArrayList();
    ListView<Item> itemListBox = new ListView<>(items);



    ObservableList<String> sectors = FXCollections.observableArrayList();
    ComboBox<String> sectorComboBox = new ComboBox<>(sectors);

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
        addCategoryHeader.getChildren().addAll(addCategoryLabel);
        GridPane addCategoryGrid = new GridPane();
        addCategoryGrid.setHgap(110);
        addCategoryGrid.setVgap(10);
        Label categoryNameLabel = new Label("Category Name:");
        categoryNameLabel.setStyle("-fx-font: 11pt Helvetica;");
        addCategoryGrid.add(categoryNameLabel, 0, 0);
        addCategoryGrid.add(categoryNameField, 1, 0);
        Label itemlistLabel = new Label("Item List:");
        itemlistLabel.setStyle("-fx-font: 11pt Helvetica;");
        addCategoryGrid.add(itemlistLabel, 0, 2);
        itemListBox.setStyle("-fx-font: 11pt Helvetica;");
        itemListBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        itemListBox.setMaxHeight(150);
        addCategoryGrid.add(itemListBox, 1, 2);
        Label sectorCategoryLabel = new Label("Sector:");
        sectorCategoryLabel.setStyle("-fx-font: 11pt Helvetica;");
        addCategoryGrid.add(sectorCategoryLabel, 0, 1);
        sectorComboBox.setStyle("-fx-font: 11pt Helvetica;");
        addCategoryGrid.add(sectorComboBox, 1, 1);
        addCategoryButton.setStyle("-fx-font: 11pt Helvetica;");
        GridPane.setHalignment(addCategoryButton, HPos.RIGHT);
        addCategoryGrid.add(addCategoryButton, 1, 3);
        addCategoryPane.getChildren().addAll(addCategoryHeader, addCategoryGrid);
        addCategoryPane.setSpacing(10);
        addCategoryPane.setPadding(new Insets(10));

        //Create Sector Pane
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
        Label categoryListViewLabel = new Label("Category:");
        categoryListViewLabel.setStyle("-fx-font: 11pt Helvetica;");
        addSectorGrid.add(categoryListViewLabel, 0, 1);
        sectorCategoryListView.setStyle("-fx-font: 11pt Helvetica;");
        sectorCategoryListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        sectorCategoryListView.setMaxHeight(150);
        addSectorGrid.add(sectorCategoryListView, 1, 1);
        addSectorButton.setStyle("-fx-font: 11pt Helvetica;");
        GridPane.setHalignment(addSectorButton, HPos.RIGHT);
        addSectorGrid.add(addSectorButton, 1, 2);
        addSectorPane.getChildren().addAll(addSectorHeader, addSectorGrid);
        addSectorPane.setSpacing(10);
        addSectorPane.setPadding(new Insets(10));

        //Display Employee's List
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
        inventoryListBox.getChildren().addAll(inventoryListLabel, inventoryTableView, updateInventoryButton);

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

    public ListView<Item> getItemListBox() {
        return itemListBox;
    }

    public void setItemListBox(ListView<Item> itemListBox) {
        this.itemListBox = itemListBox;
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



    public ListView<Category> getSectorCategoryListView() {
        return sectorCategoryListView;
    }

    public void setSectorCategoryListView(ListView<Category> sectorCategoryListView) {
        this.sectorCategoryListView = sectorCategoryListView;
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
}
