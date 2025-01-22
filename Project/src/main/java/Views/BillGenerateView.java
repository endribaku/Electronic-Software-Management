package Views;

import Models.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class BillGenerateView {
    HBox billGeneratePage = new HBox();

    ObservableList<Item> items;
    TableView<Item> inventoryTableView = new TableView<>(items);

    BorderPane createBox = new BorderPane();
    VBox addItemstoBillPane = new VBox();

    TextField quantityTextField = new TextField();

    ListView<Item> itemListView = new ListView<>();

    ObservableList<Bill_Item> billList = FXCollections.observableArrayList();
    VBox billListBox = new VBox();
    ListView<Bill_Item> billListView = new ListView<>(billList);

    Button addToBillButton = new Button("Add to Bill");

    TableView<Bill_Item> billTableView = new TableView<>(billList);

    TableColumn<Bill_Item, String> itemIDColumn = new TableColumn<>("ID");
    TableColumn<Bill_Item, String> itemNameColumn = new TableColumn<>("Name");
    TableColumn<Bill_Item, Integer> itemQuantityColumn = new TableColumn<>("Quantity");
    TableColumn<Bill_Item, Double> itemPriceColumn = new TableColumn<>("Price");
    Button createBillButton = new Button("Create Bill");



    public BillGenerateView() {

        billGeneratePage.setStyle("-fx-background-color: white; -fx-padding: 10;");
        createBox.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 10px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");
        createBox.setMinWidth(400);

        addToBillButton.setStyle("-fx-font: 11pt Helvetica;");
        GridPane.setHalignment(addToBillButton, HPos.RIGHT);

        GridPane quantityGrid = new GridPane();
        Label quantityLabel = new Label("Quantity:");
        quantityLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
        quantityTextField.setStyle("-fx-font: 11pt Helvetica;");
        quantityGrid.add(quantityLabel, 0, 0);
        quantityGrid.add(quantityTextField, 1, 0);
        quantityGrid.add(addToBillButton, 1, 1);
        quantityGrid.setHgap(130);
        quantityGrid.setVgap(10);


        //Add to Bill Pane
        addItemstoBillPane.getChildren().addAll(itemListView, quantityGrid);
        addItemstoBillPane.setSpacing(10);
        createBox.setCenter(addItemstoBillPane);

        //Display Bill
        Label billListLabel = new Label("Generate Bill");
        billListLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");
        billListBox.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 20px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");
        billListBox.setSpacing(10);

        itemIDColumn.setMinWidth(200);
        itemNameColumn.setMinWidth(200);
        itemQuantityColumn.setMinWidth(200);
        itemPriceColumn.setMinWidth(200);
        itemIDColumn.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        itemQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        itemIDColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        itemNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        itemPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        itemQuantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

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

    public VBox getAddItemstoBillPane() {
        return addItemstoBillPane;
    }

    public ListView<Item> getItemListView() {
        return itemListView;
    }

    public ObservableList<Bill_Item> getBillList() {
        return billList;
    }

    public Button getAddToBillButton() {
        return addToBillButton;
    }

    public Button getCreateBillButton() {
        return createBillButton;
    }

    public TextField getQuantityTextField() {
        return quantityTextField;
    }

    public VBox getBillListBox() {
        return billListBox;
    }

    public TableView<Bill_Item> getBillTableView() {
        return billTableView;
    }

    public TableColumn<Bill_Item, String> getItemIDColumn() {
        return itemIDColumn;
    }

    public TableColumn<Bill_Item, String> getItemNameColumn() {
        return itemNameColumn;
    }

    public TableColumn<Bill_Item, Integer> getItemQuantityColumn() {
        return itemQuantityColumn;
    }

    public TableColumn<Bill_Item, Double> getItemPriceColumn() {
        return itemPriceColumn;
    }
}
