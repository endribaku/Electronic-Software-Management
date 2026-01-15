package Views;

import Interfaces.Views.IEmployeePerformanceView;
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

public class EmployeePerformanceView implements IEmployeePerformanceView {
    private HBox employeePerformancePage = new HBox();
    private VBox billListBox = new VBox();
    private VBox chartsBox = new VBox();

    ObservableList<Bill> bills = FXCollections.observableArrayList();

    TextField searchBar = new TextField();
    FilteredList<Bill> filteredBills = new FilteredList<>(bills, p -> true);

    TableView<Bill> billTableView = new TableView<>(bills);
    TableColumn<Bill, Integer> billNumberColumn = new TableColumn<>("ID");
    TableColumn<Bill, String> cashierColumn = new TableColumn<>("Cashier");
    TableColumn<Bill, ArrayList<Bill_Item>> itemsSoldColumn = new TableColumn<>("Items Sold");
    TableColumn<Bill, Number> totalPriceColumn = new TableColumn<>("Total Price");
    TableColumn<Bill, LocalDate> dateOfSaleColumn = new TableColumn<>("Date of Sale");

    VBox headerCharts = new VBox();
    Label chartsLabel = new Label("Performance Charts");
    VBox weeklyCharts = new VBox();
    VBox monthlyCharts = new VBox();
    VBox yearlyCharts = new VBox();
    VBox totalCharts = new VBox();

    //Weekly Line Chart
    CategoryAxis xAxisWeekly = new CategoryAxis();
    NumberAxis yAxisWeekly = new NumberAxis();
    LineChart<String, Number> lineChartWeekly = new LineChart<>(xAxisWeekly, yAxisWeekly);
    XYChart.Series<String, Number> seriesWeekly = new XYChart.Series<>();
    //Monthly Line Chart
    CategoryAxis xAxisMonthly = new CategoryAxis();
    NumberAxis yAxisMonthly = new NumberAxis();
    LineChart<String, Number> lineChartMonthly = new LineChart<>(xAxisMonthly, yAxisMonthly);
    XYChart.Series<String, Number> seriesMonthly = new XYChart.Series<>();
    //Yearly Line Chart
    CategoryAxis xAxisYearly = new CategoryAxis();
    NumberAxis yAxisYearly = new NumberAxis();
    LineChart<String, Number> lineChartYearly = new LineChart<>(xAxisYearly, yAxisYearly);
    XYChart.Series<String, Number> seriesYearly = new XYChart.Series<>();
    //Total sales Line Chart
    CategoryAxis xAxisTotal = new CategoryAxis();
    NumberAxis yAxisTotal = new NumberAxis();
    LineChart<String, Number> lineChartTotal = new LineChart<>(xAxisTotal, yAxisTotal);
    XYChart.Series<String, Number> seriesTotal = new XYChart.Series<>();

    //Pie Charts of different time intervals
    PieChart pieChartWeekly = new PieChart();
    PieChart pieChartMonthly = new PieChart();
    PieChart pieChartYearly = new PieChart();
    PieChart pieChartTotal = new PieChart();

