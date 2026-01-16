package Interfaces.Views;

import Models.Bill;
import Models.Bill_Item;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IEmployeePerformanceView {

    // ✅ Root UI node (needed by controllers)
    HBox getEmployeePerformancePage();

    // bills data
    ObservableList<Bill> getBills();
    FilteredList<Bill> getFilteredBills();

    // filters + search
    ComboBox<String> getBillDateFilter();
    ComboBox<String> getEmployeePerformanceFilter();
    TextField getSearchBar();

    // table
    TableView<Bill> getBillTableView();
    TableColumn<Bill, Integer> getBillNumberColumn();
    TableColumn<Bill, String> getCashierColumn();
    TableColumn<Bill, ArrayList<Bill_Item>> getItemsSoldColumn();
    TableColumn<Bill, Number> getTotalPriceColumn();
    TableColumn<Bill, LocalDate> getDateOfSaleColumn();

    // charts containers
    VBox getChartsBox();
    VBox getWeeklyCharts();
    VBox getMonthlyCharts();
    VBox getYearlyCharts();
    VBox getTotalCharts();
    VBox getHeaderCharts();
    Label getChartsLabel();

    // charts
    LineChart<String, Number> getLineChartWeekly();
    LineChart<String, Number> getLineChartMonthly();
    LineChart<String, Number> getLineChartYearly();
    LineChart<String, Number> getLineChartTotal();

    XYChart.Series<String, Number> getSeriesWeekly();
    XYChart.Series<String, Number> getSeriesMonthly();
    XYChart.Series<String, Number> getSeriesYearly();
    XYChart.Series<String, Number> getSeriesTotal();

    PieChart getPieChartWeekly();
    PieChart getPieChartMonthly();
    PieChart getPieChartYearly();
    PieChart getPieChartTotal();
}
