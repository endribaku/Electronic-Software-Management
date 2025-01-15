package Views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class InventoryView {

    ObservableList<String> categories = FXCollections.observableArrayList(
            "Major Domestic Appliances", "Climate & Air", "Small Domestic Appliances", "Consumer Electronics",
            "TV", "IT & Accessories", "Phones & Accessories", "Gaming & Accessories", "Kitchen Utensils",
            "Electrical Accessories", "Fitness Accessories");
    ComboBox<String> itemCategoryListView = new ComboBox<String>(categories);
    ObservableList<String> suppliers = FXCollections.observableArrayList("Samsung", "Apple", "HP", "Lenovo", "Dell");
    ComboBox<String> itemSupplierListView = new ComboBox<String>(suppliers);

    public Pane show() {
        HBox inventoryPage = new HBox();
        inventoryPage.setStyle("-fx-background-color: white; -fx-padding: 10;");
        GridPane addItemGrid = new GridPane();
        addItemGrid.setHgap(10);
        addItemGrid.setVgap(10);
        addItemGrid.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 10px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");
        Label itemNameLabel = new Label("Item Name:");
        itemNameLabel.setStyle("-fx-font: 11pt Helvetica;");
        addItemGrid.add(itemNameLabel, 0, 0);
        TextField itemNameField = new TextField();
        addItemGrid.add(itemNameField, 1, 0);
        Label itemCategoryLabel = new Label("Item Category:");
        itemCategoryLabel.setStyle("-fx-font: 11pt Helvetica;");
        addItemGrid.add(itemCategoryLabel, 0, 1);
        itemCategoryListView.setStyle("-fx-font: 11pt Helvetica;");
        addItemGrid.add(itemCategoryListView, 1,1);
        Label itemQuantityLabel = new Label("Item Quantity:");
        itemQuantityLabel.setStyle("-fx-font: 11pt Helvetica;");
        addItemGrid.add(itemQuantityLabel, 0, 2);
        TextField itemQuantityField = new TextField();
        addItemGrid.add(itemQuantityField, 1, 2);
        Label itemPPriceLabel = new Label("Item Purchase Price:");
        itemPPriceLabel.setStyle("-fx-font: 11pt Helvetica;");
        addItemGrid.add(itemPPriceLabel, 0, 3);
        TextField itemPPriceField = new TextField();
        addItemGrid.add(itemPPriceField, 1, 3);
        Label itemSPriceLabel = new Label("Item Selling Price:");
        itemSPriceLabel.setStyle("-fx-font: 11pt Helvetica;");
        addItemGrid.add(itemSPriceLabel, 0, 4);
        TextField itemSPriceField = new TextField();
        addItemGrid.add(itemSPriceField, 1, 4);
        Label itemSupplierLabel = new Label("Item Supplier:");
        itemSupplierLabel.setStyle("-fx-font: 11pt Helvetica;");
        addItemGrid.add(itemSupplierLabel, 0, 5);
        itemSupplierListView.setStyle("-fx-font: 11pt Helvetica;");
        addItemGrid.add(itemSupplierListView, 1, 5);
        Button addItemButton = new Button("Add Item");
        addItemButton.setStyle("-fx-font: 11pt Helvetica;");
        addItemButton.setOnAction(e -> {
            String itemName = itemNameField.getText();
            String itemCategory = itemCategoryListView.getSelectionModel().getSelectedItem();
            int itemQuantity = Integer.parseInt(itemQuantityField.getText());
            double itemPPrice = Double.parseDouble(itemPPriceField.getText());
            double itemSPrice = Double.parseDouble(itemSPriceField.getText());
            String itemSupplier = itemSupplierListView.getSelectionModel().getSelectedItem();
            if (itemName.equals("") || itemCategory.equals("") || itemQuantity == 0 || itemPPrice == 0 || itemSPrice == 0 || itemSupplier.equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Input");
            }
        });
        GridPane.setHalignment(addItemButton, HPos.RIGHT);
        addItemGrid.add(addItemButton, 1, 6);
        inventoryPage.getChildren().addAll(addItemGrid);

        return inventoryPage;
    }
}