    ComboBox<String> billDateFilter = new ComboBox<>(FXCollections.observableArrayList("Today's Bills", "This Week's Bills", "This Month's Bills", "This Year's Bills", "Total Bills"));
    ComboBox<String> employeePerformanceFilter = new ComboBox<>(FXCollections.observableArrayList("This Week's Performance", "This Month's Performance", "This Year's Performance", "Total Performance"));

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
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> filteredBills.setPredicate(bill -> {
            if (newValue == null || newValue.isEmpty()) {
                return true; // Show all items
            }
            return bill.getUsername().toLowerCase().contains(newValue.toLowerCase()); // Filter items
        }));

        billTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        billTableView.setEditable(false);
        billNumberColumn.setCellValueFactory(new PropertyValueFactory<>("billNumber"));
        billNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        cashierColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        itemsSoldColumn.setCellValueFactory(new PropertyValueFactory<>("itemsSold"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        dateOfSaleColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfSale"));
        dateOfSaleColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));


        billTableView.getColumns().addAll(billNumberColumn, cashierColumn, itemsSoldColumn, totalPriceColumn, dateOfSaleColumn);
        billTableView.setPrefWidth(500);

        billListBox.getChildren().addAll(employeePerformanceLabel, billDateFilter, searchBar, billTableView);
        billListBox.setSpacing(10);

        //Display Charts Performance
        chartsLabel.setStyle("-fx-text-fill: #364958; -fx-font: 15pt Helvetica; -fx-font-weight: bold;");
        employeePerformanceFilter.setStyle("-fx-font: 11pt Helvetica;");
        employeePerformanceFilter.setMinWidth(470);
        headerCharts.getChildren().addAll(chartsLabel, employeePerformanceFilter);

        //Weekly
        employeePerformanceFilter.setValue("This Week's Performance");
        xAxisWeekly.setLabel("Days of the Week");
        yAxisWeekly.setLabel("Total Amount Earned");
        lineChartWeekly.setTitle("Weekly Sales Statistics");
        pieChartWeekly.setTitle("Weekly Employee Performance");
        seriesWeekly.setName("Total Sales");

        weeklyCharts.getChildren().addAll(lineChartWeekly, pieChartWeekly);

        //Monthly
        xAxisMonthly.setLabel("Days of the Month");
        yAxisMonthly.setLabel("Total Amount Earned");
        lineChartMonthly.setTitle("Monthly Sales Statistics");
        pieChartMonthly.setTitle("Monthly Employee Performance");
        seriesMonthly.setName("Total Sales");
        monthlyCharts.getChildren().addAll(lineChartMonthly, pieChartMonthly);

        //Yearly
        xAxisYearly.setLabel("Months of the Year");
        yAxisYearly.setLabel("Total Amount Earned");
        lineChartYearly.setTitle("Yearly Sales Statistics");
        pieChartYearly.setTitle("Yearly Employee Performance");
        seriesYearly.setName("Total Sales");
        yearlyCharts.getChildren().addAll(lineChartYearly, pieChartYearly);

        //Total
        xAxisTotal.setLabel("Days");
        yAxisTotal.setLabel("Total Amount Earned");
        lineChartTotal.setTitle("Sales Statistics");
        pieChartTotal.setTitle("Employee Performance");
        seriesTotal.setName("Total Sales");
        totalCharts.getChildren().addAll(lineChartTotal, pieChartTotal);

        chartsBox.getChildren().addAll(headerCharts, weeklyCharts);
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

    public TableColumn<Bill, String> getCashierColumn() {
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

    public NumberAxis getyAxisWeekly() {
        return yAxisWeekly;
    }

    public CategoryAxis getxAxisWeekly() {
        return xAxisWeekly;
    }

    public LineChart<String, Number> getLineChartWeekly() {
        return lineChartWeekly;
    }

    public PieChart getPieChartWeekly() {
        return pieChartWeekly;
    }

    public XYChart.Series<String, Number> getSeriesWeekly() {
        return seriesWeekly;
    }

    public CategoryAxis getxAxisMonthly() {
        return xAxisMonthly;
    }

    public NumberAxis getyAxisMonthly() {
        return yAxisMonthly;
    }

    public LineChart<String, Number> getLineChartMonthly() {
        return lineChartMonthly;
    }

    public XYChart.Series<String, Number> getSeriesMonthly() {
        return seriesMonthly;
    }

    public CategoryAxis getxAxisYearly() {
        return xAxisYearly;
    }

    public NumberAxis getyAxisYearly() {
        return yAxisYearly;
    }

    public LineChart<String, Number> getLineChartYearly() {
        return lineChartYearly;
    }

    public XYChart.Series<String, Number> getSeriesYearly() {
        return seriesYearly;
    }

    public CategoryAxis getxAxisTotal() {
        return xAxisTotal;
    }

    public NumberAxis getyAxisTotal() {
        return yAxisTotal;
    }

    public LineChart<String, Number> getLineChartTotal() {
        return lineChartTotal;
    }

    public XYChart.Series<String, Number> getSeriesTotal() {
        return seriesTotal;
    }

    public PieChart getPieChartMonthly() {
        return pieChartMonthly;
    }

    public PieChart getPieChartYearly() {
        return pieChartYearly;
    }

    public PieChart getPieChartTotal() {
        return pieChartTotal;
    }

    public Label getChartsLabel() {
        return chartsLabel;
    }

    public VBox getWeeklyCharts() {
        return weeklyCharts;
    }

    public VBox getMonthlyCharts() {
        return monthlyCharts;
    }

    public VBox getYearlyCharts() {
        return yearlyCharts;
    }

    public VBox getTotalCharts() {
        return totalCharts;
    }

    public VBox getHeaderCharts() {
        return headerCharts;
    }
}
