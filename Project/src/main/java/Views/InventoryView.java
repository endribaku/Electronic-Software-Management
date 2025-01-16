package Views;

import Models.Access;
import Models.Item;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InventoryView {

    HBox inventoryPage = new HBox();
    ObservableList<String> categories = FXCollections.observableArrayList(
            "Major Domestic Appliances", "Climate & Air", "Small Domestic Appliances", "Consumer Electronics",
            "TV", "IT & Accessories", "Phones & Accessories", "Gaming & Accessories", "Kitchen Utensils",
            "Electrical Accessories", "Fitness Accessories");
    ComboBox<String> itemCategoryListView = new ComboBox<String>(categories);
    ObservableList<String> suppliers = FXCollections.observableArrayList("Samsung", "Apple", "HP", "Lenovo", "Dell");
    ComboBox<String> itemSupplierListView = new ComboBox<String>(suppliers);

    ObservableList<Item> items;
    TableView<Item> inventoryTableView = new TableView<>(items);

    TextField itemNameField = new TextField();
    TextField itemQuantityField = new TextField();
    Label itemPPriceLabel = new Label("Item Purchase Price:");
    TextField itemPPriceField = new TextField();;
    Label itemSPriceLabel = new Label("Item Selling Price:");
    TextField itemSPriceField = new TextField();
    Label itemSupplierLabel = new Label("Item Supplier:");

    public InventoryView() {

        inventoryPage.setStyle("-fx-background-color: white; -fx-padding: 10;");
        VBox addItemBox = new VBox();
        addItemBox.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 10px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");
        HBox addItemHeader = new HBox();
        Label addItemLabel = new Label("Add Item");
        addItemLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");
        Button addCategoryPaneButton = new Button("Add Category");
        addCategoryPaneButton.setStyle("-fx-font: 11pt Helvetica;");
        addCategoryPaneButton.setOnAction(e -> {})
        addItemHeader.setSpacing(50);
        addItemHeader.getChildren().addAll(addItemLabel, addCategoryPaneButton);
        GridPane addItemGrid = new GridPane();
        addItemGrid.setHgap(10);
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
        Button addItemButton = new Button("Add Item");
        addItemButton.setStyle("-fx-font: 11pt Helvetica;");

        GridPane.setHalignment(addItemButton, HPos.RIGHT);
        addItemGrid.add(addItemButton, 1, 6);

        //Display Employee's List
        VBox inventoryListBox = new VBox();
        Label inventoryListLabel = new Label("Inventory List");
        inventoryListLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");
        inventoryListBox.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 20px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");
        inventoryListBox.setSpacing(10);
        //employees = FXCollections.observableArrayList(currentAdmin.getEmployees());
        TableColumn<Item, Number> itemIDColumn = new TableColumn<>("ID");
        //employeeFullNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFullName()); => needs to change fields to SimpleProperty's
        TableColumn<Item, String> itemNameColumn = new TableColumn<>("Name");
        TableColumn<Item, String> itemCategoryColumn = new TableColumn<>("Category");
        TableColumn<Item, String> itemSupplierColumn = new TableColumn<>("Supplier");
        TableColumn<Item, Number> itemQuantityColumn = new TableColumn<>("Quantity");
        TableColumn<Item, Number> itemPPriceColumn = new TableColumn<>("Purchase Price");
        TableColumn<Item, Number> itemSPriceColumn = new TableColumn<>("Selling Price");

        inventoryTableView.getColumns().addAll(itemIDColumn, itemNameColumn, itemCategoryColumn, itemSupplierColumn, itemQuantityColumn, itemPPriceColumn, itemSPriceColumn);
        inventoryTableView.setPrefWidth(1000);
        inventoryListBox.getChildren().addAll(inventoryListLabel, inventoryTableView);

        inventoryPage.getChildren().addAll(addItemGrid, inventoryListBox);
        inventoryPage.setSpacing(10);
    }

    public HBox getInventoryPage() {
        return inventoryPage;
    }

    public ObservableList<String> getCategories() {
        return categories;
    }

    public ComboBox<String> getItemCategoryListView() {
        return itemCategoryListView;
    }

    public ObservableList<String> getSuppliers() {
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
}
