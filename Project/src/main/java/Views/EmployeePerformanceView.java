package Views;

import DAO.BillFileHandler;
import Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;
import java.util.ArrayList;

public class EmployeePerformanceView {
    private HBox employeePerformancePage = new HBox();
    private VBox billListBox = new VBox();
    private VBox chartsBox = new VBox();

    ObservableList<Bill> bills = FXCollections.observableArrayList();

    TextField searchBar = new TextField();
    FilteredList<Bill> filteredBills = new FilteredList<>(bills, p -> true);

    TableView<Bill> billTableView = new TableView<>(bills);
    TableColumn<Bill, Integer> billNumberColumn = new TableColumn<>("ID");
    TableColumn<Bill, User> cashierColumn = new TableColumn<>("Cashier");
//    TableColumn<Bill, Sector> sectorColumn = new TableColumn<>("Sector");
    TableColumn<Bill, ArrayList<Bill_Item>> itemsSoldColumn = new TableColumn<>("Items Sold");
    TableColumn<Bill, Number> totalPriceColumn = new TableColumn<>("Total Price");
    TableColumn<Bill, LocalDate> dateOfSaleColumn = new TableColumn<>("Date of Sale");

    ComboBox<String> billDateFilter = new ComboBox<>(FXCollections.observableArrayList("Today's Bills", "This Months's Bills", "This Year's Bills", "Total Bills"));
    ComboBox<String> employeePerformanceFilter = new ComboBox<>(FXCollections.observableArrayList("Today's Bills", "This Months's Bills", "This Year's Bills", "Total Bills"));

    public EmployeePerformanceView() {

        Label employeePerformanceLabel = new Label("Employee Performance");
        employeePerformanceLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");

        employeePerformancePage.setStyle("-fx-background-color: white; -fx-padding: 10;");
        billListBox.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 10px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");
        billListBox.setMinWidth(800);
        billDateFilter.setValue("Today's Bills");
        billDateFilter.setStyle("-fx-font: 11pt Helvetica;");
        billDateFilter.setMinWidth(770);

        searchBar.setPromptText("Search...");
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredBills.setPredicate(bill -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Show all items
                }
                return bill.getUser().getFullName().toLowerCase().contains(newValue.toLowerCase()); // Filter items
            });
        });

        billTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        billTableView.setEditable(false);
        billNumberColumn.setCellValueFactory(new PropertyValueFactory<>("billNumber"));
        billNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        cashierColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        itemsSoldColumn.setCellValueFactory(new PropertyValueFactory<>("itemsSold"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        dateOfSaleColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfSale"));
        dateOfSaleColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));


        billTableView.getColumns().addAll(billNumberColumn, cashierColumn, itemsSoldColumn, totalPriceColumn, dateOfSaleColumn);
        billTableView.setPrefWidth(500);

        billListBox.getChildren().addAll(employeePerformanceLabel, billDateFilter, searchBar, billTableView);
        billListBox.setSpacing(10);

        //Display Charts
        Label chartsLabel = new Label("Performance Charts");
        chartsLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");

        employeePerformanceFilter.setValue("Today's Bills");
        employeePerformanceFilter.setStyle("-fx-font: 11pt Helvetica;");
        employeePerformanceFilter.setMinWidth(470);

        LineChart<String, Number> lineChart = createLineChart();
        PieChart pieChart = createPieChart();

        chartsBox.getChildren().addAll(chartsLabel, employeePerformanceFilter, lineChart, pieChart);
        chartsBox.setStyle("-fx-border-color: #E0E0CE; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-padding: 10px; -fx-background-color: #E0E0CE; -fx-background-radius: 15px;");
        chartsBox.setSpacing(10);

        employeePerformancePage.getChildren().addAll(billListBox, chartsBox);
        employeePerformancePage.setSpacing(10);
    }

    public HBox getEmployeePerformancePage() {
        return employeePerformancePage;
    }

    public VBox getBillListBox() {
        return billListBox;
    }

    public ObservableList<Bill> getBills() {
        return bills;
    }

    public TextField getSearchBar() {
        return searchBar;
    }

    public FilteredList<Bill> getFilteredBills() {
        return filteredBills;
    }

    public TableView<Bill> getBillTableView() {
        return billTableView;
    }

    public TableColumn<Bill, Integer> getBillNumberColumn() {
        return billNumberColumn;
    }

    public TableColumn<Bill, User> getCashierColumn() {
        return cashierColumn;
    }

    public TableColumn<Bill, ArrayList<Bill_Item>> getItemsSoldColumn() {
        return itemsSoldColumn;
    }

    public TableColumn<Bill, Number> getTotalPriceColumn() {
        return totalPriceColumn;
    }

    public TableColumn<Bill, LocalDate> getDateOfSaleColumn() {
        return dateOfSaleColumn;
    }

    public VBox getChartsBox() {
        return chartsBox;
    }

    public ComboBox<String> getBillDateFilter() {
        return billDateFilter;
    }

    public ComboBox<String> getEmployeePerformanceFilter() {
        return employeePerformanceFilter;
    }

    private LineChart<String, Number> createLineChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Sales Over Time");
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Sales");
        series.getData().add(new XYChart.Data<>("Mon", 1000));
        series.getData().add(new XYChart.Data<>("Tue", 2000));
        series.getData().add(new XYChart.Data<>("Wed", 3000));
        series.getData().add(new XYChart.Data<>("Thu", 4000));
        series.getData().add(new XYChart.Data<>("Fri", 3500));
        series.getData().add(new XYChart.Data<>("Sat", 2500));
        series.getData().add(new XYChart.Data<>("Sun", 1500));
        lineChart.getData().add(series);
        return lineChart;
    }

    private PieChart createPieChart() {
        PieChart pieChart = new PieChart();
        pieChart.setTitle("Employee Performance");
        pieChart.getData().add(new PieChart.Data("Hazis", 40));
        pieChart.getData().add(new PieChart.Data("Endri", 30));
        pieChart.getData().add(new PieChart.Data("Moel", 20));
        pieChart.getData().add(new PieChart.Data("Daron", 10));
        return pieChart;
    }
}
