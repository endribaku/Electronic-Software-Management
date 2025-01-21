package Views;

import Models.Item;
import Models.Supplier;
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

public class SupplierManagementView {
    private HBox suppliersPage = new HBox();
    private VBox manageSupplierBox = new VBox();
    private VBox addSupplierBox = new VBox();
    private VBox editSupplierBox = new VBox();

    private TextField suppliersNameField = new TextField();
    private ObservableList<Item> itemList = FXCollections.observableArrayList();
    private ListView<Item> itemBoxList= new ListView<Item>(itemList);

    private TextField suppliersEditNameField = new TextField();

    private Button addSupplierButton = new Button("Register Supplier");
    private Button updateSupplierButton = new Button("Update Supplier");
    private Button cancelUpdateButton = new Button("Cancel");

    private TableView<Supplier> suppliersTableView = new TableView<>();
    private TableColumn<Supplier, String> supplierIDColumn = new TableColumn<>("ID");
    private TableColumn<Supplier, String> supplierNameColumn = new TableColumn<>("Full Name");
    private TableColumn<Supplier, Item> supplierItemListColumn = new TableColumn<>("Supplied Items");

    HBox editSupplierButtonList = new HBox();
    private Button updateSupplierListButton = new Button("Update Table");
    private Button editSupplierListButton = new Button("Edit Supplier");
    private Button deleteSupplierListButton = new Button("Delete Supplier");

    public SupplierManagementView() {

        suppliersPage.setStyle("-fx-background-color: white; -fx-padding: 10;");

        //Create new Supplier
        manageSupplierBox.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 20px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");
        addSupplierBox.setSpacing(10);
        Label addSupplierLabel = new Label("Register new Supplier");
        addSupplierLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");
        GridPane addSupplierGrid = new GridPane();
        addSupplierGrid.setHgap(180);
        addSupplierGrid.setVgap(10);
        Label supplierNameLabel = new Label("Full Name:");
        supplierNameLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
        addSupplierGrid.add(supplierNameLabel, 0, 0);
        suppliersNameField.setStyle("-fx-font: 11pt Helvetica;");
        addSupplierGrid.add(suppliersNameField, 1, 0);

        //Create User Button
        addSupplierButton.setStyle("-fx-font: 11pt Helvetica;");
        GridPane.setHalignment(addSupplierButton, HPos.RIGHT);
        addSupplierGrid.add(addSupplierButton, 1, 1);
        addSupplierBox.getChildren().addAll(addSupplierLabel, addSupplierGrid);
        addSupplierBox.setPrefWidth(450);
        manageSupplierBox.getChildren().add(addSupplierBox);

        //Edit Supplier
        editSupplierBox.setSpacing(10);
        Label editSupplierLabel = new Label("Edit Supplier");
        editSupplierLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");
        GridPane editSupplierGrid = new GridPane();
        editSupplierGrid.setHgap(180);
        editSupplierGrid.setVgap(10);
        Label supplierEditNameLabel = new Label("Full Name:");
        supplierEditNameLabel.setStyle("-fx-text-fill: #364958; -fx-font: 11pt Helvetica;");
        editSupplierGrid.add(supplierEditNameLabel, 0, 0);
        suppliersEditNameField.setStyle("-fx-font: 11pt Helvetica;");
        editSupplierGrid.add(suppliersEditNameField, 1, 0);

        //Edit Supplier Button
        updateSupplierButton.setStyle("-fx-font: 11pt Helvetica;");
        GridPane.setHalignment(updateSupplierButton, HPos.RIGHT);
        editSupplierGrid.add(updateSupplierButton, 1, 1);
        cancelUpdateButton.setStyle("-fx-font: 11pt Helvetica;");
        GridPane.setHalignment(cancelUpdateButton, HPos.RIGHT);
        editSupplierGrid.add(cancelUpdateButton, 1, 2);
        editSupplierBox.getChildren().addAll(editSupplierLabel, editSupplierGrid);
        editSupplierBox.setPrefWidth(450);

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
        editSupplierListButton.setStyle("-fx-font: 11pt Helvetica;");
        deleteSupplierListButton.setStyle("-fx-font: 11pt Helvetica;");
        editSupplierButtonList.getChildren().addAll(updateSupplierListButton, editSupplierListButton, deleteSupplierListButton);
        editSupplierButtonList.setSpacing(228);

        supplierListBox.getChildren().addAll(supplierListLabel, suppliersTableView, editSupplierButtonList);
        suppliersPage.getChildren().addAll(manageSupplierBox, supplierListBox);
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

    public TextField getSuppliersEditNameField() {
        return suppliersEditNameField;
    }

    public Button getUpdateSupplierButton() {
        return updateSupplierButton;
    }

    public HBox getEditSupplierButtonList() {
        return editSupplierButtonList;
    }

    public Button getEditSupplierListButton() {
        return editSupplierListButton;
    }

    public Button getDeleteSupplierListButton() {
        return deleteSupplierListButton;
    }

    public VBox getEditSupplierBox() {
        return editSupplierBox;
    }

    public VBox getManageSupplierBox() {
        return manageSupplierBox;
    }

    public VBox getAddSupplierBox() {
        return addSupplierBox;
    }

    public Button getCancelUpdateButton() {
        return cancelUpdateButton;
    }
}
