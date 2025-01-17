package Views;

import Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class BillGenerateView {
    HBox billGeneratePage = new HBox();

    ObservableList<Item> items;
    TableView<Item> inventoryTableView = new TableView<>(items);

    BorderPane createBox = new BorderPane();
    VBox addItemstoBillPane = new VBox();
    ObservableList<Item> itemList = FXCollections.observableArrayList(
            new Item("Macbook", new Category("Laptop", new ArrayList<>()),
                    new Supplier("123", "Apple", new ArrayList<>()),
                    LocalDate.now(), 1000, 1200, 5),
            new Item("iPhone 12", new Category("Smartphone", new ArrayList<>()),
                    new Supplier("123", "Apple", new ArrayList<>()),
                    LocalDate.now(), 500, 700, 5),
            new Item("iPhone 13", new Category("Smartphone", new ArrayList<>()),
                    new Supplier("123", "Apple", new ArrayList<>()),
                    LocalDate.now(), 600, 800, 5),
            new Item("iPad 3", new Category("Tablet", new ArrayList<>()),
                    new Supplier("123", "Apple", new ArrayList<>()),
                    LocalDate.now(), 800, 1000, 5),
            new Item("iPhone 16", new Category("Smartphone", new ArrayList<>()),
                    new Supplier("123", "Apple", new ArrayList<>()),
                    LocalDate.now(), 1300, 1500, 5)
    );
    FilteredList<Item> filteredItemList = new FilteredList<>(itemList, p -> true);

    TextField searchBar = new TextField();

    ListView<Item> itemListView = new ListView<>(filteredItemList);

    ObservableList<Bill_Item> billList = FXCollections.observableArrayList();
    VBox billListBox = new VBox();
    ListView<Bill_Item> billListView = new ListView<>(billList);

    Button addToBillButton = new Button("Add to Bill");

    TableView<Bill_Item> billTableView = new TableView<>(billList);
    TableColumn<Bill_Item, Number> itemIDColumn = new TableColumn<>("ID");
    TableColumn<Bill_Item, String> itemNameColumn = new TableColumn<>("Name");
    TableColumn<Bill_Item, Number> itemQuantityColumn = new TableColumn<>("Quantity");
    TableColumn<Bill_Item, Number> itemPriceColumn = new TableColumn<>("Price");

    Button createBillButton = new Button("Create Bill");

    public BillGenerateView() {

        billGeneratePage.setStyle("-fx-background-color: white; -fx-padding: 10;");
        createBox.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 10px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");
        createBox.setMinWidth(400);

        searchBar.setPromptText("Search...");
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredItemList.setPredicate(item -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Show all items
                }
                return item.getName().toLowerCase().contains(newValue.toLowerCase()); // Filter items
            });
        });

        itemListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        addToBillButton.setOnAction(e -> {
            ObservableList<Item> selectedItems = itemListView.getSelectionModel().getSelectedItems();
            for (Item item : selectedItems) {
                Bill_Item existingBillItem = billList.stream()
                        .filter(billItem -> billItem.getItem().equals(item))
                        .findFirst()
                        .orElse(null);

                if (existingBillItem != null) {
                    // Increment quantity if already in the bill
                    int newQuantity = existingBillItem.getQuantity() + 1;
                    billList.remove(existingBillItem);
                    billList.add(new Bill_Item(item));
                } else {
                    // Add new BillItem
                    billList.add(new Bill_Item(item));
                }
            }
        });
        addToBillButton.setStyle("-fx-font: 11pt Helvetica;");
        GridPane.setHalignment(addToBillButton, HPos.RIGHT);

        //Create Item Pane
        addItemstoBillPane.getChildren().addAll(searchBar, itemListView, addToBillButton);
        addItemstoBillPane.setSpacing(10);
        createBox.setCenter(addItemstoBillPane);

        //Display Bill
        Label billListLabel = new Label("Generate Bill");
        billListLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");
        billListBox.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 20px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");
        billListBox.setSpacing(10);

        billTableView.getColumns().addAll(itemIDColumn, itemNameColumn, itemQuantityColumn, itemPriceColumn);
        billTableView.setPrefWidth(1000);
        billListBox.getChildren().addAll(billListLabel, billTableView, createBillButton);

        billGeneratePage.getChildren().addAll(createBox, billListBox);
        billGeneratePage.setSpacing(10);
    }

    public HBox getBillGeneratePage() {
        return billGeneratePage;
    }

    public ObservableList<Item> getItems() {
        return items;
    }

    public TableView<Item> getInventoryTableView() {
        return inventoryTableView;
    }

    public BorderPane getCreateBox() {
        return createBox;
    }

    public ObservableList<Item> getItemList() {
        return itemList;
    }

    public VBox getAddItemstoBillPane() {
        return addItemstoBillPane;
    }

    public FilteredList<Item> getFilteredItemList() {
        return filteredItemList;
    }

    public TextField getSearchBar() {
        return searchBar;
    }

    public ListView<Item> getItemListView() {
        return itemListView;
    }

    public ObservableList<Bill_Item> getBillList() {
        return billList;
    }

    public ListView<Bill_Item> getBillListView() {
        return billListView;
    }

    public Button getAddToBillButton() {
        return addToBillButton;
    }

    public Button getCreateBillButton() {
        return createBillButton;
    }
}
