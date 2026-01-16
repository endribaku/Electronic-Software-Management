package Views;

import Interfaces.Views.ISupplierManagementView;
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

public class SupplierManagementView implements ISupplierManagementView {

    // CSS constants
    private static final String BUTTON_STYLE = "-fx-font: 11pt Helvetica;";
    private static final String HEADER_LABEL_STYLE = "-fx-font: 15pt Helvetica; -fx-font-weight: bold; -fx-text-fill: #364958;";
    private static final String LABEL_STYLE = "-fx-font: 11pt Helvetica; -fx-text-fill: #364958;";
    private static final String BOX_STYLE = "-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 20px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;";
    private static final String PAGE_STYLE = "-fx-background-color: white; -fx-padding: 10;";

    // Layouts
    private final HBox suppliersPage = new HBox();
    private final VBox manageSupplierBox = new VBox();
    private final VBox addSupplierBox = new VBox();
    private final VBox editSupplierBox = new VBox();

    // Input fields
    private final TextField suppliersNameField = new TextField();
    private final TextField suppliersEditNameField = new TextField();
    private final ObservableList<Item> itemList = FXCollections.observableArrayList();
    private final ListView<Item> itemBoxList = new ListView<>(itemList);

    // Buttons
    private final Button addSupplierButton = new Button("Register Supplier");
    private final Button updateSupplierButton = new Button("Update Supplier");
    private final Button cancelUpdateButton = new Button("Cancel");

    private final TableView<Supplier> suppliersTableView = new TableView<>();
    private final TableColumn<Supplier, String> supplierIDColumn = new TableColumn<>("ID");
    private final TableColumn<Supplier, String> supplierNameColumn = new TableColumn<>("Full Name");
    private final TableColumn<Supplier, Item> supplierItemListColumn = new TableColumn<>("Supplied Items");

    private final HBox editSupplierButtonList = new HBox();
    private final Button updateSupplierListButton = new Button("Update Table");
    private final Button editSupplierListButton = new Button("Edit Supplier");
    private final Button deleteSupplierListButton = new Button("Delete Supplier");

    public SupplierManagementView() {

        // Main page style
        suppliersPage.setStyle(PAGE_STYLE);

        // Create Supplier Box
        manageSupplierBox.setStyle(BOX_STYLE);

        // Add Supplier Section
        addSupplierBox.setSpacing(10);
        Label addSupplierLabel = new Label("Register new Supplier");
        addSupplierLabel.setStyle(HEADER_LABEL_STYLE);

        GridPane addSupplierGrid = new GridPane();
        addSupplierGrid.setHgap(180);
        addSupplierGrid.setVgap(10);

        Label supplierNameLabel = new Label("Full Name:");
        supplierNameLabel.setStyle(LABEL_STYLE);
        addSupplierGrid.add(supplierNameLabel, 0, 0);
        suppliersNameField.setStyle(BUTTON_STYLE);
        addSupplierGrid.add(suppliersNameField, 1, 0);

        addSupplierButton.setStyle(BUTTON_STYLE);
        GridPane.setHalignment(addSupplierButton, HPos.RIGHT);
        addSupplierGrid.add(addSupplierButton, 1, 1);

        addSupplierBox.getChildren().addAll(addSupplierLabel, addSupplierGrid);
        addSupplierBox.setPrefWidth(450);

        // Edit Supplier Section
        editSupplierBox.setSpacing(10);
        Label editSupplierLabel = new Label("Edit Supplier");
        editSupplierLabel.setStyle(HEADER_LABEL_STYLE);

        GridPane editSupplierGrid = new GridPane();
        editSupplierGrid.setHgap(180);
        editSupplierGrid.setVgap(10);

        Label supplierEditNameLabel = new Label("Full Name:");
        supplierEditNameLabel.setStyle(LABEL_STYLE);
        editSupplierGrid.add(supplierEditNameLabel, 0, 0);
        suppliersEditNameField.setStyle(BUTTON_STYLE);
        editSupplierGrid.add(suppliersEditNameField, 1, 0);

        updateSupplierButton.setStyle(BUTTON_STYLE);
        GridPane.setHalignment(updateSupplierButton, HPos.RIGHT);
        editSupplierGrid.add(updateSupplierButton, 1, 1);

        cancelUpdateButton.setStyle(BUTTON_STYLE);
        GridPane.setHalignment(cancelUpdateButton, HPos.RIGHT);
        editSupplierGrid.add(cancelUpdateButton, 1, 2);

        editSupplierBox.getChildren().addAll(editSupplierLabel, editSupplierGrid);
        editSupplierBox.setPrefWidth(450);

        manageSupplierBox.getChildren().add(addSupplierBox);

        // Supplier List
        VBox supplierListBox = new VBox();
        supplierListBox.setSpacing(10);
        supplierListBox.setStyle(BOX_STYLE);

        Label supplierListLabel = new Label("Supplier List:");
        supplierListLabel.setStyle(HEADER_LABEL_STYLE);

        suppliersTableView.setEditable(true);
        suppliersTableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY); // fix deprecated
        suppliersTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        suppliersTableView.setPrefWidth(800);

