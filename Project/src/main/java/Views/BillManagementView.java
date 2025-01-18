package Views;

import Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class BillManagementView {
    HBox billManagePage = new HBox();

    ObservableList<Bill> billList = FXCollections.observableArrayList();

    FilteredList<Bill> filteredBillList = new FilteredList<>(billList, p -> true);

    TextField searchBar = new TextField();

    ListView<Bill> itemListView = new ListView<>(filteredBillList);

    VBox billListBox = new VBox();

    Button addToBillButton = new Button("Add to Bill");

    TableView<Bill> billTableView = new TableView<>(billList);
    TableColumn<Bill, Number> billNumberTableColumn = new TableColumn<>("ID");
    TableColumn<Bill, String> billStringTableColumn = new TableColumn<>("Name");
    TableColumn<Bill, Number> itemQuantityColumn = new TableColumn<>("Quantity");
    TableColumn<Bill, Number> itemPriceColumn = new TableColumn<>("Price");

    Button createBillButton = new Button("Create Bill");

    public BillManagementView() {

        billManagePage.setStyle("-fx-background-color: white; -fx-padding: 10;");
        billListBox.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 10px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");

        searchBar.setPromptText("Search...");
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredBillList.setPredicate(bill -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Show all items
                }
                return bill.getCashier().getFullName().toLowerCase().equals(newValue.toLowerCase()); // Filter items
            });
        });

        itemListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Display Bill
        Label billListLabel = new Label("Manage Bills");
        billListLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");
        billListBox.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 20px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");
        billListBox.setSpacing(10);

        billTableView.getColumns().addAll(billNumberTableColumn, billStringTableColumn, itemQuantityColumn, itemPriceColumn);
        billTableView.setPrefWidth(1300);
        billListBox.getChildren().addAll(billListLabel, searchBar, billTableView);

        billManagePage.getChildren().addAll(billListBox);
        billManagePage.setSpacing(10);
    }

    public HBox getBillManagePage() {
        return billManagePage;
    }
}
