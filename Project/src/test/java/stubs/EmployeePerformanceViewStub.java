package stubs;

import Interfaces.Views.IEmployeePerformanceView;
import Models.Bill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.chart.XYChart;

import java.time.LocalDate;
import java.util.ArrayList;

public class EmployeePerformanceViewStub implements IEmployeePerformanceView {

    private final XYChart.Series<String, Number> weekly = new XYChart.Series<>();
    private final XYChart.Series<String, Number> monthly = new XYChart.Series<>();
    private final XYChart.Series<String, Number> yearly = new XYChart.Series<>();
    private final XYChart.Series<String, Number> total = new XYChart.Series<>();


    /* ---------------- Data-only fields ---------------- */

    private final ObservableList<Bill> bills =
            FXCollections.observableArrayList();

    private final FilteredList<Bill> filteredBills =
            new FilteredList<>(bills);


    /* ---------------- Bills data ---------------- */

    @Override
    public ObservableList<Bill> getBills() {
        return bills;
    }

    @Override
    public FilteredList<Bill> getFilteredBills() {
        return filteredBills;
    }

    /* ---------------- Everything else = null ---------------- */

    @Override public javafx.scene.layout.HBox getEmployeePerformancePage() { return null; }

    @Override public javafx.scene.control.ComboBox<String> getBillDateFilter() { return null; }
    @Override public javafx.scene.control.ComboBox<String> getEmployeePerformanceFilter() { return null; }
    @Override public javafx.scene.control.TextField getSearchBar() { return null; }

    @Override public javafx.scene.control.TableView<Bill> getBillTableView() { return null; }
    @Override public javafx.scene.control.TableColumn<Bill, Integer> getBillNumberColumn() { return null; }
    @Override public javafx.scene.control.TableColumn<Bill, String> getCashierColumn() { return null; }
    @Override public javafx.scene.control.TableColumn<Bill, ArrayList<Models.Bill_Item>> getItemsSoldColumn() { return null; }
    @Override public javafx.scene.control.TableColumn<Bill, Number> getTotalPriceColumn() { return null; }
    @Override public javafx.scene.control.TableColumn<Bill, LocalDate> getDateOfSaleColumn() { return null; }

    @Override public javafx.scene.layout.VBox getChartsBox() { return null; }
    @Override public javafx.scene.layout.VBox getWeeklyCharts() { return null; }
    @Override public javafx.scene.layout.VBox getMonthlyCharts() { return null; }
    @Override public javafx.scene.layout.VBox getYearlyCharts() { return null; }
    @Override public javafx.scene.layout.VBox getTotalCharts() { return null; }
    @Override public javafx.scene.layout.VBox getHeaderCharts() { return null; }
    @Override public javafx.scene.control.Label getChartsLabel() { return null; }

    @Override public javafx.scene.chart.LineChart<String, Number> getLineChartWeekly() { return null; }
    @Override public javafx.scene.chart.LineChart<String, Number> getLineChartMonthly() { return null; }
    @Override public javafx.scene.chart.LineChart<String, Number> getLineChartYearly() { return null; }
    @Override public javafx.scene.chart.LineChart<String, Number> getLineChartTotal() { return null; }


    @Override
    public XYChart.Series<String, Number> getSeriesWeekly() {
        return weekly;
    }

    @Override
    public XYChart.Series<String, Number> getSeriesMonthly() {
        return monthly;
    }

    @Override
    public XYChart.Series<String, Number> getSeriesYearly() {
        return yearly;
    }

    @Override
    public XYChart.Series<String, Number> getSeriesTotal() {
        return total;
    }

    @Override public javafx.scene.chart.PieChart getPieChartWeekly() { return null; }
    @Override public javafx.scene.chart.PieChart getPieChartMonthly() { return null; }
    @Override public javafx.scene.chart.PieChart getPieChartYearly() { return null; }
    @Override public javafx.scene.chart.PieChart getPieChartTotal() { return null; }
}