        // Table Columns
        supplierIDColumn.setMinWidth(200);
        supplierNameColumn.setMinWidth(200);
        supplierItemListColumn.setMinWidth(200);

        supplierIDColumn.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
        supplierIDColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        supplierIDColumn.setOnEditCommit(e -> e.getRowValue().setSupplierID(e.getNewValue()));

        supplierNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        supplierNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        supplierNameColumn.setOnEditCommit(e -> e.getRowValue().setName(e.getNewValue()));

        supplierItemListColumn.setCellValueFactory(new PropertyValueFactory<>("suppliedItems"));
        supplierItemListColumn.setCellFactory(ComboBoxTableCell.forTableColumn(itemList));

        suppliersTableView.getColumns().addAll(supplierIDColumn, supplierNameColumn, supplierItemListColumn);

        // Buttons below table
        updateSupplierListButton.setStyle(BUTTON_STYLE);
        editSupplierListButton.setStyle(BUTTON_STYLE);
        deleteSupplierListButton.setStyle(BUTTON_STYLE);

        editSupplierButtonList.getChildren().addAll(updateSupplierListButton, editSupplierListButton, deleteSupplierListButton);
        editSupplierButtonList.setSpacing(228);

        supplierListBox.getChildren().addAll(supplierListLabel, suppliersTableView, editSupplierButtonList);

        // Add boxes to main page
        suppliersPage.getChildren().addAll(manageSupplierBox, supplierListBox);
        suppliersPage.setSpacing(10);
    }

    // Getters
    public HBox getSuppliersPage() { return suppliersPage; }
    public TextField getSuppliersNameField() { return suppliersNameField; }
    public ObservableList<Item> getItemList() { return itemList; }
    public ListView<Item> getItemBoxList() { return itemBoxList; }
    public TableView<Supplier> getSuppliersTableView() { return suppliersTableView; }
    public Button getAddSupplierButton() { return addSupplierButton; }
    public TableColumn<Supplier, String> getSupplierIDColumn() { return supplierIDColumn; }
    public TableColumn<Supplier, Item> getSupplierItemListColumn() { return supplierItemListColumn; }
    public TableColumn<Supplier, String> getSupplierNameColumn() { return supplierNameColumn; }
    public Button getUpdateSupplierListButton() { return updateSupplierListButton; }
    public TextField getSuppliersEditNameField() { return suppliersEditNameField; }
    public Button getUpdateSupplierButton() { return updateSupplierButton; }
    public HBox getEditSupplierButtonList() { return editSupplierButtonList; }
    public Button getEditSupplierListButton() { return editSupplierListButton; }
    public Button getDeleteSupplierListButton() { return deleteSupplierListButton; }
    public VBox getEditSupplierBox() { return editSupplierBox; }
    public VBox getManageSupplierBox() { return manageSupplierBox; }
    public VBox getAddSupplierBox() { return addSupplierBox; }
    public Button getCancelUpdateButton() { return cancelUpdateButton; }

    @Override
    public void setSuppliersTableItems(ObservableList<Supplier> suppliers) {
        suppliersTableView.setItems(suppliers);
    }

    @Override
    public void setAvailableItems(ObservableList<Item> items) {
        itemBoxList.setItems(items);
    }

    @Override
    public String getSupplierNameInput() {
        return suppliersNameField.getText();
    }

    @Override
    public java.util.List<Item> getSelectedItemsForSupplier() {
        return itemBoxList.getSelectionModel().getSelectedItems();
    }

    @Override
    public Supplier getSelectedSupplier() {
        return suppliersTableView.getSelectionModel().getSelectedItem();
    }

    @Override
    public void setEditSupplierName(String value) {
        suppliersEditNameField.setText(value);
    }

    @Override
    public String getEditSupplierName() {
        return suppliersEditNameField.getText();
    }

    @Override
    public void showAddSupplierBox() {
        manageSupplierBox.getChildren().remove(editSupplierBox);
        if (!manageSupplierBox.getChildren().contains(addSupplierBox)) {
            manageSupplierBox.getChildren().add(addSupplierBox);
        }
    }

    @Override
    public void showEditSupplierBox() {
        manageSupplierBox.getChildren().remove(addSupplierBox);
        if (!manageSupplierBox.getChildren().contains(editSupplierBox)) {
            manageSupplierBox.getChildren().add(editSupplierBox);
        }
    }

    @Override
    public void onAddSupplier(Runnable action) {
        addSupplierButton.setOnAction(e -> action.run());
    }

    @Override
    public void onDeleteSupplier(Runnable action) {
        deleteSupplierListButton.setOnAction(e -> action.run());
    }

    @Override
    public void onEditSupplier(Runnable action) {
        editSupplierListButton.setOnAction(e -> action.run());
    }

    @Override
    public void onCancelUpdate(Runnable action) {
        cancelUpdateButton.setOnAction(e -> action.run());
    }

    @Override
    public void onUpdateTable(Runnable action) {
        updateSupplierListButton.setOnAction(e -> action.run());
    }

    @Override
    public void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.show();
    }

    @Override
    public void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.show();
    }

    @Override
    public void clearAddSupplierInputs() {
        suppliersNameField.clear();
        itemBoxList.getSelectionModel().clearSelection();
    }

}