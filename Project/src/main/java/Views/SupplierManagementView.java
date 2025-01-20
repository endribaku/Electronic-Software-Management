package Views;

import Models.Access;
import Models.Item;
import Models.Supplier;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;

public class SupplierManagementView {
    private HBox suppliersPage = new HBox();

    private TextField suppliersNameField = new TextField();
    private ObservableList<Item> itemList = FXCollections.observableArrayList();
    private ListView<Item> itemBoxList= new ListView<Item>(itemList);

    private Button addSupplierButton = new Button("Register Supplier");

    private TableView<Supplier> suppliersTableView = new TableView<>();
    private TableColumn<Supplier, String> supplierIDColumn = new TableColumn<>("ID");
    private TableColumn<Supplier, String> supplierNameColumn = new TableColumn<>("Full Name");
    private TableColumn<Supplier, Item> supplierItemListColumn = new TableColumn<>("Supplied Items");

    private Button updateSupplierListButton = new Button("Update Table");

    public SupplierManagementView() {

        suppliersPage.setStyle("-fx-background-color: white; -fx-padding: 10;");

        //Create new Supplier
        VBox addSupplierBox = new VBox();
        addSupplierBox.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 20px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");
        addSupplierBox.setSpacing(10);
        Label addSupplierLabel = new Label("Register new Supplier");
        addSupplierLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");
        GridPane addSupplierGrid = new GridPane();
        addSupplierGrid.setHgap(100);
        addSupplierGrid.setVgap(10);
        Label supplierNameLabel = new Label("Full Name:");
        supplierNameLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
        addSupplierGrid.add(supplierNameLabel, 0, 0);
        suppliersNameField.setStyle("-fx-font: 11pt Helvetica;");
        addSupplierGrid.add(suppliersNameField, 1, 0);
        Label itemBoxLabel = new Label("Supplied Items:");
        itemBoxLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
        addSupplierGrid.add(itemBoxLabel, 0, 1);
        itemBoxList.setStyle("-fx-font: 11pt Helvetica;");
        itemBoxList.setMaxWidth(200);
        itemBoxList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        addSupplierGrid.add(itemBoxList, 1, 1);
        //Create User Button
        addSupplierButton.setStyle("-fx-font: 11pt Helvetica;");
        GridPane.setHalignment(addSupplierButton, HPos.RIGHT);
        addSupplierGrid.add(addSupplierButton, 1, 2);
        addSupplierBox.getChildren().addAll(addSupplierLabel, addSupplierGrid);
        addSupplierBox.setPrefWidth(450);

        //Display Suppliers' List
        VBox supplierListBox = new VBox();
        Label supplierListLabel = new Label("Supplier List:");
        supplierListLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");
        supplierListBox.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 20px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");
        supplierListBox.setSpacing(10);
        suppliersTableView.setEditable(true);
        suppliersTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        suppliersTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        supplierIDColumn.setMinWidth(200);
        supplierNameColumn.setMinWidth(200);
        supplierIDColumn.setCellValueFactory(new PropertyValueFactory<Supplier, String>("supplierID"));
        supplierIDColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        supplierIDColumn.setOnEditCommit(e -> e.getRowValue().setSupplierID(e.getNewValue()));
        supplierNameColumn.setCellValueFactory(new PropertyValueFactory<Supplier, String>("name"));
        supplierNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        supplierNameColumn.setOnEditCommit(e -> e.getRowValue().setName(e.getNewValue()));
        supplierItemListColumn.setMinWidth(200);
        supplierItemListColumn.setCellValueFactory(new PropertyValueFactory<Supplier, Item>("suppliedItems"));
        supplierItemListColumn.setCellFactory(ComboBoxTableCell.forTableColumn(itemList));
        suppliersTableView.getColumns().addAll(supplierIDColumn, supplierNameColumn, supplierItemListColumn);
        suppliersTableView.setPrefWidth(800);
        updateSupplierListButton.setStyle("-fx-font: 11pt Helvetica;");

        supplierListBox.getChildren().addAll(supplierListLabel, suppliersTableView, updateSupplierListButton);
        suppliersPage.getChildren().addAll(addSupplierBox, supplierListBox);
        suppliersPage.setSpacing(10);
    }

    public HBox getSuppliersPage() {
        return suppliersPage;
    }

    public TextField getSuppliersNameField() {
        return suppliersNameField;
    }

    public ObservableList<Item> getItemList() {
        return itemList;
    }

    public ListView<Item> getItemBoxList() {
        return itemBoxList;
    }

    public TableView<Supplier> getSuppliersTableView() {
        return suppliersTableView;
    }

    public Button getAddSupplierButton() {
        return addSupplierButton;
    }

    public TableColumn<Supplier, String> getSupplierIDColumn() {
        return supplierIDColumn;
    }

    public TableColumn<Supplier, Item> getSupplierItemListColumn() {
        return supplierItemListColumn;
    }

    public TableColumn<Supplier, String> getSupplierNameColumn() {
        return supplierNameColumn;
    }

    public Button getUpdateSupplierListButton() {
        return updateSupplierListButton;
    }
}
